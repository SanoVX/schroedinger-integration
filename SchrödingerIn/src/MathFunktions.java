
public class MathFunktions {

	static String[] funktions = {"sin","cos", "tan", "arctan", "arcsin", "abs", "arccos","sinh", "cosh", "tanh"};
	
	public static String calc(int i, String s){
		String str = "";
		double d = Double.parseDouble(s);
		if(i == 0){
			d = Math.sin(d);
		}
		if(i == 1){
			d = Math.cos(d);
		}
		if(i == 2){
			d = Math.tan(d);
		}
		if(i == 3){
			d = Math.atan(d);
		}
		if(i == 4){
			d = Math.asin(d);
		}
		if(i == 5){
			d = Math.abs(d);
		}
		if(i == 6){
			d = Math.acos(d);
		}
		if(i == 7){
			d = Math.sinh(d);
		}
		if(i == 8){
			d = Math.cosh(d);
		}
		if(i == 9){
			d = Math.tanh(d);
		}
		
		return Double.toString(d);
	}
}
