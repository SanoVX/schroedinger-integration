

public class Funktion {
	int xPixel;
	int yPixel; //pixel for one step
	double xmin;
	double xmax;
	double ymin = 0;
	double ymax = 0;
	double[] plot;
	
	//variables
	double e;
	double f;
	double a;
	
	public Funktion(Game g,double e, double f,double a, boolean showallYs){
		this.xmin = g.xmin;
		this.xmax = g.xmax;
		this.ymin = g.ymin;
		this.ymax = g.ymax;
		this.xPixel = (int)(g.xsize/(xmax - xmin));
		this.e = e;
		this.f = f;
		this.a = a;
		this.plot = new double[g.xsize]; 
		for(int i = 0; i < this.plot.length; i++){
			double k = ((double)i)/(xPixel) + xmin;
			double y = testfunktion(e,f,a,k);///////////////////
			y = this.NaNcheck(k, y);
			
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

	
	public double NaNcheck(double j, double y){
		if(y == y){
			return y;
		}
		double k = j + ((double)1)/xPixel;
		double fy = funkt(k);///////////////////
		if(fy==fy){
			return 0;
		}
		k = j - ((double)1)/xPixel;
		fy = funkt(k);///////////////////
		if(fy==fy){
			return 0;
		}
		
		
		return Double.NaN;
	}
	
	public double funkt(double k){  //defines Funktion
		double y = a*(Math.sqrt(e-f*k*k-30*Math.cos(k)));
		return y;
	}
	
	public double testfunktion(double I,double b,double l,double phi){
		phi = phi*(2*Math.PI)/360;
		double y = I*Math.pow(Math.sin(Math.PI*b/l*Math.sin(phi))/(Math.PI*b/l*Math.sin(phi)),2);
		y = Math.log10(y);
		return y;
	}
	
	
	public double Fitfunkt(double k){
		double y = f*k+a;
		return y;
	}
}
