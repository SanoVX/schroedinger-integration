package org.schrodinger.gui;

public class MathFunktions {

	
	public static String calc(int i, String s){
		String str = "";
		double d = Double.parseDouble(s);
		if(i == 0){
			d = Math.sin(d);
		}
		if(i == 1){
			d = -Math.sin(d);
		}
		if(i == 2){
			d = Math.cos(d);
		}
		if(i == 3){
			d = -Math.cos(d);
		}
		if(i == 4){
			d = Math.tan(d);
		}
		if(i == 5){
			d = -Math.tan(d);
		}
		if(i == 6){
			d = Math.atan(d);
		}
		if(i == 7){
			d = -Math.atan(d);
		}
		if(i == 8){
			d = Math.asin(d);
		}
		if(i == 9){
			d = -Math.asin(d);
		}
		if(i == 10){
			d = Math.abs(d);
		}
		if(i == 11){
			d = -Math.abs(d);
		}
		if(i == 12){
			d = Math.acos(d);
		}
		if(i == 13){
			d = -Math.acos(d);
		}
		if(i == 14){
			d = Math.sinh(d);
		}
		if(i == 15){
			d = -Math.sinh(d);
		}
		if(i == 16){
			d = Math.cosh(d);
		}
		if(i == 17){
			d = -Math.cosh(d);
		}
		if(i == 18){
			d = Math.tanh(d);
		}
		if(i == 19){
			d = -Math.tanh(d);
		}
		if(i == 20){
			if(d >= 0){
				d = 1;
			}else{
				d = 0;
			}
		}
		if(i == 21){
			if(d >= 0){
				d = -1;
			}else{
				d = 0;
			}
		}
		
		return Double.toString(d);
	}
}
