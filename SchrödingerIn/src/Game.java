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
	
	int calcTime = 10;
	ArrayList<CoordinateSystem> ks = new ArrayList<>();
	
	boolean finished = false;
	boolean init = false;
	boolean firstPaint = true;
	boolean simulated = false;
	boolean end = false;
	boolean prepSimulationList = false;
	
	int funktNr = 1;
	int currRange = 0;
	int s = 0;
	
	public ArrayList<ArrayList<Double>> copyList(ArrayList<ArrayList<Double>> funkt , int currRange){
		ArrayList<ArrayList<Double>> rfunkt = new ArrayList<>();
		for(int i = 0; i < funkt.size() && i < currRange; i++){
			rfunkt.add(funkt.get(i));
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
	
	public void simulate(Graphics2D g2d){//
		
		if(ks.size()==0){
			return;
		}

		if(!Einstellungen.showCalculation || currRange == 0){
			funktNr += 1;
		}	
		if(ks.get(0).simulation.get(s).size() < funktNr+1){
			ks.get(1).addMeasures(ks.get(1).solution.get(s));
			ks.get(1).addEnergy(ks.get(1).solEnergy.get(s));
			if(s < ks.get(1).solution.size() -1){
				s += 1;
			}else{
				simulated = true;
				end = true;
			}
			funktNr = 1;
			ks.get(0).measure.clear();
		}
		currRange += calcTime;
		for(int j = 0; j < ks.get(0).simulation.get(s).size() && j <= funktNr; j++){
			int pos = ks.get(0).simulation.get(s).get(ks.get(0).simulation.get(s).size() -1).size();
			ks.get(0).xmax = (ks.get(0).simulation.get(s).get(funktNr -1).get(ks.get(0).simulation.get(s).get(funktNr -1).size()-1).get(0));
			ArrayList<ArrayList<Double>> add = copyList(ks.get(0).simulation.get(s).get(funktNr -1), currRange);
			if(ks.get(0).simulation.get(s).get(funktNr -1).size() < currRange){
				currRange = 0;
			}
			if(ks.get(0).measure.size() > 0 && currRange != 0){
				ks.get(0).measure.remove(ks.get(0).measure.size()-1);
			}
			ks.get(0).addMeasures(add);
			
		}
		
	}
	
	
	public void paintComponent(Graphics g) {
		System.out.println("runnign");
		super.paintComponent(g);
		if(ks.size() > 0){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(!prepSimulationList){//prepare simulation
			if(ks.size() > 0){
			for(int s = 0; s < ks.get(0).simulation.size(); s++){
				for(int i = 0; i < ks.get(0).simulation.get(s).size(); i++){
					normalize(ks.get(0).simulation.get(s).get(i));
				}
			}
			ks.get(0).growingrange = false;
			ks.get(0).ymin = -1;
			ks.get(0).ymax = 1;
			ks.get(0).xmin = 0;
			prepSimulationList = true;
			for(int i = 1; i < ks.get(0).simulation.size(); i++){
				ArrayList<ArrayList<Double>> k = ks.get(0).simulation.get(i).get(0);
				ks.get(0).simulation.get(i).remove(0);
				ks.get(0).simulation.get(i-1).add(ks.get(1).solution.get(i-1));
				
			}
			}
		}
		
		if(!simulated){
			simulate(g2d);
			if(ks.size() > 0){
			ks.get(0).yaxis = true;
			ks.get(0).legend = new String[1];
			ks.get(0).legend[0] = "Rechengeschwindigkeit = "+ calcTime;
			}
		}

		if(simulated){
			if(end){
				ks.remove(0);
				end = false;
			}
			ks.get(0).changeSize(0,width-(int)(1/10.0*width), 0, (int)(height*9/10.0), 1,8,1,6);
			
		}
		if(ks != null){
			for(int i = 0; i < ks.size(); i++){
				ks.get(i).drawFunktions(ks.get(i).funktions, g2d);
				ks.get(i).drawMeasure(false,g2d);
				ks.get(i).drawKs(g2d);
			}
		}	
	}
	}
	
	public void normalize(ArrayList<ArrayList<Double>> list){
		double max = 0;
		for(int i = 0; i < list.size()-3; i++){
			if(Math.abs(list.get(i).get(1)) < Math.abs(list.get(i+1).get(1)) && Math.abs(list.get(i+2).get(1)) < Math.abs(list.get(i+1).get(1))){
				max = list.get(i+1).get(1);
			}
		}
		max = Math.abs(max);
		if(max > 0){
		for(int i = 0; i < list.size(); i++){
			list.get(i).set(1, list.get(i).get(1)/max);
		}
		}
		
		
	}
}