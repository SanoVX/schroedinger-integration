import java.util.ArrayList;

public class Calc {
	public static double[] scale(ArrayList<Double> xy){
		/*double[] list = new double[4];
		list[0] = xy.get(0);
		list[1] = xy.get(1);
		list[2] = 1/(1/xy.get(1)+xy.get(2)); // upper side of errorbar
		list[3] = 1/(1/xy.get(1)-xy.get(2)); // lower side of errorbar
		System.out.println(list[1] +" "+ list[2] +" "+ list[3]);
		return list;*/
		
		/*double[] list = new double[4];
		list[0] = xy.get(0);
		list[1] = xy.get(1);
		if(xy.size()>2){
		list[2] = Math.pow(Math.sqrt(xy.get(1))+xy.get(2),2); // upper side of errorbar
		list[3] = Math.pow(Math.sqrt(xy.get(1))-xy.get(2),2); // lower side of errorbar
		}
		return list;*/
		
		
		double[] list = new double[4];
		list[0] = xy.get(0);
		list[1] = xy.get(1);
		list[2] = 0; // upper side of errorbar
		list[3] = 0; // lower side of errorbar
		return list;
		
	}
	
	
}
