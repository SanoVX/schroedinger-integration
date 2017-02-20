import java.util.ArrayList;

public abstract class Potential {

	public static double h = SchroedingerIntegration.h; // wirkungsquantum
	public static double u = SchroedingerIntegration.u; //elementarmasse
	public static double e = SchroedingerIntegration.e; //elementarladung
	public static double pi = Math.PI; 
	public static double e0 = SchroedingerIntegration.e0;
	
	
	public Potential(){
		
	}
	
	public abstract double getPotential(double x);
	
	public abstract ArrayList<ArrayList<Double>> getPlot(double xRange);
	
	
}
