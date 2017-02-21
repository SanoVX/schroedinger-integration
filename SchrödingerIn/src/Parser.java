import java.util.ArrayList;
//
public class Parser {
	ArrayList<Double> numbers = new ArrayList<>();
	
	static String[] brace1 = {"("};
	static String[] brace2 = {")"};
	static String[] operators = {"+","-","*","/","^"};
	static String[] validNumbers = {"0","1","2","3","4","5","6","7","8","9","x","e"};
	
	public double parse(String str){
		str = deleteSpaces(str);
		
		return 0;
	}
	
	public static boolean checkSyntax(String str){
		ArrayList<Integer> syntax = new ArrayList<>(); // 0 == Number, 1 = operators, 2 == (, 3 == )
		for(int i = 0 ; i < str.length(); i++){
			char c = str.charAt(i);
			String s = Character.toString(c); 
			if(IdentityCheck.isNumber(s)){
				syntax.add(0);
			}
			if(IdentityCheck.isOperator(s)){
				syntax.add(1);
			}
			if(IdentityCheck.isBrace1(s)){
				syntax.add(2);
			}
			if(IdentityCheck.isBrace2(s)){
				syntax.add(3);
			}
		}
		if(!BraceHandler.validBraces(syntax)){
			return false;
		}
		if(!BraceHandler.validBraceContent(syntax)){
			return false;
		}
		if(!restSyntax(syntax)){
			return false;
		}
		return true;
	}
	public static boolean restSyntax(ArrayList<Integer> syntax){
		if(syntax.get(0) == 1){
			return false;
		}
		if(syntax.get(syntax.size()-1) == 1){
			return false;
		}
		
		for(int i = 1 ; i < syntax.size(); i++){
			if((syntax.get(i) == syntax.get(i-1))&&syntax.get(i)==1){
				return false;
			}
		}
		return true;
	}
	
	public static String deleteSpaces(String str){
		String newString = "";
		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			String add = Character.toString(c);
			if(!Character.toString(c).equals(" ")){
				newString += add;
			}
		}
		
		return newString;
	}
	
	public static void main(String[] args){
		String str = "10*x+4-(3+4)/(5-7)+()";
		str = deleteSpaces(str);
		System.out.println(str);
		boolean b = checkSyntax(str);
		System.out.println(b);
	}
	
	
}
