
public class IdentityCheck {
	static String[] brace1 = {"("};
	static String[] brace2 = {")"};
	static String[] operators = {"+","-","*","/","^"};
	static String[] validNumbers = {"0","1","2","3","4","5","6","7","8","9","x","e"};
	
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
	
}
