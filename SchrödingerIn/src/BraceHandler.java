import java.util.ArrayList;

public class BraceHandler {

	
	public static boolean validBraces(ArrayList<Integer> syntax){//only checks wheater braces are valid
		int openedbraces = 0;
		for(int i = 1; i < syntax.size(); i++){
			if(syntax.get(i) == 5){
				openedbraces += 1;
			}
			if(syntax.get(i) == 6){
				if(openedbraces == 0){
					return false;
				}
				if(openedbraces >= 1){
					openedbraces -= 1;
				}
			}
		}
		if(openedbraces != 0){
			return false;
		}
		return true;
	}
	
	
	public static boolean validBraceContent(Funktion f, ArrayList<Integer> syntax){
		ArrayList<ArrayList<Integer>> idx2 = new ArrayList<>(); 
		ArrayList<ArrayList<Integer>> idx3 = new ArrayList<>();
		int BraceColl = 0;
		int lastBrace = 0;
		for(int i = 0 ; i < syntax.size(); i++){
			if(BraceColl >= idx2.size()){//increase list
				ArrayList<Integer> b = new ArrayList<>();
				ArrayList<Integer> d = new ArrayList<>();
				idx2.add(b);
				idx3.add(d);
			}
			if(syntax.get(i) == 5){
				if(lastBrace == 6){
					BraceColl += 1;
					if(BraceColl >= idx2.size()){//increase list
						ArrayList<Integer> b = new ArrayList<>();

						ArrayList<Integer> d = new ArrayList<>();
						idx2.add(b);
						idx3.add(d);
					}
				}
				idx2.get(BraceColl).add(i);

				lastBrace = 5;
			}
			if(syntax.get(i) == 6){
				lastBrace = 6;
				idx3.get(BraceColl).add(i);
			}
		}
		for(int i = 0; i < idx2.size(); i++){
			
			for(int j = 0; j < idx2.get(idx2.size()-1 - i).size(); j++){
				if(!checkBraceSyntax(syntax, idx2.get(idx2.size()-1 - i).get(idx2.get(idx2.size() - 1 -i).size()-1-j), idx3.get(idx2.size() - 1 -i).get(j))){ // problem here
					return false;
				}
			}
		}
		f.idx2 = idx2;
		f.idx3 = idx3;
		return true;
	}
	
	
	public static boolean checkBraceSyntax(ArrayList<Integer> syntax, int one, int two){
		
		if(Math.abs(one - two) == 1){ // () exception
			return false;
		}
		
		if(validBegin(syntax.get(one+1))){
			return false;
		}
		if(validEnd(syntax.get(two-1))){
			return false;
		}
		for(int i = one + 2 ; i < two; i++){
			if((syntax.get(i) == syntax.get(i-1))){
				return false;
			}
			if(unallowedFollowers(syntax.get(i)) && unallowedFollowers(syntax.get(i+1))){
				return false;
			}
		}
		for(int i = 1; i < Math.abs(two-one); i++){//converts brace content into numbers
			syntax.remove(two-i);
		}
		return true;
	}
	
	public static boolean unallowedFollowers(int syn){
		int[] list = {1,2};
		for(int j = 0; j < list.length; j++){
			if(syn == list[j]){
				return true;
			}
		}
		return false;
	}
	
	public static boolean validBegin(int id){
		int[] validList = {1,2,5};
		boolean notValid = true;
		for(int i = 0 ; i < validList.length; i++){
			if(validList[i] == id){
				return false;
			}
		}
		return notValid;
	}
	
	public static boolean validEnd(int id){
		int[] validList = {1,2,6};
		boolean notValid = true;
		for(int i = 0 ; i < validList.length; i++){
			if(validList[i] == id){
				return false;
			}
		}
		return notValid;
	}
}
