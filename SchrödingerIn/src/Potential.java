import java.util.ArrayList;

public class Potential {

	public static double h = SchroedingerIntegration.h; // wirkungsquantum
	public static double u = SchroedingerIntegration.u; //elementarmasse
	public static double e = SchroedingerIntegration.e; //elementarladung
	public static double pi = Math.PI; 
	public static double e0 = SchroedingerIntegration.e0;
	
	
	public Potential(){
		
	}
	
	public double getPotential(double x){
		return -e*e/(4*pi*e0*Math.abs(x));
	}
	
	public ArrayList<ArrayList<Double>> getPlot(){
		return null;
	}
	
	
}
