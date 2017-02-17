import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel{
	int rest = 0;
	int xsize = 1200;
	int ysize = 700;
	int width = 1920;
	int height = 1080;
	ArrayList<Funktion> funktions = new ArrayList<>();
	ArrayList<double[][]> measure = new ArrayList<>();
	ArrayList<Double> energy = new ArrayList<>();
	double xmin = 0;
	double xmax = 0;
	double ymin = 0;
	double ymax = 0;
	int recsize = 6; // size of rectangle representing the data point
	public String xlabel = " ";
	public int xlabelFontSize = 20;
	public String ylabel = " ";
	public int ylabelFontSize = 20;
	public String headline = " ";
	public int headlineFontSize = 20;
	public String text = "";
	public int textFontSize = 20;
	public String[] legend;
	public int legendFontSize = 20;
	boolean init = false;
	boolean drawpoints = true;
	Color[] colorList;
	
	// method to linear fit datas
	public void addEnergy(double e){
		energy.add(e);
		Funktion f = new Funktion(this,0,e,false);
		funktions.add(f);
		
	}
				
	
	// adds data to a global data list
	public void addMeasures(ArrayList<ArrayList<Double>> mea){
		
		double[][] mea2 = new double[mea.size()][4]; // 4 indices (0 = x koordinate / 1 = y koordinate / 2,3 sind für fehlerbalken 
		for(int i = 0; i< mea.size(); i++){
			
			mea2[i] = Calc.scale(mea.get(i));
		}
		measure.add(mea2);
		MaxandMin(mea2);
	}
	
	// sets the plot range so that all data points are in the plot
	public void MaxandMin(double[][] mea){
		for(int i = 0; i < mea.length /*&& i < rest*/; i++){
			if(!init){
				init = true;
				xmin = mea[i][0];
				xmax = mea[i][0];
				ymin = mea[i][1];
				ymax = mea[i][1];
			}
			if(xmin >= mea[i][0]){
				xmin = mea[i][0];
			}
			if(xmax <= mea[i][0]){
				xmax = mea[i][0];
			}
			if(ymax <= mea[i][1]){
				ymax = mea[i][1];
			}
			if(ymin >= mea[i][1]){
				ymin = mea[i][1];
			}
		}
		double px =(xsize/Math.abs(xmax - xmin));
		double py =(ysize/Math.abs(ymax - ymin));
		if(drawpoints){
			xmin = xmin - recsize/px;
			xmax = xmax + recsize/px;
			ymin = ymin - recsize/py;
			ymax = ymax + recsize/py;
		}
	}
	
	//creates a random color
	public Color randomColor(){
		Random rand = new Random();
		float r = rand.nextFloat();
		float gg = rand.nextFloat();
		float b = rand.nextFloat();
		Color randomColor = new Color(r, gg, b);
		return randomColor;
	}
	
	// draws coordinate system including the labels, text next to the plot, a legend 
	public void drawKs(Graphics2D g){
		g.setColor(Color.BLACK);
		double px =(xsize/Math.abs(xmax - xmin));
		double py =(ysize/Math.abs(ymax - ymin));

		g.draw3DRect(width/2 -xsize/2, height/2-ysize/2, xsize, ysize, false);

		for(double i = xmin; i <= xmax ; i+=(Math.abs((xmax-xmin)))/((double)10)){
			String str = KsDigit(i, 5); 
			double k = ((double)px)*i;
			int x = (int)(width/2 -xsize/2 -xmin*px +k-g.getFontMetrics().stringWidth(str)/2);
			int y = height/2+ysize/2+g.getFontMetrics().getHeight();
			g.drawString(str, x, y);
			int x1 = (int)(width/2 -xsize/2 -xmin*px +k);
			int y1 = height/2+ysize/2 - 5;
			int x2 = (int)(width/2 -xsize/2 -xmin*px +k);
			int y2 = height/2+ysize/2 + 5;
			g.drawLine(x1, y1, x2, y2);
			
		}
		//y axis
		if(energy.size()>0){
			for(int i = 0; i< energy.size() ; i+=1){
				double k = ((double)py)*(energy.get(i));
				String str = KsDigit(energy.get(i), 2);
				int x = width/2 -xsize/2 - g.getFontMetrics().stringWidth(str) - 5;
				int y = (int)(height/2+ysize/2-k+g.getFontMetrics().getHeight()/4.0+ymin*py);
				g.drawString(str, x, y);
				int x1 = width/2 -xsize/2 + 5;
				int y1 = (int)(height/2+ysize/2-k+ymin*py);
				int x2 = width/2 -xsize/2 - 5;
				int y2 = (int)(height/2+ysize/2-k+ymin*py);
				g.drawLine(x1, y1, x2, y2);
			}
		}

		//legend
		if(legend != null){
		for(int i = 0; i < legend.length; i++){
			g.setFont(new Font("TimesRoman", Font.PLAIN, legendFontSize));
			String str = legend[i];
			int x = width/2 - g.getFontMetrics().stringWidth(str)/2;
			int y = height/2 - ysize/2 - (i+1) * (g.getFontMetrics().getHeight()+10);
			g.drawString(str, x, y);
			g.setColor(colorList[i]);
			g.fill3DRect(x - recsize -5 ,y - g.getFontMetrics().getHeight()/2,recsize,recsize,true);

			g.setColor(Color.BLACK);
		}
		}
		// Aufgabe
		String str = headline;
		g.setFont(new Font("TimesRoman", Font.PLAIN, headlineFontSize));
		int x = width/2 - g.getFontMetrics().stringWidth(str)/2;
		int y = height/2 - ysize/2 - 5 - g.getFontMetrics().getHeight();
		g.drawString(str, x, y);
		// x-achse
		str = xlabel;
		g.setFont(new Font("TimesRoman", Font.PLAIN, xlabelFontSize));
		x = width/2 - g.getFontMetrics().stringWidth(str)/2;
		y = height/2 + ysize/2 + 5 + 2*g.getFontMetrics().getHeight();
		g.drawString(str, x, y);
		// y Achse
		str = ylabel;
		g.setFont(new Font("TimesRoman", Font.PLAIN, ylabelFontSize));
		x = width/2 - xsize/2 - g.getFontMetrics().stringWidth(str) - 100;
		y = height/2 - g.getFontMetrics().getHeight()/2;
		g.drawString(str, x, y);
		//text
		str = text;
		g.setFont(new Font("TimesRoman", Font.PLAIN, textFontSize));
		x = width/2 + xsize/2 + 10;
		ArrayList<String> s = separateString(str, x);
		int zeile = 0;
		int zabs = 5; // zeilenabstand
		for(int i = 0; i < s.size(); i++){
			if(x + g.getFontMetrics().stringWidth(s.get(i) + " ") >= width){
				zeile += 1;
				x = width/2 + xsize/2 + 10;
			}
			y = height/2 - ysize/2 +  zeile *(g.getFontMetrics().getHeight()+zabs);
			g.drawString(s.get(i), x, y);

			x += g.getFontMetrics().stringWidth(s.get(i));
				
				
		}
		
	}
	
	// draws datapoints of the given list
	public void drawMeasure(double[][] mea, boolean errorbars , Graphics2D g, int s){ // creates measured points
		MaxandMin(mea);
		double px =(xsize/Math.abs(xmax - xmin));
		double py =(ysize/Math.abs(ymax - ymin));
		g.setColor(colorList[s]);
		for(int i = 0; i < mea.length && i < rest; i++){
			// wertebereich anpassen
			if(mea[i][1]==mea[i][1]){//Nan.check
				if(drawpoints){
					int xr = (int)(width/2 -xsize/2 + mea[i][0]*px-recsize/2 + (-xmin)*px);
					int yr = (int)(height/2+ysize/2-mea[i][1]*py+(ymin)*py+recsize/2);
					g.fill3DRect(xr,yr, recsize, recsize,true);
					//fehlerbalken
					int xl1 = (int) (width/2 - xsize/2 + mea[i][0]*px + (-xmin)*px);
					int yl1 = (int)(height/2+ysize/2-mea[i][2]*py+(ymin)*py);
					int xl2 = (int)(width/2 -xsize/2 + mea[i][0]*px + (-xmin)*px);
					int yl2 = (int)(height/2+ysize/2-mea[i][3]*py+(ymin)*py);
					g.drawLine(xl1,yl1,xl2,yl2);
				}else{
					if(i != 0){
						int xr = (int)(width/2 -xsize/2 + mea[i][0]*px + (-xmin)*px);
						int yr = (int)(height/2+ysize/2-mea[i][1]*py+(ymin)*py);
						int xl = (int)(width/2 -xsize/2 + mea[i-1][0]*px + (-xmin)*px);
						int yl = (int)(height/2+ysize/2-mea[i-1][1]*py+(ymin)*py);
						g.drawLine(xl,yl,xr,yr);

					}
				}
			}

		}
	}
	
	// draws all funktions of the given list
	public void drawFunktions(ArrayList<Funktion> funktions2,Graphics2D g){

		if(funktions2 != null){
			double py =(ysize/Math.abs(ymax - ymin));
			for(int i = 0; i < funktions2.size(); i++){
				g.setColor(Color.BLACK);
				if(funktions2.get(i) != null){
					funktions2.get(i).refresh(this);
					for(int j = 0; j+1 < funktions2.get(i).plot.length; j++){
						double d1 = funktions2.get(i).plot[j];
						double d2 = funktions2.get(i).plot[j+1];
						int x1= width/2 -xsize/2 + j;
						int y1 = (int)(height/2 + ysize/2 +((ymin)*py) -(d1*py));//fix +2 stuff
						int x2 = width/2 -xsize/2 + j+1;
						int y2 = (int)(height/2 + ysize/2 +((ymin)*py) -(d2*py));
						g.drawLine(x1,y1,x2,y2);
					}
				}
			}
		}
	}
	// Hilfsmethode
	public ArrayList<String> separateString(String str, int x){
		str += " "; 
		ArrayList<String> s = new ArrayList<>();
		String d = "";
		for(int i = 0; i < str.length(); i++){
			if(Character.toString(str.charAt(i)).equals(" ")){
				s.add(d);
				d = "";
			}
			d += str.charAt(i);
		}
		return s;
	}
	// method which returns senseful numbers for the koordinate system
	public String KsDigit(double number, int nst){
		String s = Double.toString(number);
		String p = "";
		boolean n = false; //nachkommastellen
		boolean e = false; //written as E^sth
		String eString = "";
		int f = 0;
		int j = 0;
		boolean count0dig = true;;
		for(int i = 0; i < s.length(); i++){
			if(n){
				if(Character.toString(s.charAt(i)).equals("0")&&count0dig){
					j += 1;
				}
				if(!Character.toString(s.charAt(i)).equals("0")){
					count0dig = false;
				}
			}
			if(j > nst){
				nst = j;
			}
			if(Character.toString(s.charAt(i)).equals(".")){
				n = true;
			}
			if(Character.toString(s.charAt(i)).equals("E")){
				e = true;
			}
			if(e){
				eString += s.charAt(i);
			}
		}
		boolean countf = false;
		for(int i = 0; i < s.length(); i++){
			p += s.charAt(i);
			if(n && e){
				if(Character.toString(s.charAt(i)).equals(".")||countf){
					f += 1;
					countf = true;
				}
				if(f >= nst){
					p += eString;
					i = s.length();
				}
			}
			if(n && !e){
				if(Character.toString(s.charAt(i)).equals(".")||countf){
					f += 1;
					countf = true;
				}
				if(f >= nst){
					p += eString;
					i = s.length();
				}
			}
			if(!n && e){
				f += 1;
				if(f >= nst){
					p += eString;
					i = s.length();
				}
			}
		}
		return p;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for(int i = 0; i < measure.size(); i++){

			drawMeasure(measure.get(i), true,g2d,i);

		}

		drawKs(g2d);

		drawFunktions(funktions, g2d);
		
		}
	// method to plot whatever is inside the data and funktionlist
	public void plot() throws InterruptedException{
		JFrame frame = new JFrame("2-D Plot");
		frame.add(this);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDoubleBuffered(false);

		colorList = new Color[measure.size()];
		for(int i = 0; i < colorList.length; i++){
			colorList[i] = randomColor();
		}
		while(true){

			rest += 100;
			this.repaint();
			Thread.sleep(200); // sleeping time
		}
	}
}