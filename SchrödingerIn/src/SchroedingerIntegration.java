import java.util.ArrayList;

public class SchroedingerIntegration {
	
	private double e = Einstellungen.e;
	
	public static Potential potential = new Coulomb(Einstellungen.e);

	private Game g;
	private Thread t1;

	public SchroedingerIntegration(Game g) {
		this.g = g;
	}
	
	
	public ArrayList<Double> run() throws InterruptedException{
		ArrayList<Double> energies = new ArrayList<>();
		int xsize = g.width*3/8;
		int ysize = g.height*3/8;
		//erstellen der koordinatensysteme
		int anzahlks = 2;
		for(int i = 0; i < anzahlks; i++ ){
			CoordinateSystem k = new CoordinateSystem(g,g.width/2*i + g.width*1/16, g.height/2 - ysize/2, xsize, ysize);
			g.ks.add(k);
			k.drawpoints = false;
			if(true){// growing range only in left coordinate system
				k.growingrange = true;
			}
			k.plotThickness = 1;
			g.calcTime = 50;
			if(i == anzahlks -1){
				k.headline = "Energieniveaus";
			}else{
				k.headline = "Suche nach Energieniveaus";
			}
			k.xlabel = "Abstand des Kerns in m";
			k.ylabel = "Energie in eV";
		}

		//numerische integration
		Energieeigenwerte E = new Energieeigenwerte(potential, Einstellungen.E_min, Einstellungen.E_max);
		E.step();
		int count = 0;
		do{			
			ArrayList<ArrayList<ArrayList<Double>>> l = E.gibloesungsschritte().get(0);
			if(l.size() > 1){
				int size = l.size();
				for(int j=size-15; j>=0; j-- ){
						l.remove(j);
				}
			}
			//for(int s = 0; s < l.size(); s++){

					//g.ks.get(0).addEnergy(E.getEnergy()/e);	
					g.ks.get(0).simulation.add(l);
					
			//}
			System.out.println(E.getEnergy()/e);
			g.ks.get(1).addEnergy(E.getEnergy()/e);	
			g.ks.get(1).solution.add(E.getSolution());
			energies.add(E.getEnergy()/e);

			
			count ++;
		}while(E.step()&&count<Einstellungen.maxNiveaus);
			
			t1 = new Thread(){
			public void run(){
				while(!g.simulated&&!isInterrupted()){
					g.repaint();
					try {
						Thread.sleep(100); // sleeping time
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		};
		t1.start();

		return energies;
	}


	public void stop() {
		t1.interrupt();
	}

}
