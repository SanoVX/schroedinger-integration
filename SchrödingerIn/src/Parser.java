import java.util.ArrayList;
//
public class Parser {
	ArrayList<Double> numbers = new ArrayList<>();
	
	boolean valid = true;
	int index = 0;
	String helpString = "";
	
	String syntax = "";
	ArrayList<String> SyntaxList;
	ArrayList<Integer> Identity;
	
	//Fehlermeldungen
	String message = "";
	String variableAdder = "";
	
	Funktion f;
	
	static String[] brace1 = {"("};
	static String[] brace2 = {")"};
	static String[] operators1 = {"+","-"};
	static String[] operators2 = {"*","/","^"};
	static String[] validNumbers = {"0","1","2","3","4","5","6","7","8","9","."};
	static ArrayList<String> variable = new ArrayList<>();
	static ArrayList<Double> values = new ArrayList<>();
	static ArrayList<String> funktions = new ArrayList<>();
	
	public Parser(String syntax, Funktion f){
		this.f = f;
		setVariables();
		setfunktions();
		syntax = deleteSpaces(syntax);
		ConvertToList(syntax);
		overworkSyntax();

		/*for(int i = 0; i < Identity.size(); i++){
			System.out.println("Printing id list " + Identity.get(i));
		}*/
		if(CheckSyntax()){
			this.message = "Calculating...";
		}else{
			valid = false;
			this.message = "Syntax Error";
		}
		System.out.println(message);
		for(int i = 0; i < SyntaxList.size(); i++){
			System.out.println("Printing Syntax list " + SyntaxList.get(i));
		}
		for(int i = 0; i < Identity.size(); i++){
			System.out.println("Printing id list " + Identity.get(i));
		}
	}
	
	public void setVariables(){
		String[] var = {"x", "e", "pi", "e0", "q"};
		double[] val = {0.0, Math.E, Math.PI, 8.854*Math.pow(10, -12), Einstellungen.e};
		for(int i = 0; i < var.length; i++){
			variable.add(var[i]);
			values.add(val[i]);
			variable.add("-"+var[i]);
			values.add(-val[i]);
		}

	}
	
	public void setfunktions(){
		String[] funkt = {"sin","cos", "tan", "arctan", "arcsin", "abs", "arccos","sinh", "cosh", "tanh", "theta"};
		for(int i = 0; i < funkt.length; i++){
			funktions.add(funkt[i]);
			funktions.add("-" + funkt[i]);
		}
	}
	
	public void addVariable(String str, double value){
		boolean exists = false;
		for(int i = 0; i < variable.size(); i++){
			if(str.equals(variable.get(i))){
				exists = true;
				variableAdder = "This Variable already exists!";
			}
		}
		if(!exists){
			variable.add(str);
			values.add(value);
			variable.add("-"+str);
			values.add(-value);
			variableAdder = "Variable has ben added.";
		}
	}
	
	public void overwriteVariable(String str, double value){
		boolean exists = false;
		for(int i = 0; i < variable.size(); i++){
			if(str.equals(variable.get(i))){
				exists = true;
				if(exists){
					values.set(i, value);
					i = variable.size();
				}
			}
		}
	}
	
	
	public void ConvertToList(String str){
		System.out.println(str);
		ArrayList<String> stringList = new ArrayList<>();
		ArrayList<Integer> identity = new ArrayList<>(); //numbers, variables, konstants = 1; funktions = 2; operator1 = 3; operator2 = 4; brace1 = 5, brace2 = 6
		String HelpString = "";
		boolean added = false;
		for(int i = 0; i < str.length(); i++){
			added = false;
			HelpString = "";
			helpString = "";
			/*if(index > i && index < str.length()){//updates index
				i = index;
			}*/
			char c = str.charAt(i);
			String s = Character.toString(c); 
			//System.out.println("Index = " + index+ " " +str.length());
			if(!IdentityCheck.isNumber(this, s,str ,i)){ // adds numbers to Stringlist
				if(!helpString.equals("")){
					identity.add(1);
					stringList.add(helpString);
					added = true;
					i = index;
				}
			}
			if(i < str.length()){
				if(IdentityCheck.isVariable(this, s,str, i) && !added){
					identity.add(1);
					stringList.add(helpString);
					added = true;
					i = index;
				}
				if(IdentityCheck.isFunktion(this, s,str, i)&& !added){
					identity.add(2);
					stringList.add(helpString);
					added = true;
					i = index;
				}
				if(IdentityCheck.isOperator1(this, s)&& !added){
					HelpString += s;
					identity.add(3);
					stringList.add(HelpString);
					added = true;
				}
				if(IdentityCheck.isOperator2(this, s)&& !added){
					HelpString += s;
					identity.add(4);
					stringList.add(HelpString);
					added = true;
				}
			}
			if(IdentityCheck.isBrace1(this, s)&& !added){
				HelpString += s;
				identity.add(5);
				stringList.add(s);
				added = true;
			}
			if(IdentityCheck.isBrace2(this, s)&& !added){
				HelpString += s;
				identity.add(6);
				stringList.add(s);
				added = true;
			}
			if(!added){
				identity.add(7);
				stringList.add("");
			}
			
		}
		SyntaxList = stringList;
		Identity = identity;
	}
	
	public void overworkSyntax(){//for negative and positive Numbers
		int[] notOpIf = {5,4,3}; //notOperatorIf
		ArrayList<Integer> clearIndex = new ArrayList<>(); 
		for(int i = 0; i < Identity.size(); i++){
			if(i > 0){
				if(Identity.get(i) == 1 && Identity.get(i-1) == 3){
					if(i - 1 == 0){
						clearIndex.add(i-1);
					}
					else{
						for(int j = 0; j < notOpIf.length; j++){
							if(Identity.get(i-2) == notOpIf[j]){
								clearIndex.add(i-1);
							}
						}
					}
				}
			}
		}
		for(int i = 0; i < clearIndex.size(); i++){

			int m = clearIndex.get(clearIndex.size() - 1 - i);
			String s = SyntaxList.get(clearIndex.get(clearIndex.size()-1 - i));
			if(s.equals("-")){
				String d = SyntaxList.get(clearIndex.get(clearIndex.size() - 1 - i) +1);
				SyntaxList.set(clearIndex.get(clearIndex.size() - 1 - i) +1, "-" + d);
			}
			Identity.remove(m);
			SyntaxList.remove(m);
		}
		
	}
	
	
	public boolean CheckSyntax(){
		ArrayList<Integer> id = new ArrayList<>();
		for(int i = 0; i < Identity.size(); i++){
			id.add(Identity.get(i));
			if(Identity.get(i) == 7){
				return false;
			}
		}
		if(!checkFunktion(Identity)){
			return false;
		}
		if(!checkNumbers(Identity)){
			return false;
		}
		if(!BraceHandler.validBraces(id)){
			return false;
		}
		if(!BraceHandler.validBraceContent(f,id)){
			return false;
		}
		if(!restSyntax(id)){
			return false;
		}
		return true;
	}
	
	public boolean restSyntax(ArrayList<Integer> syntax){
		
		if(BraceHandler.validBegin(syntax.get(0))){
			
			return false;
		}
		
		if(BraceHandler.validEnd(syntax.get(syntax.size()-1))){
			return false;
		}
		for(int i = 1 ; i < syntax.size(); i++){
			if((syntax.get(i) == syntax.get(i-1))){
				return false;
			}
			if(BraceHandler.unallowedFollowers(syntax.get(i)) && BraceHandler.unallowedFollowers(syntax.get(i-1))){
				return false;
			}
		}
		return true;
	}

	
	public String deleteSpaces(String str){
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
	
	public boolean checkNumbers(ArrayList<Integer> syntax){
		for(int i = 0; i < syntax.size(); i++){
			if(syntax.get(i) == 1){
				int points = 0;
				String str = SyntaxList.get(i);
				for(int j = 0; j < str.length(); j++){
					char c = str.charAt(j);
					String s = Character.toString(c);
					if(s.equals(".")){
						points += 1;
					}
					if(points > 1){
						return false;
					}
				}
			}
		}
		return true;
	}
	public boolean checkFunktion(ArrayList<Integer> syntax){
		for(int i = 0; i < syntax.size(); i++){
			if(i+1 < syntax.size()){
				if(syntax.get(i) == 2 && !(syntax.get(i+1) == 5)){
					return false;
				}
			}
		}
		return true;
	}
	
	
	
}
