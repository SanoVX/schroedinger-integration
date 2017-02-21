import java.util.ArrayList;

public abstract class Potential {

	protected double h = SchroedingerIntegration.h; // wirkungsquantum
	protected double u = SchroedingerIntegration.u; //elementarmasse
	protected double e = SchroedingerIntegration.e; //elementarladung
	protected double pi = Math.PI; 
	protected double e0 = SchroedingerIntegration.e0;
		
	
	public Potential(){
		
	}
	
	public abstract double getPotential(double x);
	
	public abstract ArrayList<ArrayList<Double>> getPlot(double xRange);
	
	
}
