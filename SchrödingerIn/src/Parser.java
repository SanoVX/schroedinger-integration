import java.util.ArrayList;

public class Parser {
	ArrayList<Double> numbers = new ArrayList<>();
	
	static String[] brace1 = {"("};
	static String[] brace2 = {")"};
	static String[] operators = {"+","-","*","/","^"};
	static String[] validNumbers = {"0","1","2","3","4","5","6","7","8","9","x","e"};
	
	public double parse(String str){
		
		
		return 0;
	}
	
	public static boolean checkSyntax(String str){
		ArrayList<Integer> syntax = new ArrayList<>(); // 0 == Number, 1 = operators, 2 == (, 3 == )
		for(int i = 0 ; i < str.length(); i++){
			char c = str.charAt(i);
			String s = Character.toString(c); 
			if(isNumber(s)){
				syntax.add(0);
			}
			if(isOperator(s)){
				syntax.add(1);
			}
			if(isBrace1(s)){
				syntax.add(2);
			}
			if(isBrace2(s)){
				syntax.add(3);
			}
		}
		int openedbraces = 0;
		for(int i = 1; i < syntax.size(); i++){
			if(syntax.get(i) == 2){
				openedbraces += 1;
			}
			if(syntax.get(i) == 3){
				if(openedbraces == 0){
					return false;
				}
				if(openedbraces <= 1){
					openedbraces -= 1;
				}
			}
			
			if((syntax.get(i) > 1 && (syntax.get(i-1) >= syntax.get(i)) || syntax.get(i-1) <= syntax.get(i))){ // checks if operator or brace is in front
				return false;
			}
		}
		if(openedbraces != 0){
			return false;
		}
		return true;
	}
	
	public static boolean isNumber(String str){
		if(str.length() == 1){
			for(int j = 0; j < validNumbers.length; j++){
				if(str.equals(validNumbers[j])){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isOperator(String str){
		if(str.length() == 1){
			for(int j = 0; j < operators.length; j++){
				if(str.equals(operators[j])){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isBrace1(String str){
		if(str.length() == 1){
			for(int j = 0; j < brace1.length; j++){
				if(str.equals(brace1[j])){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isBrace2(String str){
		if(str.length() == 1){
			for(int j = 0; j < brace2.length; j++){
				if(str.equals(brace2[j])){
					return true;
				}
			}
		}
		return false;
	}
	
	public String deleteSpaces(String str){
		String newString = "";
		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			String add = Character.toString(c);
			if(!Character.toString(c).equals(" ")){
				newString += Character.toString(c);
			}
		}
		
		return newString;
	}
	
	public static void main(String[] args){
		String str = "10*x+4-3*((7-1))";
		boolean b = checkSyntax(str);
		System.out.println(b);
	}
	
	
}
