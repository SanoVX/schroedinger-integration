import java.util.ArrayList;

public class SchroedingerIntegration {

	public static double h = 6.626070040*Math.pow(10, -34); // wirkungsquantum
	public static double u = 9.10938356*Math.pow(10,-31); //elementarmasse
	public static double e = 1.6021766208*Math.pow(10,-19); //elementarladung

	public static double e0 = 8.85418781762*Math.pow(10,-12);

	
	
	public static void main(String[] args) throws InterruptedException{
		Game g = new Game();
		g.drawpoints = false; // Befehl sodass die Punkte des Plots miteinander verbunden werden
		g.growingrange = true;
		g.plotThickness = 2;
		g.calcTime = 5;
		Energieeigenwerte E = new Energieeigenwerte(new Coulomb(), -16*e, -0.1*e);
		for(int i = 0; i<5;i++){
			E.step();
		
			System.out.println(E.getEnergy()/e);
			g.addEnergy(E.getEnergy()/e);			
			g.addMeasures(E.getSolution());
		}
		g.addMeasures(new Coulomb().getPlot(Math.pow(10,-8)));
		g.plot();
		
	}

}
