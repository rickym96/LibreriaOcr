package unical.libreria.detection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JOptionPane;

import unical.libreria.capture.ImageEditor;

//Classe ocr dove vengono eseguiti i riconoscimenti del testo 

public class OcrDetection {
	
	

	
	/*
	 * metodo sperimentale, il metodo restituisce un intero, in base al valore 
	 * l'algoritmo sarà camace di notificare l'utente la posizione del libro cercato.
	 * es: la libreria è in grado di contenere solo 4 libri a sezione ed è composta da 2 sezioni (A e B).
	 * viene restituito 3, l'algoritmo notifica l'utente che il libro richiesto è nella sezione A.
	 */
	
	public int FindText(String messaggio) throws FileNotFoundException {
		File f=new File("/home/pi/Desktop/here/1.txt");
		String input, tesseract;
		
		input=messaggio;
		
		getPhoto();
		
		for(int i=0; i<10; i++) {
		eseguiTesseract("tesseract /home/pi/Desktop/here/A"+i+".png /home/pi/Desktop/here/1");

		
		tesseract=leggiTesseract(f);
		
		if(valutaDifferenze(input, tesseract))
			return i;
		}
		
		return -1;
		
	}
	
	public int FindWithOutGetPhoto(String book) throws FileNotFoundException{
		for(int i=0; i<10; i++) {
			eseguiTesseract("tesseract /home/pi/Desktop/here/A"+i+".png /home/pi/Desktop/here/1");

			File f=new File("/home/pi/Desktop/here/1.txt");
			String tesseract=leggiTesseract(f);
			
			if(valutaDifferenze(book, tesseract))
				return i;
			}
			
			return -1;
	}
	
	

	      
	//Metodo per valutare la differenza tra il testo inserito e il testo letto da un libro tramite la webcam.
	//Se la percentuale di caratteri corrispondenti supererà quella impostata restituirà true 
	    public static Boolean valutaDifferenze(String a, String b) {
	    	
	    	/*int input=a.length();
	    	int tesseract=b.length();
	    	int distanza=calculate(a, b);
	    	int percent=((input-distanza)*100)/input;*/
	    	
	    	int input=a.length();
	    	int tesseract=b.length();
	    	int distanza=calculate(a, b);
	    	int percent=0;
	    	if(distanza>0 && tesseract>0)
	    		percent=100-((100*distanza)/tesseract);
	    	
	    	System.out.println(input+ " "+ tesseract+ " "+ distanza + " "+percent+"%");
	    	
	    	if(percent>=60)
	    		return true;
	    	else
	    		return false;
	    }
	    
	    
	    //Legge dal file di testo generato durante l'acquisizione per ottenere il testo del libro
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
	    
	    //Acquisizione foto tramite webcam
	    public void getPhoto() {
	    	//scatta una foto alla libreria
	    	try {
				Process p = Runtime.getRuntime().exec("fswebcam --no-banner -r 1920x1080 --jpeg 100 -D 4 /home/pi/Desktop/here/A.jpeg");
				p.waitFor();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	ImageEditor editor = new ImageEditor("A");
	        BufferedImage originale = editor.getPrimaImmagine();
	        editor.saveImage(originale, "test");
	        
	        editor.setStartHeight(66);
	        editor.setFinalHeight(157);
	        editor.setStartWidth(40);
	        
	        editor.setFinalWidth(610);
	        
	        
	        for(int i=0; i<5; i++){
	        	
	        	editor.cutImages("A"+i);
	        	editor.setStartHeight(66+170+(170*i));
	        }
	        
	        editor.setStartHeight(1010);
	        
	        for(int i=0; i<5; i++){
	        	
	        	editor.cutImages("A"+(i+5));
	        	editor.setStartHeight(1010+(170*(i+1)));
	        }
	        
	    }
	    
	    //Avvio procedura
	    public static void eseguiTesseract(String Comando) {
	    	//esegui Tesseract
	    			try {
	    				Process p = Runtime.getRuntime().exec(Comando);
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
