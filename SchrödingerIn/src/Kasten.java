import java.util.ArrayList;

public class Kasten extends Potential {

	private double ground, height, width;
	
	public Kasten(double ground, double height, double width){
		this.ground = ground;
		this.height = height;
		this.width = width;
	}
	
	@Override
	public double getPotential(double x) {
		if(Math.abs(x)<= width/2){
			return ground;
		}else{
			return ground+height;
		}
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

	@Override
	public String gibFunktion() {
		return "theta(x-"+width+")*theta(x+"+width+")*(-"+height/e+")+"+height/e+"+"+ground/e;
	}
	
	

}
