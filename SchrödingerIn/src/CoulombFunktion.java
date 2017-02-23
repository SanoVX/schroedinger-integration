
public class CoulombFunktion extends Funktion{
	int xPixel;
	int yPixel; //pixel for one step
	double xmin;//
	double xmax; //
	double ymin = 0;
	double ymax = 0;
	double[] plot;
	
	//const
	protected double h = SchroedingerIntegration.h; // wirkungsquantum
	protected double u = SchroedingerIntegration.u; //elementarmasse
	protected double e = SchroedingerIntegration.e; //elementarladung
	protected double pi = Math.PI; 
	protected double e0 = SchroedingerIntegration.e0;
	
	//variables
	double b;
	double a;
	boolean showallYs;
	
	public CoulombFunktion(CoordinateSystem g, double a, double b, boolean showallYs) {
		super(g, a, b, showallYs);
		this.a = a;
		this.b = b;
		this.showallYs = showallYs;
		refresh(g);
		// TODO Auto-generated constructor stub
	}
	
	
	public void refresh(CoordinateSystem g){
		this.xmin = g.xmin;
		this.xmax = g.xmax;
		this.ymin = g.ymin;
		this.ymax = g.ymax;
		this.xPixel = (int)(g.xsize/(xmax - xmin));

		this.plot = new double[g.xsize]; 
		for(int i = 0; i < this.plot.length; i++){
			double x = ((double)i)/(xPixel) + xmin;
			double y = funkt(a,b,x);
			
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

	
	
	public double funkt(double a, double b, double x){
		if(x != 0){
			return -e*e/(4*pi*e0*Math.abs(x));
		}else{
			return 0;
		}
	}


}
