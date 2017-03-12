
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Writer {
	public ArrayList<ArrayList<Double>> m = new ArrayList<>();
	
	
    public void readFile(String fileName) {
        try {
          File file = new File(fileName);
          Scanner scanner = new Scanner(file);
          while (scanner.hasNextLine()) {
            ArrayList<Double> line = convertStringtoInt(scanner.nextLine());
            m.add(line);
          }
          scanner.close();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
	
    public void writeFile() throws IOException{

    }
    

    
    public void writeIntoFile(String p, String line){
    	Path path = Paths.get(p);
		List<String> lines;
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);

			String extraLine = line;  

			lines.add(extraLine);
			Files.write(path, lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    public void createNewFile(String s){
    	String dataPath = s;
		File f = new File(dataPath);
		f.getParentFile().mkdirs();
		try {
			f.createNewFile();
			System.out.println(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public double[] convertToList(ArrayList<Double> d, int k){
    	double[] s = new double[d.size() - k];
    	for(int i = k; i < d.size(); i++){
    		s[i-k] = d.get(i);
    	}
    	return s;
    }
    public double Mittelwert(ArrayList<Double> values){
    	double s = 0;
    	for(int i = 0; i < values.size(); i++){
    		s += values.get(i);
    	}
    	s /= values.size();
    	return s;
    }
    
    public double Standartabweichung(ArrayList<Double> values, double m){
    	if(values.size() > 1){
    	double s = 0;
    	for(int i = 0; i < values.size(); i++){
    		s += Math.pow((values.get(i) - m),2);
    	}
    	s = Math.sqrt(s/(values.size()*((values.size()-1))));
    	return s;
    	}
    	return 0;
    	
    }
    
    public ArrayList<Double> convertStringtoInt(String s){
    	ArrayList<Double> values = new ArrayList<>();
    	String d = "";
    	for(int i = 0; i < s.length(); i++){
    		d += Character.toString(s.charAt(i));
    		if(Character.toString(s.charAt(i)).equals(" ") || i + 1 == s.length()){
    			double k = Double.parseDouble(d);
    			d = " ";
    			values.add(k);
    		}
    	}

    	return values;
    }
    
	public static void main(String[] args) throws IOException{
		
	}
}
