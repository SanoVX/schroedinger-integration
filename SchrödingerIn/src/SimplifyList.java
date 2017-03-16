import java.util.ArrayList;

public class SimplifyList {
	static ArrayList<Integer> firstPriority;
	static ArrayList<Integer> secondPriority;
	static ArrayList<Integer> thirdPriority;
	static ArrayList<Integer> Identity;
	static ArrayList<ArrayList<Integer>> idx2 = new ArrayList<>(); 
	static ArrayList<ArrayList<Integer>> idx3 = new ArrayList<>();
	static ArrayList<String> xContent = new ArrayList<>();
	public static ArrayList<String> simplify(Funktion f, ArrayList<String> List){
		
		Identity = new ArrayList<>();
		for(int i = 0; i < f.p.Identity.size(); i++){
			Identity.add(f.p.Identity.get(i));

		}
		ArrayList<String> stringList = new ArrayList<>();
		for(int i = 0; i < List.size(); i++){
			stringList.add(List.get(i));

		}

		updateIndexLists();
		String str = f.funktion;
		while(idx2.size()>0 && idx2.get(0).size() != 0){
			while(idx2.size() != 0 && idx2.get(idx2.size()-1).size()>0){
				stringList = calculate(stringList, idx2.get(idx2.size() - 1).get(idx2.get(idx2.size() - 1).size()-1), idx3.get(idx2.size() - 1).get(0), f);
				updateIndexLists();
			}
		}

		stringList = calculate(stringList ,-1,stringList.size(), f);
		InsertXcontent(stringList);
		return stringList;
	}
	
	public static void InsertXcontent(ArrayList<String> stringList){
		for(int i = 0; i < stringList.size(); i++){
			if(stringList.get(i).equals("x")){
				stringList.set(i, xContent.get(xContent.size()-1));
				xContent.remove(xContent.size()-1);
			}
		}
	}
	
	public static ArrayList<String> calculate(ArrayList<String> stringList,  int one, int two, Funktion f){
		boolean containsx = false;
		for(int i = one + 1; i < two; i++){
			if(!stringList.get(i).equals("x")){
				if(isVar(stringList.get(i), f.p)){
					String str = getCorrespondingValue(stringList.get(i), f.p);
					stringList.set(i, str);
				}
			}else{
				containsx = true;
			}
			if(Identity.size() < i + 1){
			System.out.println(stringList + " " + i);
			}
			if(Identity.get(i) == 2){
				String str = calcFunktion(stringList.get(i), stringList.get(i+1), f.p);
				stringList.set(i, str);
				stringList.remove(i+1);
				Identity.remove(i+1);
				two -= 1;
			}
		}
		ArrayList<String> str = CalculateStringList(stringList, Identity, one, two);
		
		if(one != -1 && two != stringList.size() && !containsx){
			stringList.remove(one+2);
			stringList.remove(one);
			Identity.remove(one+2);
			Identity.remove(one);
		}
		if(one != -1 && two != stringList.size() && containsx){
			String xcontent = "";
			for(int i = one; i < two+1; i++){
				xcontent += stringList.get(i);
			}
			xContent.add(xcontent);
			stringList.set(one, "x");
			Identity.set(one, 1);
			for(int i = one+1; i < two+1; i++){
				stringList.remove(i);
				Identity.remove(i);
			}
		}

		return str;
	}
	public static void updateIndexLists(){
		ArrayList<Integer> syntax = Identity;
		idx2.clear();
		idx3.clear();
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
		if(idx2.get(0).size()==0){
			idx2.clear();
			idx3.clear();
		}
		
	}
	
	public static ArrayList<String> CalculateStringList(ArrayList<String> list, ArrayList<Integer> Identitiy, int one, int two){
		
		firstPriority = new ArrayList<>();
		secondPriority = new ArrayList<>();
		thirdPriority = new ArrayList<>();
		ArrayList<ArrayList<Integer>> priorities = new ArrayList<>();
		priorities.add(firstPriority);
		priorities.add(secondPriority);
		priorities.add(thirdPriority);
		int k = 0;
		for(int s = 0; s < 3; s++){
			
			updatePriorityLists(list, one , two - k);

			
			k += calcPriorityLists(s, list);
			if(list.size()==1){
				s = 3;
			}
		}

		
		
		return list;
	}
	
	public static int calcPriorityLists(int priorityidx, ArrayList<String> list){
		int s = 0;
		double d = 0;
		if(priorityidx == 0){
			for(int i = 0; i < firstPriority.size(); i++){
				int idx = firstPriority.get(firstPriority.size() - 1- i);
				String number1 = list.get(idx-1);
				String number2 = list.get(idx+1);
				if(!(number1.equals("x") || number2.equals("x"))){
				d = Math.pow(Double.parseDouble(number1), Double.parseDouble(number2));
				for(int j = 1; j > -1; j--){
					list.remove(idx + j);
					Identity.remove(idx + j);
				}
				s += 2;
				list.set(idx -1, Double.toString(d));
				Identity.set(idx -1, 1);
				}
			
			}
		}
		if(priorityidx == 1){
			for(int i = 0; i < secondPriority.size(); i++){
				int idx = secondPriority.get(secondPriority.size() - 1- i);
				String number1 = list.get(idx-1);
				String number2 = list.get(idx+1);
				if(!(number1.equals("x") || number2.equals("x"))){
					d = 0;
					if(list.get(idx).equals("*")){
						d = Double.parseDouble(number1) * Double.parseDouble(number2);
					}
					if(list.get(idx).equals("/")){
						if(Double.parseDouble(number2)!=0){
							d = Double.parseDouble(number1) / Double.parseDouble(number2);
						}
					}
					for(int j = 1; j > -1; j--){
						list.remove(idx + j);
						Identity.remove(idx + j);
					}
					s += 2;
					list.set(idx -1, Double.toString(d));
					Identity.set(idx -1, 1);
				}
				
			}
		}
		if(priorityidx == 2){
			for(int i = 0; i < thirdPriority.size(); i++){
				int idx = thirdPriority.get(thirdPriority.size() - 1- i);
				String number1 = list.get(idx-1);
				String number2 = list.get(idx+1);
				d = 0;
				if(!(number1.equals("x") || number2.equals("x"))){
					if(list.get(idx).equals("+")){
						d = Double.parseDouble(number1) + Double.parseDouble(number2);
					}
					System.out.println(number1 + " "+ number2);
					if(list.get(idx).equals("-")){
						d = Double.parseDouble(number1) - Double.parseDouble(number2);
					}
					for(int j = 1; j > -1; j--){
						list.remove(idx + j);
						Identity.remove(idx + j);
					}
					s += 2;
					list.set(idx -1, Double.toString(d));
					Identity.set(idx -1, 1);
				}
			}
		}
		if(Double.isNaN(d) || Double.isInfinite(d)){
			d = Double.NaN;
			int f = list.size();
			list.clear();
			Identity.clear();
			list.add(Double.toString(d));
			Identity.add(1);
			return list.size()-1;
		}
		return s;
	}
	
	public static void updatePriorityLists(ArrayList<String> list, int one, int two){
		firstPriority.clear();
		secondPriority.clear();
		thirdPriority.clear();
		for(int i = one + 1; i < two; i++){
			if(list.get(i).equals("^")){
				firstPriority.add(i);
			}
			if(list.get(i).equals("*") || list.get(i).equals("/")){
				secondPriority.add(i);
			}
			if(list.get(i).equals("+") || list.get(i).equals("-")){
				thirdPriority.add(i);
			}
		}
	}
	
	public static boolean isVar(String str, Parser p){
		for(int i = 0; i < p.variable.size(); i++){

			if(str.equals(p.variable.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public static String getCorrespondingValue(String str, Parser p){
		for(int i = 0; i < p.variable.size(); i++){
			if(str.equals(p.variable.get(i))){
				return Double.toString(p.values.get(i));
			}
		}
		return "";
	}
	public static String calcFunktion(String str, String str2, Parser p){
		for(int i = 0; i < p.funktions.size(); i++){
			if(str.equals(p.funktions.get(i))){
				
				return MathFunktions.calc(i, str2);
			}
		}
		return "";
	}
	
}
