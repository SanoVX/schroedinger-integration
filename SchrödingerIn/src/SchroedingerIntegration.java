import java.util.ArrayList;

import javax.swing.JOptionPane;
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
		clear();
		Einstellungen.berechneteNiveaus = 0;
		ArrayList<Double> energies = new ArrayList<>();

		int dis = (int)(1/10.0*g.width);
		int anzahlks = 2;
		for(int i = 0; i < anzahlks; i++ ){
			CoordinateSystem k = new CoordinateSystem(g, (g.width-dis)/2*i,(g.width-dis)/2*(i+1),0,(int)(g.height*9/10.0), 1,7,1,3.5);
			g.ks.add(k);
			k.drawpoints = false;
			k.plotThickness = 0;
			g.calcTime = 50;
			if(i == anzahlks -1){
				k.headline = "Energieniveaus";
			}else{
				k.headline = "Suche nach Energieniveaus";
			}
			k.xlabel = "x Position in m";
			k.ylabel = "Energie in eV";

			
			System.out.println("potential " + potential.gibFunktion());
			if(potential.gibFunktion() != null){
			Funktion f = new Funktion(k, potential.gibFunktion(), false);
			k.funktions.add(f);
			}
			
		}
		
		int count = 0;

		if(Einstellungen.alleNiveaus){
			Einstellungen.ungerade = false;
			//numerische integration
			Energieeigenwerte E = new Energieeigenwerte(potential, Einstellungen.E_min, Einstellungen.E_max);
			if(!E.step()){
				JOptionPane.showMessageDialog(null, "Keine Eigenwerte gefunden!", "Error", JOptionPane.ERROR_MESSAGE);
				Einstellungen.allesGezeichnet = true;
				return null;
			}
			do{			
				if(E.gibloesungsschritte()!= null){
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
				}
				
				System.out.println(E.getEnergy()/e);
				g.ks.get(1).solEnergy.add(E.getEnergy()/e);	
				g.ks.get(1).solution.add(E.getSolution());
				energies.add(E.getEnergy()/e);

			
				count ++;
				Einstellungen.berechneteNiveaus++;
			}while(E.step()&&count<Einstellungen.maxNiveaus/2);
			Einstellungen.ungerade  = true;
		}
		Energieeigenwerte E = new Energieeigenwerte(potential, Einstellungen.E_min, Einstellungen.E_max);
		if(!E.step()){
			JOptionPane.showMessageDialog(null, "Keine Eigenwerte gefunden!", "Error", JOptionPane.ERROR_MESSAGE);
			Einstellungen.allesGezeichnet = true;
			return null;
		}
		do{			
			if(E.gibloesungsschritte()!= null){
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
			}

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
				while(!isInterrupted()){
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
					
					if(g.simulated){
						Einstellungen.allesGezeichnet = true;
					}
					
					try {
						Thread.sleep(sleeptime); // sleeping time
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
	
	public void clear(){
		if(t1!=null&&t1.isAlive()){
			t1.interrupt();
		}
		g.ks.clear();
		g.currRange = 0;
		g.s = 0;
		g.funktNr = 1;
		g.simulated = false;
	}

}