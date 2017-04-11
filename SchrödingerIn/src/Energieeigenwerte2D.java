import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.FastMath;
import org.tc33.jheatchart.HeatChart;

public class Energieeigenwerte2D {

	private double h = Einstellungen.h;
	private double u = Einstellungen.u;
	private double e = Einstellungen.e;
	private double pi = Math.PI; 
	private double e0 = Einstellungen.e0;
	
	Potential c = new Parabel(0,1);
	
	int dimension = 2;
	

	public static void main(String[] args) {
		Energieeigenwerte2D E = new Energieeigenwerte2D();
		E.run();
	}	
	
	public void run1(){
		double step = 1E-10;
		int N = 10;
		int EW = 1;
		int a_max = (int) Math.pow(N, dimension);
		
		RealVector A = new ArrayRealVector(N*N);
		
		for(int a = 0; a<N*N; a++){
			A.setEntry(a, potential(a, N, step));
		}
		
		
		final HeatChart chart = new HeatChart(to2D(A.toArray(),N));
		chart.setColourScale(HeatChart.SCALE_LINEAR);
		chart.setHighValueColour(Color.red);
		chart.setLowValueColour(Color.blue);
		try {
			chart.saveToFile(new File("C:/Users/jneuser/Documents/test.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run(){
		System.out.println("Search started with "+dimension+"dimensions");
		double step = 1E-10;
		int N = 60;
		int EW = 5;
		int a_max = (int) Math.pow(N, dimension);
		
		double C = h*h/(8*pi*pi*u*step*step);
		
		RealMatrix A = MatrixUtils.createRealMatrix(a_max, a_max);
				
		System.out.println("Building Matrix A");
		//Build Matrix A
		for(int a=0;a<A.getRowDimension();a++){
			A.setEntry(a, a, potential(a,N,step));
			for(int j=0;j<dimension;j++){
				int temp = (int) Math.pow(N, j);
				if(a+temp<a_max){
					A.setEntry(a, a+temp, C);
				}
				if(a-temp >=0){
					A.setEntry(a, a-temp, C);
				}
			}
		}
		
		System.out.println("Ready for search of eigenvalues");
		
		System.out.println("Begin search");
				
		RealMatrix X = MatrixUtils.createRealMatrix(a_max, EW);
		RealMatrix S = MatrixUtils.createRealMatrix(a_max,a_max);
		RealMatrix G = MatrixUtils.createRealMatrix(a_max, a_max);
		RealMatrix Lambda = MatrixUtils.createRealMatrix(EW,EW);
		
		for(int i = 0; i<EW; i++){
			for(int j = i; j<a_max;j++){
				X.setEntry(j,i,1);
			}
		}
		gramSchmidt(X);
		
		Lambda = X.transpose().multiply(A.multiply(X));
		S = A.multiply(X).subtract(X.multiply(Lambda));
		G=S;
		int iterations = 100;
		for(int i=0;i<iterations;i++){
			System.out.println("Finished "+i*100/iterations+"%");
			
			X = minimizeRQ(X, S, A);
			gramSchmidt(X);
			if(i%4==0){
				//Ritz
				RealMatrix A_ = X.transpose().multiply(A.multiply(X));
				A_ = A_.scalarMultiply(1E20);
				EigenDecomposition eigen = new EigenDecomposition(A_);
				RealMatrix Q = eigen.getV();
				Lambda = eigen.getD().scalarMultiply(1E-20);
				X = X.multiply(Q);
				S = A.multiply(X).subtract(X.multiply(Lambda));
				G=S;
			}else{
				//Standard
				for(int j = 0; j<EW;j++){
					RealVector g_alt = G.getColumnVector(j);
					RealVector g = A.operate(X.getColumnVector(j)).subtract(X.getColumnVector(j).mapMultiply(RQ(A,X.getColumnVector(j))));
					RealVector s = g.subtract(S.getColumnVector(j).mapMultiply(g.dotProduct(g)/g_alt.dotProduct(g_alt))); 
					G.setColumnVector(j,g);
					S.setColumnVector(j,s);
				}
			}
		}

		for(int i = 0; i< EW; i++){
			System.out.println(Lambda.getEntry(i,i)/e);
			final HeatChart chart = new HeatChart(to2D(X.getColumn(i),N));
			chart.setColourScale(HeatChart.SCALE_LINEAR);
			chart.setHighValueColour(Color.red);
			chart.setLowValueColour(Color.blue);
			try {
				JFrame frame=new JFrame();
		        frame.setLayout(new FlowLayout());
		        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		        frame.setSize(500,600);
		        JLabel lbl = new JLabel(new ImageIcon(chart.getChartImage()));
		        lbl.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		        frame.add(lbl);
		        frame.setVisible(true);
		        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				chart.saveToFile(new File("C:/Users/jneuser/Documents/test"+i+".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private double[][] to2D(double[] x, int N) {
		double[][] ret = new double[N][N];
		for(int i = 0 ; i<N;i++){
			for(int j = 0; j< N ; j++){
				ret[i][j] = x[i + N*j];
			}
		}
		return ret;
	}

	private double RQ(RealMatrix A,RealVector x) {
		return x.dotProduct(A.operate(x))/x.getNorm();
	}

	private RealMatrix gramSchmidt(RealMatrix X) {	
		X.setColumnVector(0, X.getColumnVector(0).unitVector());

		for(int i = 0; i<X.getColumnDimension();i++){
			for(int j = 0; j<i; j++){
				X.setColumnVector(i,X.getColumnVector(i).subtract(X.getColumnVector(j).mapMultiplyToSelf(
						X.getColumnVector(j).dotProduct(X.getColumnVector(i)))));
			}
			X.setColumnVector(i,X.getColumnVector(i).unitVector());
		}
		return X;
	}

	private RealMatrix minimizeRQ(RealMatrix X, RealMatrix S, RealMatrix A) {
		for(int i = 0; i<X.getColumnDimension();i++){
			double sx = S.getColumnVector(i).dotProduct(X.getColumnVector(i));
			double xx = X.getColumnVector(i).dotProduct(X.getColumnVector(i));
			double ss = S.getColumnVector(i).dotProduct(S.getColumnVector(i));
			double sAs = S.getColumnVector(i).dotProduct(A.operate(S.getColumnVector(i)));
			double xAs = X.getColumnVector(i).dotProduct(A.operate(S.getColumnVector(i)));
			double xAx = X.getColumnVector(i).dotProduct(A.operate(X.getColumnVector(i)));
			
			double p = sx*sAs-ss*xAs;
			double q = xx*sAs-ss*xAx;
			double r = xx*xAs-sx*xAx;
			
			double alpha = (-q + Math.sqrt(q*q - 4*p*r))/(2*p);
			
			X.setColumnVector(i,X.getColumnVector(i).add(S.getColumnVector(i).mapMultiply(alpha)));
		}
		return X;
	}

	private double potential(int a,int N, double step) {
		int i=0;
		for(int j=dimension; j>0;j--){
			int temp = (int)FastMath.floor(a/FastMath.pow(N, j-1));
			i += (temp-(N+1)/2)*(temp-(N+1)/2);//Quadrate der einzelnen Zahlen addieren
			a-=temp * FastMath.pow(N, j-1); // abziehen
		}
		
		double x = Math.sqrt(i)*step;
		return c.getPotential(x);
	}
}
