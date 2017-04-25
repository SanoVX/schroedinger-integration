package org.schrodinger.potential;
import java.util.ArrayList;

import org.schrodinger.gui.Funktion;


public class UserFunction extends Potential {
	
	private Funktion function;
	
	public UserFunction(Funktion f){
		this.function = f;
	}
	
	
	
	@Override
	public double getPotential(double x) {
		return function.getY(x);
	}

	@Override
	public ArrayList<ArrayList<Double>> getPlot(double xRange) {
		return null;
	}



	@Override
	public String gibFunktion() {
		return function.funktion;
	}



	@Override
	public double getBorder(double E) {
		return 0;
	}

}
