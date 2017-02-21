import java.util.ArrayList;

public class BraceHandler {

	
	public static boolean validBraces(ArrayList<Integer> syntax){//only checks weather braces are valid
		int openedbraces = 0;
		for(int i = 1; i < syntax.size(); i++){
			if(syntax.get(i) == 2){
				openedbraces += 1;
			}
			if(syntax.get(i) == 3){
				if(openedbraces == 0){
					return false;
				}
				if(openedbraces >= 1){
					openedbraces -= 1;
				}
			}
		}
		//System.out.println("open" + " " + openedbraces);
		if(openedbraces != 0){
			return false;
		}
		return true;
	}
	
	
	public static boolean validBraceContent(ArrayList<Integer> syntax){
		ArrayList<Integer> idx2 = new ArrayList<>(); // () + () false case!!
		ArrayList<Integer> idx3 = new ArrayList<>();
		for(int i = 0 ; i < syntax.size(); i++){
			if(syntax.get(i) == 2){
				idx2.add(i);
			}
			if(syntax.get(i) == 3){
				idx3.add(i);
			}
		}
		for(int i = 0; i < idx2.size(); i++){
			if(!checkBraceSyntax(syntax, idx2.get(idx2.size()-1-i), idx3.get(i))){ // problem here
				return false;
			}
		}
		return true;
	}
	
	
	public static boolean checkBraceSyntax(ArrayList<Integer> syntax, int one, int two){
		if(one - two == 1){ // () exception
			return false;
		}
		
		if(syntax.get(one+1) == 1){
			return false;
		}
		System.out.println((two-1) + " " +syntax.size());
		if(syntax.get(two-1) == 1){
			return false;
		}
		for(int i = one + 2 ; i < two; i++){
			if((syntax.get(i) == syntax.get(i-1))&&syntax.get(i)==1){
				return false;
			}
		}
		for(int i = 1; i < Math.abs(two-one); i++){
			syntax.set(one + i, 0);
		}
		return true;
	}
}
