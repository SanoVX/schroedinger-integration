import java.util.ArrayList;

public class PeriodicPotential extends Potential {
	private int Anzahl;
	private double distance;
	private Potential potential;

	public PeriodicPotential(int anz, double distance, Potential potential){
		this.Anzahl = anz;
		this.distance = distance;
		this.potential = potential;
	}
	
	@Override
	public double getPotential(double x) {
		if(x>distance*Anzahl){
			return potential.getPotential(x-distance*Anzahl);
		}
		x = x%distance-distance/2;
		return potential.getPotential(x);
	}

	@Override
	public ArrayList<ArrayList<Double>> getPlot(double xRange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gibFunktion() {
		return potential.gibFunktion();
	}

}
