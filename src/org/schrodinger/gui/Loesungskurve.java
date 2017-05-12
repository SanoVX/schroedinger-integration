package org.schrodinger.gui;
import java.util.ArrayList;

import org.schrodinger.Einstellungen;

public class Loesungskurve extends ArrayList<ArrayList<Double>> {
	/**Energiewert der Loesungskurve*/
	private double Energie;
	
	/**
	 * Erzeugt eine Loesungskurve mit Default-Energie-Wert 0
	 */
	public Loesungskurve(){
		this(0);
	}
	
	/**
	 * Erzeugt eine Loesungskurve mit Energiewerte Energie
	 * 
	 * @param Energie Energie der Loesungskurve
	 */
	public Loesungskurve(double Energie){
		super();
		this.Energie = Energie;
	}
	
	
	/**
	 * Gibt den aktuellen Energiewerte der Loesungskurve zurueck
	 * @return Energiewerte der Loesungskurve
	 */
	public double getEnergie(){
		return Energie;
	}

	/**
	 * Setzt den Energiewert der Loesungskurve
	 * @param Energie Energiewert der Loesungskurve
	 */
	public void setEnergie(double Energie) {
		this.Energie = Energie;
	}
	
	/**
	 * Normiert die Loesungskurve durch Division durch das Maximum
	 * @param solution
	 */
	public void normalizeMaximum(){
		double max = 0;
		for(int i = 0; i<size();i++){
			if(Math.abs(get(i).get(1)) > max){
				max = Math.abs(get(i).get(1));
			}
		}
		for(int i = 0; i < size(); i++){
			get(i).set(1, get(i).get(1)/(max));
			//get(i).set(1,get(i).get(1) + Energie/e);
		}
	}
	
	/**
	 * Schneidet das Ende passend ab
	 * @param epsilon der Grenzabstand
	 * @param border Ab wann soll geprueft werden
	 */
	public void cutoff(double epsilon, double border){
		int counter = 0;
		
		for(int i = 0; i < size(); i++){
			if(counter >50||get(i).get(1)>1E4){
				for(int j = size()-1; j > i; j--){
					remove(j);
				}
				break;
			}
			if(get(i).get(0)>border && Math.abs(get(i).get(1))<epsilon){
				counter++;
			}else{
				counter = 0;
			}
		}
	}
	
	/**
	 * get nur mit Energie
	 */
	public ArrayList<Double> getMitEnergie(int index){
		ArrayList<Double> ret = super.get(index);
		ret.set(1, ret.get(1)+Energie/Einstellungen.e);
		return ret;
	}
	
	/**
	 * Spiegelt die Loesungskurve an der y-Achse je nach parameter ungerade.
	 * @param ungerade Ist es eine Ungerade Spiegelung?
	 */
	public void mirror(boolean ungerade){
		for(int i = 1; i<size();i+=2){
			ArrayList<Double>  S = new ArrayList<>();
			S.add(-get(i).get(0));
			if(ungerade){
				S.add(-get(i).get(1));
			}else{
				S.add(get(i).get(1));
			}
			add(0,S);
		}
	}
		
	/**
	 * Rueckgabe einer ArrayList mit Energie
	 * @return ArrayList mit Energie
	 */
	public ArrayList<ArrayList<Double>> toArrayList(){
		ArrayList<ArrayList<Double>> ret = new ArrayList<>();
		for(int i=0; i<size();i++){
			ret.add(getMitEnergie(i));
		}
		return ret;
	}
	
	/**
	 * Rueckgabe einer ArrayList ohne Energiewert
	 * @return ArrayList ohne Energie
	 */
	public ArrayList<ArrayList<Double>> toArrayListOhneEnergie(){
		ArrayList<ArrayList<Double>> ret = new ArrayList<>();
		for(int i=0; i<size();i++){
			ret.add(get(i));
		}
		return ret;
	}

	public void divideR() {
		for (ArrayList<Double> e: this){
			e.set(1,e.get(1)/e.get(0));
		}
		normalizeMaximum();
	}


}
