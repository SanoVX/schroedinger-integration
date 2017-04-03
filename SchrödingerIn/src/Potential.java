import java.util.ArrayList;

public abstract class Potential {

	protected double h = Einstellungen.h; // wirkungsquantum
	protected double u = Einstellungen.u; //elementarmasse
	protected double e = Einstellungen.e; //elementarladung
	protected double pi = Math.PI; 
	protected double e0 = Einstellungen.e0;
		
	
	public Potential(){
		
	}
	
	/**
	 * Auswertung des Potentials an der Stelle x
	 * @param x x-Koordinate an der Ausgewertet wird
	 * @return Potential an der Stelle x
	 */
	public abstract double getPotential(double x);
	
	/**
	 * @deprecated Use {@link #gibFunktion()}
	 */
	public abstract ArrayList<ArrayList<Double>> getPlot(double xRange);

	/**
	 * Gibt eine String zum Zeichnen der Funktion zurueck
	 * @return String zum zeichnen der Funktion
	 */
	public abstract String gibFunktion();

	/**
	 * Gibt den x-Wert zurueck bis zu dem mindestens gerechnet werden muss
	 * @param E Energiewerte an dem das Potential betrachtet werden soll
	 * @return Grenze fuer die numerische Integration
	 */
	public abstract double getBorder(double E);
	
}
