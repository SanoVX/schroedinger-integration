public class Einstellungen {
	//Konstanten
	/**Planck'sches Wirkungsquantum*/
	public static double h = 6.626070040*Math.pow(10, -34);
	/**Masse des Elektrons*/
	public static double u = 9.10938356*Math.pow(10,-31);
	/**Elementarladung*/
	public static double e = 1.6021766208*Math.pow(10,-19);
	/**elektrische Permittivitaet*/
	public static double e0 = 8.85418781762*Math.pow(10,-12);

	
	//Einstellungen zur Anzeige
	/**Soll die Eigenwertberechnung angezeigt werden*/
	public static boolean showCalculation = true;
	
	//Einstellung fuer Berechnung
	/**maximale Energie fuer die Suche*/
	public static double E_max = 0.0;
	/**minimale Energie fuer die Suche*/
	public static double E_min = -20*e;
	/**maximale Anzahl an Energieniveaus*/
	public static int maxNiveaus = 4;
	/**Grenze bei der Berechnung der Amplitude der Eigenfunktionen*/
	public static double Amplitudengrenze = 1E4;
	/**Genauigkeit der Energieeigenwerte*/
	public static int accuracy = 14;
	/**Schritte, in die die xragne unterteilt wird*/
	public static double steps = 1E6;
	/**ungerade Funktionen?*/
	public static boolean ungerade = true;
	/**gerade und ungerade Niveaus?*/
	public static boolean alleNiveaus = false;

	
	//Steuerung des Hauptfensters
	/**Zaehler fuer die berechneten Niveaus*/
	public static int berechneteNiveaus = 0;
	/**Wurden alle Niveaus gezeichnet?*/
	public volatile static boolean allesGezeichnet = false;
	
}
