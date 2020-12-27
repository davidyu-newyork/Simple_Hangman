import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class WordCounter {

    // The rest of your code must run with no changes.
    public static final Path FOLDER_OF_TEXT_FILES  = Paths.get("C:\\Users\\David Yu\\eclipse-workspace\\hangman\\src\\text"); // path to the folder where input text files are located
    public static final Path WORD_COUNT_TABLE_FILE = Paths.get("C:\\Users\\David Yu\\eclipse-workspace\\hangman\\src\\new.txt"); // path to the output plain-text (.txt) file
    public static final int  NUMBER_OF_THREADS     = 1;                // max. number of threads to spawn 
   
    
    
    public static int index =0;
    private static int maxLength = 0;
    private static boolean flag = true;
    private static ArrayList<Thread> threads = new ArrayList<>();
    private static ArrayList<String> words = new ArrayList<>(); 
    private static ArrayList<String> fileNames = new ArrayList<>(); 
    private static ArrayList<HashMap<String,Integer>> maps = new ArrayList<>(); 
    public static HashMap<String, Integer> map = new HashMap<>();
    private static ArrayList<Integer> columnWidth = new ArrayList<>();
    private static Object lock = new Object();
    
    
	public static void main(String[] args) {

		File c = FOLDER_OF_TEXT_FILES.toFile();
		File[] listFiles = c.listFiles();
		double size = (double)listFiles.length;
		double yoo = size/NUMBER_OF_THREADS ;
		yoo =Math.round(yoo); 
		ArrayList<File> arList = new ArrayList<>();
		for(File d : listFiles) {
			arList.add(d);
			
		}
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			ArrayList<File> tempList = new ArrayList<>();
			tempList.clear();
			for (int j = 0; j < yoo; j++) {
				if (arList.size()>0) {
					tempList.add(arList.get(0));
					arList.remove(0);
				}
				
			}
			threads.add(createNewThread(tempList)); // --------------- CREATE NEW THREAD
			
		}
		for (int i = 0; i < threads.size(); i++) {
			try {
				threads.get(i).join();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		String[] yi = words.toArray(new String[words.size()]);
		Arrays.sort(yi);
		for (String r : yi) {
			if (r.length()>maxLength)
				maxLength = r.length();
		}
		
		for(String name : fileNames) {
			columnWidth.add(name.length()+3);
		}

		finallyWrite(yi);


	}
		public synchronized static void finallyWrite(String[] yi) {
			try {
				FileWriter fileWriter = new FileWriter(WORD_COUNT_TABLE_FILE.toFile());
			    PrintWriter printer = new PrintWriter(fileWriter);
			    for (int i = 0; i <= maxLength; i++) {
			    	printer.print(" ");
				}
			    for(String name: fileNames) {
			    	printer.print(name + "   ");
			    }
			    printer.print("total");
			    printer.println();
			    
			for(String word : yi) {
				printer.print(word);
				int temp = maxLength - word.length()+1;
				for (int i = 0; i < temp; i++) {
					printer.print(" ");
				}
				int temperoy = 0;
				for(HashMap<String,Integer> theMaps : maps) {
						
					int it = columnWidth.get(temperoy);
					int tempe =0;
					if(theMaps.get(word)==null) {
						printer.print(0);

					}
					else {
						tempe = theMaps.get(word);
						printer.print(tempe);}
					int length = String.valueOf(tempe).length();
					for (int i = 0; i < it-length; i++) {
						printer.print(" ");
					}
					temperoy++;
					}
				printer.print(map.get(word));
				printer.println();
			}
			printer.close();

			} catch (Exception e) {
				System.out.println(e);
			}
			
			
		}
		
	
	
	public synchronized static void write(String[] x,String fileName) {
		flag = false;

		HashMap<String,Integer> tempMap = new HashMap<>();
		maps.add(tempMap);
		fileNames.add(fileName);
		for(String c : x) {
			c =c.toLowerCase();
			if (!words.contains(c))
				words.add(c);
			if (map.get(c) ==null) {
				map.put(c, 1);
			}
			else
				map.put(c, map.get(c)+1);
			if (tempMap.get(c) ==null) {
				tempMap.put(c, 1);
			}
			else
				tempMap.put(c, tempMap.get(c)+1);
		}
		flag = true;

		
	}
	public static Thread createNewThread(ArrayList<File> x) {
		Thread thready = new Thread(new Counter(x));
		thready.start();
		return thready;
	}
	
	
	public static class Counter implements Runnable {

		private ArrayList<File> arr;

	    public Counter(ArrayList<File> arr) {
	        this.arr = arr;
	    }
	    
	    public synchronized void run() {
	    	
	    	try {
	    		for (int i = 0; i < arr.size(); i++) {
	    			String temp = new Scanner(arr.get(i)).useDelimiter("\\Z").next();
					String[] temp2 = temp.split("\\W+");
					synchronized (lock) {				
						if(!flag)
							{try {
								this.wait(); 
								write(temp2,arr.get(i).getName());
							} catch (Exception e) {
								System.out.println(e);
							}
							}
						else {
							flag = false;
							write(temp2,arr.get(i).getName());
							notifyAll();	
						}
						
					}

					}
				} catch (Exception e) {
					System.out.println(e);
					// TODO: handle exception
				} 
	    		}
					
				
				
	       // WordCounter.readFile(arr);
	    }
	
 
}

