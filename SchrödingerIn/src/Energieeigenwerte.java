import java.util.ArrayList;


public class Energieeigenwerte {
	//Variablendefinition
	private Potential potential;
	private double E_current,E_max,E_min;
	private double xrange = Math.pow(10,-7);
	private boolean searched;
	private boolean ungerade;
	
	//Variable fuer die Loesungskurve
	private ArrayList<ArrayList<Double>> solution = new ArrayList<>();
	
	private ArrayList<ArrayList<ArrayList<ArrayList<Double>>>> loesungsschritte = new ArrayList<>();
	
	//Import der Konstanten
	private double h = Einstellungen.h; // wirkungsquantum
	private double u = Einstellungen.u; //elementarmasse
	private double e = Einstellungen.e; //elementarladung
	private double pi = Math.PI; 
	private double e0 = Einstellungen.e0;
	
	//Konstruktor
	public Energieeigenwerte(Potential potential, double E_min, double E_max){
		this.potential = potential;
		
		this.E_max = E_max;
		this.E_min = E_min;
		this.E_current = E_min;
		
		ungerade = true;
		searched = false;
	}
	
	/*
	 * Fuehrt die Berechung des naechsten Energieeigenwerts aus
	 */
	public boolean step(){
		searched = false;
		E_current += 0.00001*e;
		double E_start = E_current;
		
		if(loesungsschritte == null){
			loesungsschritte = new ArrayList<>();
		}
		loesungsschritte.clear();
		ArrayList<ArrayList<ArrayList<Double>>> loesungsblock = new ArrayList<>();
		
		for(int i = 1; i< Einstellungen.accuracy; i++){
			int change_sign = (-1)*recursion();
			while(recursion()!=change_sign){
				E_current += Math.pow(10,-i)*e;
				if(E_current>E_max){
					solution = null;
					loesungsschritte = null;
					return false;
				}
				int size = solution.size();
				
				for(int j=size-1;j>size-400 && j>1;j--){
					solution.remove(j);
				}
				
				cutoff(solution);
				
				for(int j = 0; j< solution.size(); j++){
					solution.get(j).set(1, solution.get(j).get(1)+E_start);
				}

				loesungsblock.add(solution);
				
			}
			int size = solution.size();
			
			ArrayList<ArrayList<Double>>solutionTemp = new ArrayList<>();
			for(int a = 0; a<solution.size();a++){
				solutionTemp.add(new ArrayList<>(solution.get(a)));		
			}
						
			for(int j=size-1;j>size-700 && j>1;j--){
				solutionTemp.remove(j);
			}
			
			cutoff(solutionTemp);
	
			loesungsblock.add(solutionTemp);
			
			for(int j=size-1;j>size-100 && j>1;j--){
				solution.remove(j);
			}
			
			cutoff(solution);
			
			if(Einstellungen.normalizeIntegral){
				normalizeIntegral(solution);
			}else{
				normalizeMaximum(solution);
			}

			loesungsschritte.add(loesungsblock);
			loesungsblock = new ArrayList<>();
			E_current -= Math.pow(10,-i)*e;
		}
		
		searched = true;
		if(E_current>E_max){
			return false;
		}else{
			return true;
		}
	}
	
	/*
	 * Integration fuer einen Energiewert
	 */
	private int recursion(){
		ArrayList<ArrayList<Double>> temp = new ArrayList<>();
		//xrange = -e/(4*pi*e0*E);
		double step = xrange/Einstellungen.steps;
		//Anfangsbedingungen
		ArrayList<Double> Sx = new ArrayList<>(); // Liste mit x,y koordinaten der punkte 
		
		if(ungerade){
			Sx.add(0.); Sx.add(0.); // initialisiere ersten Punkt auf 0/0
			temp.add(Sx);				// fÃ¯Â¿Â½gt punkt in liste f hinzu
			Sx = new ArrayList<>();
			Sx.add(step); Sx.add(1.0); // initialisiere zweiten punkt auf step/step
			temp.add(Sx);
		}else{
			Sx.add(0.); Sx.add(1.); // initialisiere ersten Punkt auf 0/0
			temp.add(Sx);				// fÃ¯Â¿Â½gt punkt in liste f hinzu
			Sx = new ArrayList<>();
			Sx.add(step); Sx.add(1.2); // initialisiere zweiten punkt auf step/step
			temp.add(Sx);
		}
		
		for(int i = 1; i < Einstellungen.steps+1; i++){

			double x = step*i; // berechnet x koordinate des nÃ¯Â¿Â½chsten punktes
			Sx = new ArrayList<>();
			double y = Qi(step*i,step)*temp.get(i).get(1)-temp.get(i-1).get(1);

			Sx.add(x);
			Sx.add(y);
			temp.add(Sx);

				
			if(y>Einstellungen.Amplitudengrenze){
				solution = temp;
				return 1;
			}else if (y<-Einstellungen.Amplitudengrenze){
				solution = temp;
				return -1;
			}
		}
		return 0;
	}	
	
	private void normalizeIntegral(ArrayList<ArrayList<Double>> solution){
		double integral = 0;
		for(int i = 0; i<solution.size();i++){
			integral+=(solution.get(i).get(1))*(solution.get(i).get(1))*xrange/10000;
		}
		
		for(int i = 0; i < solution.size(); i++){
			solution.get(i).set(1, solution.get(i).get(1)/(integral*1E8));
			solution.get(i).set(1,solution.get(i).get(1) + E_current/e);
		}
	}
	
	private void normalizeMaximum(ArrayList<ArrayList<Double>> solution){
		double max = 0;
		for(int i = 0; i<solution.size();i++){
			if(Math.abs(solution.get(i).get(1)) > max){
				max = Math.abs(solution.get(i).get(1));
			}
		}
		for(int i = 0; i < solution.size(); i++){
			solution.get(i).set(1, solution.get(i).get(1)/(max));
			solution.get(i).set(1,solution.get(i).get(1) + E_current/e);
		}
	}
	
	public void cutoff(ArrayList<ArrayList<Double>> solution){
		double epsilon = 0.1;
		int counter = 0;
		
		for(int i = 0; i < solution.size(); i++){
			if(counter >10){
				for(int j = solution.size()-1; j > i; j--){
					solution.remove(j);
				}
				break;
			}
			if(Math.abs(solution.get(i).get(1))<epsilon){
				counter++;
			}else{
				counter = 0;
			}
		}
	}
	
	/*
	 * Berechnung der Proportionalitaetskonstanten
	 */
	private double Qi(double x, double step){
		return 2-step*step*8*pi*pi*u*(E_current-potential.getPotential(x))/(h*h);
	}
	
	/*
	 * Gibt Energiewert zurueck
	 * return: Energiewert, falls bereits gesucht
	 * 			-infinity falls noch nicht gesucht
	 */
	public double getEnergy(){
		if(searched){
			return E_current;
		}else{
			return Double.POSITIVE_INFINITY;
		}
	}
	
	/*
	 * Gibt Loesungskurve zurueck
	 * return: Loesungskurve, falls bereits gesucht
	 * 			null, falls noch nicht gesucht
	 */
	public ArrayList<ArrayList<Double>> getSolution(){
		if(searched){
			return solution;
		}else{
			return null;
		}
	}
	
	/*
	 * Wenn true uebergeben wird nach ungeraden funktionen gesucht ansonsten nach geraden
	 */
	public void setUngerade(boolean ungerade){
		this.ungerade = ungerade;
	}
	
	/*
	 * Setzt die Energiewerte auf den Anfang zurueck, z.B. vor Beginn einer neuen suche
	 * 
	 */
	public void reset(){
		E_current = E_min;
		searched = false;
	}
	
	/*
	 * Gibt alle Loesungsschritte aus
	 * Format:
	 * - 1. Iteration:  1.Kurve
	 * 					2.Kruve
	 * 					3.Kurve ...
	 * - 2. Iteration: 	1.Kurve
	 * 					2.Kurve ...
	 */
	public ArrayList<ArrayList<ArrayList<ArrayList<Double>>>> gibloesungsschritte(){
		if(searched){
			return loesungsschritte;
		}else{
			return null;
		}
	}
	
}
