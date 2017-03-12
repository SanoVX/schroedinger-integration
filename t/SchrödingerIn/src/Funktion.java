import java.util.ArrayList;

public class Funktion {
	int xPixel;
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
	public void refresh(CoordinateSystem g){
		plot = new ArrayList<>();
		this.xmin = g.xmin;
		this.xmax = g.xmax;
		this.ymin = g.ymin;
		this.ymax = g.ymax;
		this.xPixel = (int)(g.xsize/(xmax - xmin));
		for(int i = 0; i < g.xsize; i++){
			double x = ((double)i)/(xPixel) + xmin;
			double y = CalculateString.returnY(this, x, p.SyntaxList); ///////////////////
			ArrayList<Double> xy = new ArrayList<>();
			xy.add(x);
			if(showallYs || (y >= ymin && y<ymax)){
				xy.add(y);
			}
			if(y < ymin){
				xy.add(ymin);
			}
			if(y > ymax){
				xy.add(ymax);
			}
			if(Double.isNaN(y)){
				xy.add(y);
			}
			plot.add(xy);
		}
		
	}

	
}
