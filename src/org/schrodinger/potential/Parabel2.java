package org.schrodinger.potential;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.commons.math3.util.FastMath;

public class Parabel2 extends Potential {

	double tiefe, breite;
	
	public Parabel2(double tiefe, double breite){
		this.tiefe = tiefe;
		this.breite = breite;
	}
	
	
	
	@Override
	public double getPotential(double x) {
		//return tiefe/FastMath.pow(breite, 4)*(x-breite)*(x-breite)*(x+breite)*(x+breite);
		return 1-FastMath.pow(x*1E10,2)/6+FastMath.pow(x*1E10,4)/120;
	}

	@Override
	public ArrayList<ArrayList<Double>> getPlot(double xRange) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String gibFunktion() {
		return breite/e+"*x*x+"+tiefe/e;
	}



	@Override
	public double getBorder(double E) {
		return Math.sqrt((E-tiefe)/breite);
	}

}
