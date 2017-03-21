public class Einstellungen {
	//Kosntanten
	public static double h = 6.626070040*Math.pow(10, -34); // wirkungsquantum
	public static double u = 9.10938356*Math.pow(10,-31); //elementarmasse
	public static double e = 1.6021766208*Math.pow(10,-19); //elementarladung
	public static double e0 = 8.85418781762*Math.pow(10,-12);
	
	//Einstellungen zur Anzeige
	public static boolean showCalculation = false;
	public static boolean normalizeIntegral = false;
	
	//Einstellung fuer Berechnung
	public static double E_max = 0.0;
	public static double E_min = -20*e;
	public static int maxNiveaus = 5;
	public static double Amplitudengrenze = 1E3;
	public static int accuracy = 10;
	public static double steps = 1E5;

	
	//Steuerung des Hauptfensters
	public static int berechneteNiveaus = 0;
	public static boolean allesGezeichnet = false;
	
}
