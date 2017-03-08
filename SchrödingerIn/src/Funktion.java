import java.util.ArrayList;

public class Funktion {
	int xPixel;
	int yPixel; //pixel for one step
	double xmin;//
	double xmax; ///
	double ymin = 0;
	double ymax = 0;
	double[] plot;
	
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
		this.xmin = g.xmin;
		this.xmax = g.xmax;
		this.ymin = g.ymin;
		this.ymax = g.ymax;
		this.xPixel = (int)(g.xsize/(xmax - xmin));

		this.plot = new double[g.xsize]; 
		for(int i = 0; i < this.plot.length; i++){
			double x = ((double)i)/(xPixel) + xmin;
			double y = CalculateString.returnY(this, x, p.SyntaxList); ///////////////////
			//System.out.println(y);
			if(showallYs || (y >= ymin && y<ymax)){
				this.plot[i] = y; // use k and not i here
			}
			if(y < ymin){
				this.plot[i] = ymin;
			}
			if(y > ymax){
				this.plot[i] = ymax;
			}
		}
		
	}

	
}
