import java.util.ArrayList;

public class Parabel extends Potential {

	double tiefe, breite;
	
	public Parabel(double tiefe, double breite){
		this.tiefe = tiefe;
		this.breite = breite;
	}
	
	
	
	@Override
	public double getPotential(double x) {
		return breite*x*x+tiefe;
	}

	@Override
	public ArrayList<ArrayList<Double>> getPlot(double xRange) {
		// TODO Auto-generated method stub
		return null;
	}

}
