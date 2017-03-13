import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

public class CoordinateSystem {

	Game game;
	int xpos;
	int ypos;
	int xsize;
	int ysize;
	ArrayList<Funktion> funktions = new ArrayList<>();
	ArrayList<double[][]> measure = new ArrayList<>();
	ArrayList<Double> energy = new ArrayList<>();

	ArrayList<ArrayList<ArrayList<ArrayList<Double>>>> simulation = new ArrayList<>();
	ArrayList<ArrayList<ArrayList<Double>>> solution = new ArrayList<>();
	
	double xmin = 0;
	double xmax = 0;
	double ymin = 0;
	double ymax = 0;
	int recsize = 6; // size of rectangle representing the data point
	
	//legend
	public int numberTextSize = 10;//
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
	boolean growingrange = false;
	boolean drawable = false;
	boolean changedrange = false;
	
	Color[] colorList = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PINK};

	//plot
	int plotThickness = 0;
	//
	
	
	public CoordinateSystem(Game game, int xpos, int ypos, int xsize, int ysize){
		this.game = game;
		this.xpos = xpos;
		this.ypos = ypos;
		this.xsize = xsize;
		this.ysize = ysize;
	}
	
	public void addEnergy(double e){
		energy.add(e);
		Funktion f = new Funktion(this,Double.toString(e),false);
		funktions.add(f);
		
	}
	public void changeSize(int xsize, int ysize){
		this.xsize = xsize;
		this.ysize = ysize;
	}
	
	public void changePos(int xpos, int ypos){
		this.xpos = xpos;
		this.ypos = ypos;
	}
	
				
	public void resetRange(){
		xmin = 0;
		xmax = 0;
		ymin = 0;
		ymax = 0;
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
		int k = mea.length;
		/*if(growingrange && searchRangeMax() < mea.length){
			k = searchRangeMax();
		}*/
		for(int i = 0; i < k /*&& i < rest*/; i++){
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

	
	// draws datapoints of the given list
	public void drawMeasure(boolean errorbars , Graphics2D g){ // creates measured points
		if(measure != null){
			for(int s = 0; s < measure.size(); s++){
				if(measure.get(s) != null){
				double[][] mea = measure.get(s);
				if(!changedrange){
				MaxandMin(mea);
				}
				drawable = true;
				double calcxmin = xmin;
				double calcxmax = xmax;
				double calcymin = ymin;
				double calcymax = ymax;
				double px =(xsize/Math.abs(xmax - xmin));
				double py =(ysize/Math.abs(ymax - ymin));
				g.setColor(colorList[s%colorList.length]);

				for(int i = 0; i < mea.length; i++){
					//wertebereich anpassen
					if(!noChange(calcxmin, calcxmax, calcymin, calcymax)){
						i = mea.length;
					}
					if(mea[i][1]==mea[i][1]){//Nan.check
						if(drawpoints){
							int xr = (int)(xpos + mea[i][0]*px-recsize/2 + (-xmin)*px);
							int yr = (int)(ypos + ysize -mea[i][1]*py+(ymin)*py+recsize/2);
							g.fill3DRect(xr,yr, recsize, recsize,true);
							//fehlerbalken
							int xl1 = (int) (xpos + mea[i][0]*px + (-xmin)*px);
							int yl1 = (int)(ypos + ysize -mea[i][2]*py+(ymin)*py);
							int xl2 = (int)(xpos + mea[i][0]*px + (-xmin)*px);
							int yl2 = (int)(ypos + ysize -mea[i][3]*py+(ymin)*py);
							g.drawLine(xl1,yl1,xl2,yl2);

						}else{
							if(i != 0){
								//better thickness
								double[] vect = {mea[i][0]-mea[i-1][0], mea[i][1]-mea[i-1][1]};
								double xcomp = -vect[1];
								double ycomp = vect[0];
								double length = Math.sqrt(Math.pow(xcomp, 2)+Math.pow(ycomp, 2));
								if(length != 0){
									xcomp /= length;
									ycomp /= length;
								}
								for(int j = -plotThickness; Math.abs(j) <= plotThickness+1; j++){//for thickness
									int xr = (int)(xpos + mea[i][0]*px + (-xmin)*px);
									xr += (int)(xcomp*j);
									int yr = (int)(ypos + ysize -mea[i][1]*py+(ymin)*py);
									yr += (int)(ycomp*j);
									int xl = (int)(xpos + mea[i-1][0]*px + (-xmin)*px);
									xl += (int)(xcomp*j);
									int yl = (int)(ypos + ysize -mea[i-1][1]*py+(ymin)*py);
									yl += (int)(ycomp*j);
									if(inKs(xr,yr,xl,yl)){
									g.drawLine(xl,yl,xr,yr);
									}
								}
		
							}
						}
					}
				}
			}
		}
		}
		
	}
	
	// draws all funktions of the given list
	public void drawFunktions(ArrayList<Funktion> funktions2,Graphics2D g){
		if(funktions2 != null){
			if(drawable){
				double calcxmin = xmin;
				double calcxmax = xmax;
				double calcymin = ymin;
				double calcymax = ymax;
				double px =(xsize/Math.abs(xmax - xmin));
				double py =(ysize/Math.abs(ymax - ymin));
				
				for(int i = 0; i < funktions2.size(); i++){
					g.setColor(Color.RED);
					if(funktions2.get(i) != null){
						funktions2.get(i).refresh(this);
						if(funktions2.get(i).plot != null){
							for(int j = 0; j+1 < funktions2.get(i).plot.size(); j++){
								if(!noChange(calcxmin, calcxmax, calcymin, calcymax)){
									i = funktions2.size();
									j = funktions2.get(i).plot.size();
								}
								double d1 = funktions2.get(i).plot.get(j).get(1);
								double d2 = funktions2.get(i).plot.get(j+1).get(1);
								if(!Double.isNaN(d1) && !Double.isNaN(d2)){
									double xx1 = funktions2.get(i).plot.get(j).get(0);
									double xx2 = funktions2.get(i).plot.get(j+1).get(0);
	
									int x1= (int)(xpos  + xx1*px + (-xmin)*px);
									int y1 = (int)(ypos + ysize +((ymin)*py) -(d1*py));
									int x2 = (int)(xpos  + xx2*px + (-xmin)*px);
									int y2 = (int)(ypos + ysize +((ymin)*py) -(d2*py));
									if(inKs(x1,y1,x2,y2)){
									g.drawLine(x1,y1,x2,y2);
									}
								}
								
							}
						}
					}
				}
			}
		}
	}
	
	public boolean noChange(double cxmin, double cxmax, double cymin, double cymax){
		if(xmin == cxmin && ymin == cymin && xmax == cxmax && ymax == cymax){
			return true;
		}
		return false;
	}
	
	public boolean inKs(int x1, int y1, int x2, int y2){
		if(x1 > xpos && x1 < xpos + xsize && y1 > ypos && y1 < ypos + ysize){
			if(x2 > xpos && x2 < xpos + xsize && y2 > ypos && y2 < ypos + ysize){	
				return true;
			}
		}
		return false;
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
	
	public void drawKs(Graphics2D g){
		if(drawable){
			g.setColor(Color.BLACK);
			double px =(xsize/Math.abs(xmax - xmin));
			double py =(ysize/Math.abs(ymax - ymin));
	
			g.draw3DRect(xpos, ypos, xsize, ysize, false);
			// xaxis numbers
	
			g.setFont(new Font("TimesRoman", Font.PLAIN, numberTextSize));
			for(double i = xmin; i <= xmax ; i+=(Math.abs((xmax-xmin)))/((double)10)){
				String str = KsDigit(i, 5); 
				double k = ((double)px)*i;
				int x = (int)(xpos -xmin*px +k-g.getFontMetrics().stringWidth(str)/2);
				int y = ypos + ysize+g.getFontMetrics().getHeight();
				g.drawString(str, x, y);
				int x1 = (int)(xpos -xmin*px +k);
				int y1 = ypos + ysize - 5;
				int x2 = (int)(xpos -xmin*px +k);
				int y2 = ypos + ysize + 5;
				g.drawLine(x1, y1, x2, y2);
				
			}
			//y axis numbers
			if(energy.size()>0){
				for(int i = 0; i< energy.size() ; i+=1){
					double k = ((double)py)*(energy.get(i));
					String str = KsDigit(energy.get(i), 2);
					int x = xpos - g.getFontMetrics().stringWidth(str) - 5;
					int y = (int)(ypos + ysize-k+g.getFontMetrics().getHeight()/4.0+ymin*py);
					g.drawString(str, x, y);
					int x1 = xpos + 5;
					int y1 = (int)(ypos + ysize-k+ymin*py);
					int x2 = xpos - 5;
					int y2 = (int)(ypos + ysize-k+ymin*py);
					g.drawLine(x1, y1, x2, y2);
				}
			}
	
			//legend
			if(legend != null){
			for(int i = 0; i < legend.length; i++){
				g.setFont(new Font("TimesRoman", Font.PLAIN, legendFontSize));
				String str = legend[i];
				int x = xpos + xsize/2 - g.getFontMetrics().stringWidth(str)/2;
				int y = ypos + ysize - (i+1) * (g.getFontMetrics().getHeight()+10);
				g.drawString(str, x, y);
				g.setColor(colorList[i]);
				g.fill3DRect(x - recsize -5 ,y - g.getFontMetrics().getHeight()/2,recsize,recsize,true);
	
				g.setColor(Color.BLACK);
			}
			}
			// headline
			String str = headline;
			g.setFont(new Font("TimesRoman", Font.PLAIN, headlineFontSize));
			int x = xpos + xsize/2 - g.getFontMetrics().stringWidth(str)/2;
			int y = ypos - 5 - g.getFontMetrics().getHeight();
			g.drawString(str, x, y);
			// x-achse
			str = xlabel;
			g.setFont(new Font("TimesRoman", Font.PLAIN, xlabelFontSize));
			x = xpos + xsize/2 - g.getFontMetrics().stringWidth(str)/2;
			y = ypos + ysize + 5 + 2*g.getFontMetrics().getHeight();
			g.drawString(str, x, y);
			// y Achse
			str = ylabel;
			g.setFont(new Font("TimesRoman", Font.PLAIN, ylabelFontSize));
			x = xpos - 100;
			y = ypos + ysize/2 - g.getFontMetrics().getHeight()/2;
			//g.rotate(-Math.PI/2);
			g.drawString(str, x, y);
			//g.rotate(Math.PI/2);
			//text
			str = text;
			g.setFont(new Font("TimesRoman", Font.PLAIN, textFontSize));
			x = xpos + 10;
			ArrayList<String> s = separateString(str, x);
			int zeile = 0;
			int zabs = 5; // zeilenabstand
			for(int i = 0; i < s.size(); i++){
				if(x + g.getFontMetrics().stringWidth(s.get(i) + " ") >= game.width){
					zeile += 1;
					x = xpos + 10;
				}
				y = ypos + ysize +  zeile *(g.getFontMetrics().getHeight()+zabs);
				g.drawString(s.get(i), x, y);
	
				x += g.getFontMetrics().stringWidth(s.get(i));
							
			}
		}
		
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
	
}
