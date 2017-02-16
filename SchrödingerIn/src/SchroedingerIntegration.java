import java.util.ArrayList;

public class SchroedingerIntegration {
	public static double step = Math.pow(10,-11);
	public static double xrange = Math.pow(10,-8);
	public static double h = 6.626*Math.pow(10, 34); // wirkungsquantum
	public static double u = 9.1*Math.pow(10,-31); //elementarmasse
	public static double e = 1.6*Math.pow(10,-19); //elementarladung
	public static double pi = Math.PI; 
	public static double E = -13.7; //startenergie

	public static double e0 = 8.8*Math.pow(10,-12);
	static ArrayList<ArrayList<Double>> f = new ArrayList<>();
	
	public static void recursion(Game g){
		xrange = -e/(4*pi*e0*E);
		step = xrange/10000;
		//Anfangsbedingungen
		ArrayList<Double> Sx = new ArrayList<>(); // Liste mit x,y koordinaten der punkte 
		Sx.add(-step/2); Sx.add(-(1-ti(-1))*1.0); // initialisiere ersten Punkt auf 0/0
		f.add(Sx);				// f�gt punkt in liste f hinzu
		Sx = new ArrayList<>();
		Sx.add(step/2); Sx.add((1-ti(1))*1.0); // initialisiere zweiten punkt auf step/step
		f.add(Sx);

		for(int s = 0; s < (int)(xrange/step); s++){
			int i = s+1;
			double x = f.get(i).get(0) + step*i; // berechnet x koordinate des n�chsten punktes
			Sx = new ArrayList<>();
			double y = (2+10*ti(i))/(1-ti(i))-1/Ri(i-1); // berechnet y kooordinate des n�chsten Punktes mit der Rekursionsformel
			Sx.add(x);
			Sx.add(y);
			f.add(Sx);
		}
		
		
	}	
	
	public static double ti(int i){
		double t = -2*u*e/(12*4*pi*pi)*(E - V(i));
		return t;
	}

	public static double fi(int i){
		double fi = (1-ti(i))* f.get(i).get(1);
		return fi;
	}
	
	public static double Ri(int i){
		double ri = fi(i+1)/fi(i);
	return ri;	
	}
	public static double V(int i){
		double v = e/(4*pi*e0*Math.abs((i+0.5)*step));
		
		
		//e/(4*pi*e0*(i+1)*step)
		
		return v;
	}
	
	
	public static void main(String[] args) throws InterruptedException{
		Game g = new Game();
		g.drawpoints = false; // Befehl sodass die Punkte des Plots miteinander verbunden werden
		for(int i = 0; i < 1; i++){
			recursion(g);
			g.addMeasures(f); // f�gt f zu den plottbaren funktionen hinzu
			E = -13.6;
			f.clear();

			step = step/((double)10);
		}
		g.plot();
		
	}
}
