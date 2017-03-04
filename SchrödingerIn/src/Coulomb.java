import java.util.ArrayList;

public class Coulomb extends Potential {

	private double q_c;

	public Coulomb(double q_c){
		super();
		this.q_c = q_c;
	}
	
	@Override
	public double getPotential(double x) {
		return -q_c*e/(4*pi*e0*Math.abs(x));
	}

	@Override
	public ArrayList<ArrayList<Double>> getPlot(double xRange) {
		ArrayList<ArrayList<Double>> ret = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> point = new ArrayList<>();
		double step = xRange/1000;
		
		for(int i = 10; i<1000; i++){
			point.clear();
			point.add(step*i);
			point.add(getPotential(step*i)/e);
			
			ret.add(point);
		}
		
		return ret;
	}
	
	

}
