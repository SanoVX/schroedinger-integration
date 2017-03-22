import java.util.ArrayList;

import javax.swing.JOptionPane;

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



	@Override
	public String gibFunktion() {
		return breite+"*x^2+"+tiefe/e;
	}



	@Override
	public double getBorder(double E) {
		return Math.sqrt((E-tiefe)/breite);
	}

}
