import java.util.ArrayList;

public class IdentityCheck {
	
	public static boolean isNumber(Parser p, String str, String str2, int idx){
		for(int j = 0; j < p.validNumbers.length; j++){
			int i = idx;
			if(p.validNumbers[j].equals(str)){
				p.helpString += str; 
				p.index = i;
				i += 1;
				//System.out.println(i);
				if(i < str2.length()){
					char c = str2.charAt(i);
					String s = Character.toString(c);
					if(isNumber(p,s,str2,i)){					
					}
				}

			}
		}
		return false;
	}
	
	public static boolean isVariable(Parser p, String str, String str2, int idx){
		ArrayList<String> variables = new ArrayList<>();
		ArrayList<Integer> index = new ArrayList<>();
		for(int j = 0; j < p.variable.size(); j++){
			String op = p.variable.get(j);
			if(op.length() > 1){
				for(int i = 1; i < op.length(); i++){
					if(idx + i < str2.length()){
						char c = str2.charAt(idx + i);
						String s = Character.toString(c);
						str += s;
					}
				}
			}
			if(str.equals(p.variable.get(j))){
				variables.add(str);
				index.add(idx + str.length() - 1);
			}
			char c = str2.charAt(idx);
			String s = Character.toString(c);
			str = s;
		}
		int maxidx = -1;
		int s = 0;
		for(int i = 0; i < variables.size(); i++){
			if(index.get(i) > maxidx){
				maxidx = index.get(i);
				s = i;
			}
		}
		if(maxidx > -1){
			System.out.println("variable "+variables.get(s));
			System.out.println("index " + index.get(s));
			p.helpString = variables.get(s);
			p.index = maxidx;
			return true;
		}
		return false;
	}
	public static boolean isFunktion(Parser p, String str, String str2, int idx){

		/*for(int j = 0; j < p.funktions.size(); j++){
			String op = p.funktions.get(j);
			if(op.length() > 1){
				
				for(int i = 1; i < op.length(); i++){
					if(idx + i < str2.length()){
						char c = str2.charAt(idx + i);
						String s = Character.toString(c);
						str += s;
					}
				}
			}
			if(str.equals(p.funktions.get(j))){
					p.helpString = str;
					p.index = idx + str.length() - 1; 
					return true;
			}
			char c = str2.charAt(idx);
			String s = Character.toString(c);
			str = s;
		}
		
		return false;*/
		ArrayList<String> funkt = new ArrayList<>();
		ArrayList<Integer> index = new ArrayList<>();
		for(int j = 0; j < p.funktions.size(); j++){
			String op = p.funktions.get(j);
			if(op.length() > 1){
				for(int i = 1; i < op.length(); i++){
					if(idx + i < str2.length()){
						char c = str2.charAt(idx + i);
						String s = Character.toString(c);
						str += s;
					}
				}
			}
			if(str.equals(p.funktions.get(j))){
				funkt.add(str);
				index.add(idx + str.length() - 1);
			}
			char c = str2.charAt(idx);
			String s = Character.toString(c);
			str = s;
		}
		int maxidx = -1;
		int s = 0;
		for(int i = 0; i < funkt.size(); i++){
			if(index.get(i) > maxidx){
				maxidx = index.get(i);
				s = i;
			}
		}
		if(maxidx > -1){
			System.out.println("variable "+funkt.get(s));
			System.out.println("index " + index.get(s));
			p.helpString = funkt.get(s);
			p.index = maxidx;
			return true;
		}
		return false;
	}
	
	
	
	
	public static boolean isOperator1(Parser p, String str){
		if(str.length() == 1){
			for(int j = 0; j < p.operators1.length; j++){
				if(str.equals(p.operators1[j])){
					return true;
				}
			}
		}
		return false;
	}
	public static boolean isOperator2(Parser p, String str){
		if(str.length() == 1){
			for(int j = 0; j < p.operators2.length; j++){
				if(str.equals(p.operators2[j])){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isBrace1(Parser p, String str){
		if(str.length() == 1){
			for(int j = 0; j < p.brace1.length; j++){
				if(str.equals(p.brace1[j])){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isBrace2(Parser p, String str){
		if(str.length() == 1){
			for(int j = 0; j < p.brace2.length; j++){
				if(str.equals(p.brace2[j])){
					return true;
				}
			}
		}
		return false;
	}
	
}
