

public class Funktion {
	int xPixel;
	int yPixel; //pixel for one step
	double xmin;//
	double xmax; //
	double ymin = 0;
	double ymax = 0;
	double[] plot;
	
	//variables
	double b;
	double a;
	boolean showallYs;
	
	public Funktion(Game g,double a, double b, boolean showallYs){
		this.a = a;
		this.b = b;
		this.showallYs = showallYs;
		refresh(g);
	}
	public void refresh(Game g){
		this.xmin = g.xmin;
		this.xmax = g.xmax;
		this.ymin = g.ymin;
		this.ymax = g.ymax;
		this.xPixel = (int)(g.xsize/(xmax - xmin));

		this.plot = new double[g.xsize]; 
		for(int i = 0; i < this.plot.length; i++){
			double x = ((double)i)/(xPixel) + xmin;
			double y = Fitfunkt(a,b,x);///////////////////
			
			if(showallYs || (y >= ymin && y<ymax)){
				this.plot[i] = y; // use k and not i here
			}if(y < ymin){
				this.plot[i] = ymin;
			}
			if(y > ymax){
				this.plot[i] = ymax;
			}
		}
	}

	

	
	
	public double testfunktion(double I,double b,double l,double phi){
		phi = phi*(2*Math.PI)/360;
		double y = I*Math.pow(Math.sin(Math.PI*b/l*Math.sin(phi))/(Math.PI*b/l*Math.sin(phi)),2);
		y = Math.log10(y);
		return y;
	}
	
	
	public double Fitfunkt(double a, double b, double x){
		double y = a*x+b;
		return y;
	}
}
