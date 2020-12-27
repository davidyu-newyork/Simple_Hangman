import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GetWord {
	private String line = "";
	private ArrayList<String> words = new ArrayList<String>();
	private String fileName = "/words.txt";
	
	public GetWord() {
        try  {
        	
        	InputStream input = getClass().getResourceAsStream(fileName);
        	BufferedReader bf = new BufferedReader(new InputStreamReader(input));
        	line = bf.readLine();
        	
            while (line != null) {
            	
            	words.add(line);
            	line = bf.readLine();

            }
            	
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getRandomWord() {
        return words.get((int)( Math.random() * words.size()));
    }

}
 