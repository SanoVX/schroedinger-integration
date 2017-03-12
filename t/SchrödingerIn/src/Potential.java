import java.util.ArrayList;

public abstract class Potential {

	protected double h = Einstellungen.h; // wirkungsquantum
	protected double u = Einstellungen.u; //elementarmasse
	protected double e = Einstellungen.e; //elementarladung
	protected double pi = Math.PI; 
	protected double e0 = Einstellungen.e0;
		
	
	public Potential(){
		
	}
	
	public abstract double getPotential(double x);
	
	public abstract ArrayList<ArrayList<Double>> getPlot(double xRange);
	
	
}
