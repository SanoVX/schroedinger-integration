import java.util.ArrayList;
//
public class SchroedingerIntegrationLeuchs {
	public static double step = Math.pow(10,-11);
	public static double xrange = Math.pow(10,-9);
	public static double h = 6.626*Math.pow(10, -34); // wirkungsquantum
	public static double u = 9.1*Math.pow(10,-31); //elementarmasse
	public static double e = 1.6*Math.pow(10,-19); //elementarladung
	public static double pi = Math.PI; 
	public static double E = -13.6*e; //startenergie

	public static double e0 = 8.8*Math.pow(10,-12);
	static ArrayList<ArrayList<Double>> f = new ArrayList<>();
	
	public static void recursion(Game g){
		//xrange = -e/(4*pi*e0*E);
		step = xrange/10000;
		//Anfangsbedingungen
		ArrayList<Double> Sx = new ArrayList<>(); // Liste mit x,y koordinaten der punkte 
		Sx.add(-step/2); Sx.add(0.0); // initialisiere ersten Punkt auf 0/0
		f.add(Sx);				// f�gt punkt in liste f hinzu
		Sx = new ArrayList<>();
		Sx.add(step/2); Sx.add(1.0); // initialisiere zweiten punkt auf step/step
		f.add(Sx);

		for(int s = 0; s < (int)(xrange/step); s++){
			int i = s+1;
			double x = f.get(i).get(0) + step*i; // berechnet x koordinate des n�chsten punktes
			Sx = new ArrayList<>();
				double y = Qi(i)*f.get(i).get(1)-f.get(i-1).get(1);
				//double y = ((2+10*ti(i))/(1-ti(i))*fi(i)-fi(i-1))/(1-ti(i));
				//double y = (2+10*ti(i))/(1-ti(i))-1/Ri(i-1); // berechnet y kooordinate des n�chsten Punktes mit der Rekursionsformel
				Sx.add(x);
				Sx.add(y);
				f.add(Sx);

		}
		
		
	}	
	
	public static double Qi(int i){
		return 2-step*step*8*pi*pi*u*(E-V(i))/(h*h);
	}
	public static double V(int i){
		return -e*e/(4*pi*e0*Math.abs((i+0.5)*step));
	}
	
	
	public static void main(String[] args) throws InterruptedException{
		Game g = new Game();
		g.drawpoints = false; // Befehl sodass die Punkte des Plots miteinander verbunden werden
		for(int i = 0; i < 5; i++){
			recursion(g);
			g.addMeasures(f); // f�gt f zu den plottbaren funktionen hinzu
			E = (-13.6+i)*e;
			f.clear();

			step = step/((double)10);
		}
		g.plot();
		
	}
}
