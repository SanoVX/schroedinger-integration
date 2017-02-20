import java.util.ArrayList;


public class Energieeigenwerte {
	private Potential potential;
	private double E_current,E_max,E_min;
	private int accuracy = 15;
	private double xrange = Math.pow(10,-8);
	private boolean searched;
	
	private ArrayList<ArrayList<Double>> solution = new ArrayList<>();
	
	public static double h = SchroedingerIntegration.h; // wirkungsquantum
	public static double u = SchroedingerIntegration.u; //elementarmasse
	public static double e = SchroedingerIntegration.e; //elementarladung
	public static double pi = Math.PI; 
	public static double e0 = SchroedingerIntegration.e0;
	
	public Energieeigenwerte(Potential potential, double E_min, double E_max){
		this.potential = potential;
		
		this.E_max = E_max;
		this.E_min = E_min;
		this.E_current = E_min;
		
		searched = false;
	}
	
	public void step(){
		searched = false;
		E_current += 0.00001*e;
		for(int i = 1; i< accuracy; i++){
			int change_sign = (-1)*recursion();
			while(recursion()!=change_sign){
				E_current += Math.pow(10,-i)*e;
				if(E_current>E_max){
					break;
				}
			}
			E_current -= Math.pow(10,-i)*e;
			searched = true;
		}
	}
	
	
	private int recursion(){
		ArrayList<ArrayList<Double>> temp = new ArrayList<>();
		//xrange = -e/(4*pi*e0*E);
		double step = xrange/10000;
		//Anfangsbedingungen
		ArrayList<Double> Sx = new ArrayList<>(); // Liste mit x,y koordinaten der punkte 
		Sx.add(0.); Sx.add(0.); // initialisiere ersten Punkt auf 0/0
		temp.add(Sx);				// f�gt punkt in liste f hinzu
		Sx = new ArrayList<>();
		Sx.add(step); Sx.add(1.0); // initialisiere zweiten punkt auf step/step
		temp.add(Sx);
		
		double max = 0;
		for(int i = 1; i < (int)(xrange/step)+1; i++){

			double x = temp.get(i).get(0) + step*i; // berechnet x koordinate des n�chsten punktes
			Sx = new ArrayList<>();
			double y = Qi(step*i,step)*temp.get(i).get(1)-temp.get(i-1).get(1);
				
			if(Math.abs(y) > max){
					max = Math.abs(y);
			}
			Sx.add(x);
			Sx.add(y);
			temp.add(Sx);

				
			if(y>100){
				solution = temp;
				return 1;
			}else if (y<-100){
				solution = temp;
				return -1;
			}
		}
		for(int i = 0; i < temp.size(); i++){

			//System.out.println("max" + " " + max + " " + temp.get(i).set(1,temp.get(i).get(1)));
			temp.get(i).set(1, temp.get(i).get(1)/(max/4));
			temp.get(i).set(1,temp.get(i).get(1) + E_current/e);
		}
		return 0;
	}	
	
	private double Qi(double x, double step){
		return 2-step*step*8*pi*pi*u*(E_current-potential.getPotential(x))/(h*h);
	}
	
	public double getEnergy(){
		if(searched){
			return E_current;
		}else{
			return Double.POSITIVE_INFINITY;
		}
	}
	
	public ArrayList<ArrayList<Double>> getSolution(){
		if(searched){
			return solution;
		}else{
			return null;
		}
	}
	
	public boolean setAccuracy(int accuracy){
		if(searched){
			this.accuracy = accuracy;
			return true;
		}else{
			return false;
		}
	}
	
	
	
}
