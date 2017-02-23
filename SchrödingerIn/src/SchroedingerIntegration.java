import java.util.ArrayList;

public class SchroedingerIntegration {

	public static double h = 6.626070040*Math.pow(10, -34); // wirkungsquantum
	public static double u = 9.10938356*Math.pow(10,-31); //elementarmasse
	public static double e = 1.6021766208*Math.pow(10,-19); //elementarladung

	public static double e0 = 8.85418781762*Math.pow(10,-12);

	
	
	public static void main(String[] args) throws InterruptedException{
		Game g = new Game();
		int energylevels = 5;
		int xsize = g.width*3/8;
		int ysize = g.height*3/8;
		for(int i = 0; i < 2; i++ ){
			CoordinateSystem k = new CoordinateSystem(g,g.width/2*i + g.width*1/8/2, g.height/2 - ysize/2, xsize, ysize);
			g.ks.add(k);
			k.drawpoints = false; // Befehl sodass die Punkte des Plots miteinander verbunden werden
			k.growingrange = true;
			k.plotThickness = 1;
			g.calcTime = 100;
			k.xlabel = "Abstand des Kerns in m";
			k.ylabel = "Energie in eV";
			Funktion coulomb = new CoulombFunktion(k,0,0,false); 
			k.funktions.add(coulomb);
		}
		Energieeigenwerte E = new Energieeigenwerte(new Coulomb(), -16*e, -0.1*e);
		for(int i = 0; i < energylevels; i++){
			E.step();
			
			ArrayList<ArrayList<ArrayList<ArrayList<Double>>>> l = E.gibloesungsschritte();
			
			for(int j = 0; j< l.get(0).size();j++){

				g.ks.get(1).addEnergy(E.getEnergy()/e);	
				g.ks.get(1).addMeasures(l.get(0).get(j));
			}
			System.out.println(E.getEnergy()/e);
			g.ks.get(0).addEnergy(E.getEnergy()/e);	
			g.ks.get(0).addMeasures(E.getSolution());
		}
		g.plot();
		 /*	E.step();
		
		ArrayList<ArrayList<ArrayList<ArrayList<Double>>>> l = E.gibloesungsschritte();
		
		for(int i = 0; i< l.get(0).size();i++){
			g.addMeasures(l.get(0).get(i));
		}*/
	}

}
