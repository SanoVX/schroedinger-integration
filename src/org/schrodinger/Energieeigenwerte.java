package org.schrodinger;
import java.util.ArrayList;

import org.schrodinger.gui.Loesungskurve;
import org.schrodinger.potential.Potential;


public class Energieeigenwerte {
	/**Potential */
	private Potential potential;
	/**aktuelle Energie*/
	private double E_current,E_max,E_min;
	/** Laenge der x-Achse */
	private double xrange = Math.pow(10,-6);
	/** Wurde bereits gesucht*/
	private boolean searched;
	
	/**Loesung fuer die Wellenfunktion psi*/
	private Loesungskurve solution = new Loesungskurve();
	
	/**Speicher fuer den Loesungsweg*/
	private ArrayList<Loesungskurve> loesungsschritte;
	
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
		
		searched = false;
	}
	
	/**
	 * Fuehrt die Berechung des naechsten Energieeigenwerts aus
	 * @return Energiewert gefunden, oder nicht
	 */
	public boolean step(){
		searched = false;
		E_current += 0.00001*e;
		double E_start = E_current;
		
		loesungsschritte = new ArrayList<>();
		//loesungsschritte.clear();
		ArrayList<Loesungskurve> loesungsblock = new ArrayList<>();
		
		boolean first = true;
		
		for(int i = 1; i< Einstellungen.accuracy; i++){
			int change_sign = (-1)*recursion();
			while(recursion()!=change_sign){
				E_current += Math.pow(10,-i)*e;
				if(E_current>E_max){
					solution = null;
					loesungsschritte = null;
					return false;
				}

				for(int j=0;j<solution.size();j++){
					if(solution.get(j).get(1)>1E-7){
						solution.remove(j);
					}
				}
				

								
				/*for(int j = 0; j< solution.size(); j++){
					solution.get(j).set(1, solution.get(j).get(1)+E_current/e);
				}*/

				solution.setEnergie(E_current);
				
				if(first){
					first = false;
				}else{
					loesungsblock.add(solution);
				}
				
			}
			int size = solution.size();
			
			for(int j=size-1;j>size-100 && j>1;j--){
				solution.remove(j);
			}
			
			solution.cutoff(0.1, potential.getBorder(E_current));
						
			solution.normalizeMaximum();
			
			solution.setEnergie(E_current);
			
			if(loesungsblock.size()>4){
				int size2 = loesungsblock.size();
				for(int k=size2-2; k>=1;k--){
					if(k%4 != 0){
						loesungsblock.remove(k);
					}
				}
			}

			loesungsschritte.addAll(loesungsblock);
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
	

	/**
	 * Integration fuer einen Energiewert
	 * @return -1 psi geht gegen -unendlich;
	 * 			1 psi geht gegen +unendlich
	 */
	private int recursion(){
		double border = potential.getBorder(E_current);
		Loesungskurve temp = new Loesungskurve();
		//xrange = -e/(4*pi*e0*E);
		double step = xrange/Einstellungen.steps;
		//Anfangsbedingungen
		ArrayList<Double> Sx = new ArrayList<>(); // Liste mit x,y koordinaten der punkte 
		
		if(Einstellungen.ungerade){
			Sx.add(0.); Sx.add(0.); // initialisiere ersten Punkt auf 0/0
			temp.add(Sx);				// fÃ¯Â¿Â½gt punkt in liste f hinzu
			Sx = new ArrayList<>();
			Sx.add(step); Sx.add(1.0); // initialisiere zweiten punkt auf step/step
			temp.add(Sx);
		}else{
			Sx.add(-step/2); Sx.add(1.); // initialisiere ersten Punkt auf 0/0
			temp.add(Sx);				// fÃ¯Â¿Â½gt punkt in liste f hinzu
			Sx = new ArrayList<>();
			Sx.add(step/2); Sx.add(1.); // initialisiere zweiten punkt auf step/step
			temp.add(Sx);
		}
		
		for(int i = 1; i < Einstellungen.steps+1; i++){

			double x = step*(i+1/2); // berechnet x koordinate des nÃ¯Â¿Â½chsten punktes
			Sx = new ArrayList<>();
			double y = Qi(x,step)*temp.get(i).get(1)-temp.get(i-1).get(1);

			Sx.add(x);
			Sx.add(y);
			temp.add(Sx);

			if(x>border){
				if(y>Einstellungen.Amplitudengrenze){
					solution = temp;
					return 1;
				}else if (y<-Einstellungen.Amplitudengrenze){
					solution = temp;
					return -1;
				}
			}else if(x>border-3*step){
				if(y>Einstellungen.Amplitudengrenze*1E-4){
					Einstellungen.Amplitudengrenze = 1E10*y;
				}
			}
		}
		return 0;
	}	
	
	
	
	/**
	 * Berechnung der Proportionalitaetskonstanten
	 * @param x Position zur Auswertung
	 * @param step Schrittweite bei der Integration
	 * 
	 * @return Qi Porportionalitaetskonstante
	 */
	private double Qi(double x, double step){
		return 2-step*step*8*pi*pi*u*(E_current-potential.getPotential(x))/(h*h);
	}
	
	/**
	 * Gibt Energiewert zurueck
	 * @return: Energiewert, falls bereits gesucht
	 * 			-infinity falls noch nicht gesucht
	 */
	public double getEnergy(){
		if(searched){
			return E_current;
		}else{
			return Double.POSITIVE_INFINITY;
		}
	}
	
	/**
	 * Gibt Loesungskurve zurueck
	 * @return: Loesungskurve, falls bereits gesucht;
	 * 			<code>null</code>, falls noch nicht gesucht
	 */
	public Loesungskurve getSolution(){
		if(searched){
			solution.mirror(Einstellungen.ungerade);
			return solution;
		}else{
			return null;
		}
	}
	
	
	/**
	 * Setzt die Energiewerte auf den Anfang zurueck, z.B. vor Beginn einer neuen suche
	 * 
	 */
	public void reset(){
		E_current = E_min;
		searched = false;
	}
	
	/**
	 * Gibt alle Loesungsschritte aus
	 * @return Arraylist von Loesungskurven
	 */
	public ArrayList<Loesungskurve> gibloesungsschritte(){
		if(searched){
			return loesungsschritte;
		}else{
			return null;
		}
	}
	
}
