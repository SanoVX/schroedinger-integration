import java.util.ArrayList;

public class Funktion {
	double xPixel;
	int yPixel; //pixel for one step
	double xmin;//
	double xmax; ///
	double ymin = 0;
	double ymax = 0;
	ArrayList<ArrayList<Double>> plot;
	
	ArrayList<ArrayList<Integer>> idx2; 
	ArrayList<ArrayList<Integer>> idx3;
	
	
	//variables
	String message;
	String funktion;
	boolean showallYs;

	Parser p;
	
	
	//konstanten 
	
	public Funktion(CoordinateSystem g, String str, boolean showallYs){
		this.showallYs = showallYs;
		Parser p = new Parser(str, this);
		this.message = p.message;
		this.p = p;
		if(p.valid){
			refresh(g);
		}
	}
	
	public Funktion(String str, boolean showallYs){
		this.showallYs = showallYs;
		Parser p = new Parser(str, this);
		this.message = p.message;
		this.p = p;
		this.funktion = str;

	}
	public void refresh(CoordinateSystem g){
		plot = new ArrayList<>();
		this.xmin = g.xmin;
		this.xmax = g.xmax;
		this.ymin = g.ymin;
		this.ymax = g.ymax;
		this.xPixel = (g.xsize/(xmax - xmin));
		for(int i = 0; i <= g.xsize; i++){
			double x = ((double)i)/((xPixel)) + xmin;
			double y = CalculateString.returnY(this, x, p.SyntaxList); ///////////////////
			ArrayList<Double> xy = new ArrayList<>();
			xy.add(x);
			if(showallYs || (y >= ymin && y<ymax)){
				y = y;
			}
			if(y < ymin){
				y = ymin;
			}
			if(y > ymax){
				y = ymax;
			}
			xy.add(y);
			
			plot.add(xy);
		}
		
	}
	
	public double getY(double x){
		if(p.valid){
			if(p.SyntaxList != null){
				return CalculateString.returnY(this, x, p.SyntaxList); 
			}
		}
		return Double.NaN;
	}

	
}
