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
		x = x%distance;

		return potential.getPotential(x);
	}

	@Override
	public ArrayList<ArrayList<Double>> getPlot(double xRange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gibFunktion() {
		String ret="";
		ret += potential.gibFunktion()+"*theta(x-"+distance/2+")";
		for(int i = 1; i<Anzahl; i++){
			ret += "+"+potential.gibFunktion().replaceAll("(x)", "x-"+distance*i)+"*theta(x-"+(2*i-1)*distance/2+")*theta(-x+"+(2*i+1)*distance/2+")";
		}
		return "-x";//ret;
	}

	@Override
	public double getBorder(double E) {
		return potential.getBorder(E)+distance*(Anzahl+0.2);
	}

}
