import java.util.ArrayList;

public class Coulomb extends Potential {

	@Override
	public double getPotential(double x) {
		return -e*e/(4*pi*e0*Math.abs(x));
	}

	@Override
	public ArrayList<ArrayList<Double>> getPlot(double xRange) {
		ArrayList<ArrayList<Double>> ret = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> point = new ArrayList<>();
		double step = xRange/1000;
		
		for(int i = 0; i<1000; i++){
			point.clear();
			point.add(step*i);
			point.add(getPotential(step*i));
			
			ret.add(point);
		}
		
		return ret;
	}
	
	

}
