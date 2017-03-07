

import java.util.ArrayList;

public class CalculateString {
	
	static ArrayList<Integer> firstPriority;
	static ArrayList<Integer> secondPriority;
	static ArrayList<Integer> thirdPriority;
	
	public static double returnY(Funktion f, double x, ArrayList<String> stringList){
		f.p.values.set(0,x);
		//System.out.println(f.p.syntax);
		ArrayList<ArrayList<Integer>> idx2 = f.idx2;
		ArrayList<ArrayList<Integer>> idx3 = f.idx3;
		String str = f.funktion;
		for(int i = 0; i < idx2.size(); i++){
			for(int j = 0; j < idx2.get(i).size(); j++){
				stringList = calculate(stringList, x, idx2.get(i).get(idx2.get(i).size()-1-j), idx3.get(i).get(j), f);
				for(int s = 0; s < idx2.size(); s++){
					for(int d = 0; d < idx2.get(s).size(); s++){
						if(idx2.get(s).get(d) < idx3.get(i).get(j)){
							idx2.get(s).set(d,idx2.get(s).get(d) - (idx3.get(i).get(j)-idx2.get(i).get(idx2.get(i).size()-1-j)-2));
						}
						if(idx3.get(s).get(d) < idx3.get(i).get(j)){
							idx3.get(s).set(d,idx3.get(s).get(d) - (idx3.get(i).get(j)-idx2.get(i).get(idx2.get(i).size()-1-j)-2));
						}
					}
				}
			}
		}
		stringList = calculate(stringList ,x,-1,stringList.size(), f);

		//System.out.println(x + " " + stringList.get(0));
		return Double.parseDouble(stringList.get(0));
	}

	public static ArrayList<String> calculate(ArrayList<String> stringList, double x, int one, int two, Funktion f){
		for(int i = one + 1; i < two; i++){
			if(isVar(stringList.get(i), f.p)){
				String str = getCorrespondingValue(stringList.get(i), f.p);
				stringList.set(i, str);
			}
			if(f.p.Identity.get(i) == 2){
				String str = calcFunktion(stringList.get(i), stringList.get(i+1), f.p);
				stringList.set(i, str);
			}
		}
		ArrayList<String> str = CalculateStringList(stringList);
		
		return str;
	}
	
	public static ArrayList<String> CalculateStringList(ArrayList<String> list){
		
		firstPriority = new ArrayList<>();
		secondPriority = new ArrayList<>();
		thirdPriority = new ArrayList<>();
		ArrayList<ArrayList<Integer>> priorities = new ArrayList<>();
		priorities.add(firstPriority);
		priorities.add(secondPriority);
		priorities.add(thirdPriority);
		for(int s = 0; s < 3; s++){
			/*if(s < 4){

			System.out.println(" listsize"+ " : " + list.size());
			//System.out.println(" secondPrioritySize"+ " : " + secondPriority.size());
			for(int i = 0; i < list.size(); i++){
				System.out.println(i + " : " + list.get(i));
			}}*/
			updatePriorityLists(list);
			calcPriorityLists(s, list);
			s+= 1;
		}
		
		
		return list;
	}
	
	public static void calcPriorityLists(int priorityidx, ArrayList<String> list){
		if(priorityidx == 0){
			for(int i = 0; i < firstPriority.size(); i++){
				int idx = firstPriority.get(firstPriority.size() - 1- i);
				String number1 = list.get(idx-1);
				String number2 = list.get(idx+1);
				double d = Math.pow(Double.parseDouble(number1), Double.parseDouble(number2));
				for(int j = 1; j > -1; j--){
					list.remove(idx + j);
				}
				list.set(idx -1, Double.toString(d));
			}
		}
		if(priorityidx == 1){
			for(int i = 0; i < secondPriority.size(); i++){
				int idx = secondPriority.get(secondPriority.size() - 1- i);
				String number1 = list.get(idx-1);
				String number2 = list.get(idx+1);
				double d = 0;
				if(list.get(idx).equals("*")){
					d = Double.parseDouble(number1) * Double.parseDouble(number2);
				}
				if(list.get(idx).equals("/")){
					d = Double.parseDouble(number1) / Double.parseDouble(number2);
				}
				for(int j = 1; j > -1; j--){
					list.remove(idx + j);
				}
				list.set(idx -1, Double.toString(d));
			}
		}
		if(priorityidx == 2){
			for(int i = 0; i < thirdPriority.size(); i++){
				int idx = thirdPriority.get(thirdPriority.size() - 1- i);
				String number1 = list.get(idx-1);
				String number2 = list.get(idx+1);
				double d = 0;
				if(list.get(idx).equals("+")){
					d = Double.parseDouble(number1) + Double.parseDouble(number2);
				}
				if(list.get(idx).equals("-")){
					d = Double.parseDouble(number1) - Double.parseDouble(number2);
				}
				for(int j = 1; j > -1; j--){
					list.remove(idx + j);
				}
				list.set(idx -1, Double.toString(d));
			}
		}
	}
	
	public static void updatePriorityLists(ArrayList<String> list){
		firstPriority.clear();
		secondPriority.clear();
		thirdPriority.clear();
		for(int i = 0; i < list.size(); i++){
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
		for(int i = 0; i < p.funktions.length; i++){
			if(str.equals(p.funktions[i])){
				return MathFunktions.calc(i, str2);
			}
		}
		return "";
	}
	


}
