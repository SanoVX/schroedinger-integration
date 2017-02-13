import java.util.ArrayList;

public class SchrödingerIntegration {
	public static double step = 0.000000001;
	public static double xrange = 0.00001;
	public static double h = 6.626*Math.pow(10, 34); // wirkungsquantum
	public static double u = 9.1*Math.pow(10,-31); //elementarmasse
	public static double e = 1.6*Math.pow(10,-19); //elementarladung
	public static double pi = Math.PI; 
	public static double s = 2*u/e/(4*pi*pi);
	public static double E = -13.7;

	public static double e0 = 8.8*Math.pow(10,-12);
	static ArrayList<ArrayList<Double>> f = new ArrayList<>();
	
	public static void recursion(){

		//Anfangsbedingungen
		ArrayList<Double> Sx = new ArrayList<>();
		Sx.add(0.0); Sx.add(0.0);
		f.add(Sx);
		Sx = new ArrayList<>();
		Sx.add(step); Sx.add(step);
		f.add(Sx);

		for(double x = 0; x < xrange; ){
			x = f.get(f.size()-1).get(0) + step;
			Sx = new ArrayList<>();
			double y = (2+10*tfactor(x))*f.get(f.size()-1).get(1)-(1-tfactor(x-step))*f.get(f.size()-2).get(1);
			y /= (1-tfactor(x+step));
			Sx.add(x);
			Sx.add(y);
			f.add(Sx);
		}
		for(int i = 0; i < f.size(); i++){
			f.get(i).set(1, f.get(i).get(1));
		}
		
		
	}
	
	public static double tfactor(double x){

		double t = -(E - e/(4*pi*e0*x*x))/((E - e/(4*pi*e0*step*step)));
		//System.out.println(t);
		return t;
	}
	
	
	public static void main(String[] args) throws InterruptedException{
		Game g = new Game();
		g.drawpoints = false;
		for(int i = 0; i < 5; i++){
		recursion();
		System.out.println(f.size());
		g.addMeasures(f);
		E = -13.6;
		xrange = Math.sqrt(e/(4*pi*e0*E));
		f.clear();
		}
		g.plot();
		
	}
}
