package unical.libreria.detection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class OcrDetection {
	
	public Boolean FindText(String messaggio) throws FileNotFoundException {
		File f=new File("/home/michele/Scrivania/1.txt");
		String input, tesseract;
		
		input=messaggio;
		
		eseguiTesseract("tesseract /home/A.jpeg /home/michele/Scrivania/1");

		
		tesseract=leggiTesseract(f);
		
		if(valutaDifferenze(input, tesseract))
			return true;
		else
			return false;
		
	}
	
//	 public static String input() {
//	    	String a=JOptionPane.showInputDialog(null);
//	    	a=a.replaceAll(" ", "");
//	    	a=a.toLowerCase();
//	    	System.out.println(a);
//	    	return a;
//	    }
	      
	    public static Boolean valutaDifferenze(String a, String b) {
	    	
	    	/*int input=a.length();
	    	int tesseract=b.length();
	    	int distanza=calculate(a, b);
	    	int percent=((input-distanza)*100)/input;*/
	    	
	    	int input=a.length();
	    	int tesseract=b.length();
	    	int distanza=calculate(a, b);
	    	int percent=100-((100*distanza)/tesseract);
	    	
	    	System.out.println(input+ " "+ tesseract+ " "+ distanza + " "+percent+"%");
	    	
	    	if(percent>=60)
	    		return true;
	    	else
	    		return false;
	    }
	    
	    
	    
	    public static String leggiTesseract(File f) throws FileNotFoundException {
	    	String b="";
	    	Scanner sc = new Scanner(f); 
			while (sc.hasNextLine()) 
			     b+=sc.nextLine(); 
			b=b.replaceAll(" ", "");
		    b=b.toLowerCase();
		    System.out.println(b);
		    return b;
	    }
	    
	    
	    public static void eseguiTesseract(String Comando) {
	    	//esegui Tesseract
	    			try {
	    				Process p = Runtime.getRuntime().exec("fswebcam --no-banner -r 1920x1080 --jpeg 100 -D 0 A.jpeg");
	    				p.waitFor();
	    				p = Runtime.getRuntime().exec(Comando);
	    				p.waitFor();
	    			} catch (IOException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			} catch (InterruptedException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    }
	    
	    
	    public static int costOfSubstitution(char a, char b) {
	        return a == b ? 0 : 1;
	    }
	    
	    public static int min(int... numbers) {
	        return Arrays.stream(numbers)
	          .min().orElse(Integer.MAX_VALUE);
	    }
	    
	    static int calculate(String x, String y) {
	        int[][] dp = new int[x.length() + 1][y.length() + 1];
	     
	        for (int i = 0; i <= x.length(); i++) {
	            for (int j = 0; j <= y.length(); j++) {
	                if (i == 0) {
	                    dp[i][j] = j;
	                }
	                else if (j == 0) {
	                    dp[i][j] = i;
	                }
	                else {
	                    dp[i][j] = min(dp[i - 1][j - 1] 
	                     + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)), 
	                      dp[i - 1][j] + 1, 
	                      dp[i][j - 1] + 1);
	                }
	            }
	        }
	     
	        return dp[x.length()][y.length()];
	    }
}
