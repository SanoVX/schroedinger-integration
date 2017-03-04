import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth();
	int height = (int) screenSize.getHeight();
	int rest = 0;
	//time
	int calcTime = 10;
	ArrayList<CoordinateSystem> ks = new ArrayList<>();
	//while loop
	boolean finished = false;
	boolean init = false;
	boolean firstPaint = true;
	boolean simulated = false;
	
	int funktNr = 1;
	int currRange = 0;
	int s = 0;
	
	public ArrayList<ArrayList<Double>> copyList(ArrayList<ArrayList<Double>> funkt , int currRange){
		ArrayList<ArrayList<Double>> rfunkt = new ArrayList<>();
		for(int i = 0; i < funkt.size() && i < currRange; i++){
			rfunkt.add(funkt.get(i));
			//System.out.println(rfunkt.size() + " " + currRange);
		}
		return rfunkt;
		
	}
	
	public void funktionCleaner(){
		if(ks.size()==0){
			return;
		}
		if(ks.get(0).measure.size() > 20){
			ks.get(0).measure.remove(0);
		}
	}
	
	public void simulate(Graphics2D g2d){

		if(currRange == 0){
			funktNr += 1;
		}		
		if(ks.size()==0){
			return;
		}
		if(ks.get(0).simulation.get(s).size() < funktNr){
			ks.get(1).addMeasures(ks.get(1).solution.get(s));
			if(s < ks.get(1).solution.size() -1){
				s += 1;
			}else{
				simulated = true;
			}
			funktNr = 1;
			ks.get(0).measure.clear();
			ks.get(0).resetRange();
		}
		currRange += calcTime;
		for(int j = 0; j < ks.get(0).simulation.get(s).size() && j < funktNr; j++){
			ArrayList<ArrayList<Double>> add = copyList(ks.get(0).simulation.get(s).get(funktNr -1), currRange);
			if(ks.get(0).simulation.get(s).get(funktNr -1).size() < currRange){
				currRange = 0;
			}
			ks.get(0).addMeasures(add);
		}
		
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(!simulated){
			simulate(g2d);
		}

		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // sleeping time
		
		if(ks != null){
			for(int i = 0; i < ks.size(); i++){
				ks.get(i).drawFunktions(ks.get(i).funktions, g2d);
				ks.get(i).drawMeasure(false,g2d);
				ks.get(i).drawKs(g2d);
			}
		}

		//funktionCleaner();

		
	}
	


	/*public void plot() throws InterruptedException{
		
		while(!simulated){
			this.repaint();
			Thread.sleep(100); // sleeping time
		}
	}*/
}