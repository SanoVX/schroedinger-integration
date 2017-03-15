import java.util.ArrayList;

import javax.swing.JProgressBar;

public class SchroedingerIntegration {
	
	private double e = Einstellungen.e;
	
	public static Potential potential = new Coulomb(Einstellungen.e);

	private Game g;
	private Thread t1;

	public SchroedingerIntegration(Game g) {
		this.g = g;
	}
	
	
	public ArrayList<Double> run() throws InterruptedException{
		Einstellungen.berechneteNiveaus = 0;
		ArrayList<Double> energies = new ArrayList<>();
		int xsize = g.width*3/8;
		int ysize = g.height*3/8;
		
		int anzahlks = 2;
		for(int i = 0; i < anzahlks; i++ ){
			CoordinateSystem k = new CoordinateSystem(g,g.width/2*i + g.width*1/16, g.height/2 - ysize/2, xsize, ysize);
			g.ks.add(k);
			k.drawpoints = false;
			if(true){// growing range only in left coordinate system
				k.growingrange = true;
			}
			k.plotThickness = 0;
			g.calcTime = 50;
			if(i == anzahlks -1){
				k.headline = "Energieniveaus";
			}else{
				k.headline = "Suche nach Energieniveaus";
			}
			k.xlabel = "Abstand des Kerns in m";
			k.ylabel = "Energie in eV";
			//Funktion f = new Funktion(k, "-q/(4*pi*e0*abs(x))", false);
			//k.funktions.add(f);
		}

		//numerische integration
		Energieeigenwerte E = new Energieeigenwerte(potential, Einstellungen.E_min, Einstellungen.E_max);
		E.step();
		int count = 0;
		do{			
			ArrayList<ArrayList<ArrayList<Double>>> l = E.gibloesungsschritte().get(0);

			if(l.size() > 1){
				//Beschraenkung der Loesungsschritte
				int size = l.size();
				for(int j=size-2; j>=0; j-- ){
					if(j%(int)(size/15.0+1) != 0){
						l.remove(j);
					}
				}
			}
	
			if(l.size() == 0){
				System.exit(0);
			}
			
			g.ks.get(0).simulation.add(l);

			System.out.println(E.getEnergy()/e);
			g.ks.get(1).solEnergy.add(E.getEnergy()/e);	
			g.ks.get(1).solution.add(E.getSolution());
			energies.add(E.getEnergy()/e);

			
			count ++;
			Einstellungen.berechneteNiveaus++;
		}while(E.step()&&count<Einstellungen.maxNiveaus);
			
			t1 = new Thread(){
			public void run(){
				long initsleeptime = 100;
				long sleeptime = 100;
				int i = 0;
				long calctime = 0;
				while(/*!g.simulated&&*/!isInterrupted()){
					if(i == 0){
					long startTime = System.currentTimeMillis();
					g.repaint();
					long endTime = System.currentTimeMillis();
					calctime = (endTime - startTime);
					i += 1;
					}else{
						long startTime = System.currentTimeMillis();
						g.repaint();
						long endTime = System.currentTimeMillis();
						long calculated = (endTime - startTime);
						sleeptime = initsleeptime + calctime - calculated;
						if(sleeptime < 0){
							sleeptime = 0;
						}
					}
					
					try {
						//System.out.println(sleeptime);
						Thread.sleep(sleeptime); // sleeping time
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
				Einstellungen.allesGezeichnet = true;
				g.repaint();
			}
		};
		t1.start();

		return energies;
	}


	public void stop() {
		t1.interrupt();
	}
	
	public void clear(){
		if(t1.isAlive()){
			t1.interrupt();
		}
		g.ks.clear();
		g.currRange = 0;
		g.s = 0;
		g.funktNr = 1;
	}

}