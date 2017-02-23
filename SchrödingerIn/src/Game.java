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
	int calcTime = 100;
	ArrayList<CoordinateSystem> ks = new ArrayList<>();
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(ks != null){
			for(int i = 0; i < ks.size(); i++){
				ks.get(i).showRange += calcTime;
				ks.get(i).drawFunktions(ks.get(i).funktions, g2d);
				for(int j = 0; j < ks.get(i).measure.size(); j++){
					ks.get(i).drawMeasure(ks.get(i).measure.get(j), true,g2d,j);
	
				}
	
				ks.get(i).drawKs(g2d);
			}
		}
		}
	// method to plot whatever is inside the data and funktionlist
	public void plot() throws InterruptedException{
		JFrame frame = new JFrame("2-D Plot");
		frame.add(this);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDoubleBuffered(false);

		/*colorList = new Color[measure.size()];
		for(int i = 0; i < colorList.length; i++){
			colorList[i] = randomColor();
		}*/
		while(true){
			this.repaint();
			Thread.sleep(100); // sleeping time
		}
	}
}