package org.schrodinger;

import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.FastMath;
import org.schrodinger.gui.Map2D;
import org.schrodinger.potential.Coulomb;
import org.schrodinger.potential.Parabel2;
import org.schrodinger.potential.Potential;

public class Energieeigenwerte2D {

	private double h = Einstellungen.h;
	private double u = Einstellungen.u;
	private double e = Einstellungen.e;
	private double pi = Math.PI; 
	private double e0 = Einstellungen.e0;
	
	private RealMatrix A,G,S;
	
	Potential c;
	
	int dimension = 2;

	public Energieeigenwerte2D(Potential p){
		c = p;
	}
	
	public static void main(String[] args){
		new Energieeigenwerte2D(new Parabel2(Einstellungen.e,1E-10)).run();
	}
	
	public void run1(){
		double step = 1E-10;
		int N = 500;
		int EW = 1;
		int a_max = (int) Math.pow(N, dimension);
		
		RealVector A = new ArrayRealVector(N*N);
		
		for(int a = 0; a<N*N; a++){
			A.setEntry(a, potential(a, N, step));
		}
		
		
		JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(500,600);
        Map2D m = new Map2D();
        m.setSize(frame.getWidth(), frame.getHeight());
        m.setLocation(20, 20);
        m.setData(to2D(A.toArray(),N));
        m.repaint();
        frame.setContentPane(m);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.repaint();
	}

	public void run(){
		System.out.println("Search started with "+dimension+"dimensions");
		System.out.println("Available Memory"+Runtime.getRuntime().maxMemory()/1024/1024+"MB");
		double step = 1E-10;
		int N = 50;
		int EW = Einstellungen.maxNiveaus;
		int a_max = (int) Math.pow(N, dimension);
		
		double C = -h*h/(8*pi*pi*u*step*step);
		
		A = MatrixUtils.createRealMatrix(a_max, a_max);
				
		System.out.println("Building Matrix A");
		//Build Matrix A
		for(int a=0;a<A.getRowDimension();a++){
			A.setEntry(a, a, potential(a,N,step));//zurückändern
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
		S = MatrixUtils.createRealMatrix(a_max,a_max);
		G = MatrixUtils.createRealMatrix(a_max, a_max);
		RealMatrix Lambda = MatrixUtils.createRealMatrix(EW,EW);
		
		for(int i = 0; i<EW; i++){
			for(int j = i; j<a_max;j++){
				X.setEntry(j,i,1);
			}
		}
		gramSchmidt(X);
		
		Matrix.multiply(Matrix.transpose(X.getData()),Matrix.multiply(A.getData(), X.getData()));
		Lambda = X.transpose().multiply(A.multiply(X));
		S = A.multiply(X).subtract(X.multiply(Lambda));
		G=S;
		int iterations = 301;
		for(int i=0;i<iterations;i++){
			Einstellungen.berechneteNiveaus = i*100/iterations;
			System.out.println("Finished "+i*100/iterations+"%");
			
			X = minimizeRQ(X, S, A);
			gramSchmidt(X);
			if(i%4==0){
				//Ritz
				RealMatrix A_ = X.transpose().multiply(A.multiply(X));
				A_ = A_.scalarMultiply(1E20);
				try{
					EigenDecomposition eigen = new EigenDecomposition(A_);
					RealMatrix Q = eigen.getV();
					Lambda = eigen.getD().scalarMultiply(1E-20);
					X = X.multiply(Q);
					S = A.multiply(X).subtract(X.multiply(Lambda));
					G=S;
				}catch(MaxCountExceededException e){
					JOptionPane.showMessageDialog(null,"Error: Konvergenz fehlgeschlagen");
					Einstellungen.allesGezeichnet = true;
					return;
				}
			}else{
				//Standard
				ArrayList<Thread> thread= new ArrayList<>();
				for(int k = 0; k<EW;k++){
					final int j = k;
					final RealVector g_alt = G.getColumnVector(j);
					final RealVector x = X.getColumnVector(j);
					final RealVector s_ = S.getColumnVector(j);
					Thread t = new Thread(){
						public void run(){
							RealVector g = A.operate(x).subtract(x.mapMultiply(RQ(A,x)));
							RealVector s = g.subtract(s_.mapMultiply(g.dotProduct(g)/g_alt.dotProduct(g_alt))); 
							G.setColumnVector(j,g);
							S.setColumnVector(j,s);
						}
					};
					t.start();
					thread.add(t);
				}
				for(Thread th: thread){
					try {
						th.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		Lambda = X.transpose().multiply(A.multiply(X));
		for(int i = 0; i< EW; i++){
				JFrame frame=new JFrame();
				frame.setTitle(new DecimalFormat("##.### eV").format(Lambda.getEntry(i,i)/e));
		        frame.setLayout(new FlowLayout());
		        frame.setSize(500,500);
		        frame.setBounds(i%3*500, i/3*500, 500,500);
		        Map2D m = new Map2D();
		        m.setSize(frame.getWidth(), frame.getHeight());
		        m.setLocation(20, 20);
		        m.setData(to2D(X.getColumn(i),N));
		        m.repaint();
		        frame.setContentPane(m);
		        frame.setVisible(true);
		        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        frame.repaint();
		}
		Einstellungen.allesGezeichnet = true;
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

	private RealMatrix minimizeRQ(final RealMatrix X, final RealMatrix S, final RealMatrix A) {
		ArrayList<Thread> thread = new ArrayList<>();
		for(int j = 0; j<X.getColumnDimension();j++){
			final int i = j;
			Thread t = new Thread(){
				public void run(){
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
			};
			t.start();
			thread.add(t);
		}
		for(Thread th: thread){
			try {
				th.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return X;
	}

	private double potential(int a,int N, double step) {
		float i=0;

		for(int j=dimension; j>0;j--){
			int temp = (int)FastMath.floor(a/FastMath.pow(N, j-1));
			i += (temp-(N+1)/2.)*(temp-(N+1)/2.);//Quadrate der einzelnen Zahlen addieren
			a-=temp * FastMath.pow(N, j-1); // abziehen
		}
		
		double x = Math.sqrt(i)*step;

		double ret = c.getPotential(x);
		if(ret<-200*e){
			return ret;
		}else{
			return ret;
		}
	}
	
	private double potential2(int a0, int N, double step){
		float i = 0;
		if(dimension != 2){
			throw new IllegalArgumentException("Dimension is not 2");
		}
		
		int temp = (int)FastMath.floor(a0/FastMath.pow(N, 1));
		double x = temp*step;
		a0-=temp * FastMath.pow(N, 1); // abziehen

		temp = (int)FastMath.floor(a0);
		double y = temp*step;
		
			
		double x0 = 1E-10;
		double V0 = 1;
		double c=1E30;
				
		return V0/FastMath.pow(x0, 4)*(x-x0)*(x-x0)*(x+x0)*(x+x0)+c*y*y;
	}
}
