package unical.libreria.capture;

import java.awt.image.BufferedImage;
import java.io.IOException;

//Classe Per testare a mano i ritagli, decommentare solo se serve fare test, altrimenti utilizzarla nel codice come già implementata

public class TestRitagli {

	public static void main(String[] args) {
		try {
			Process p = Runtime.getRuntime().exec("fswebcam --no-banner -r 1920x1080 --jpeg 100 /home/pi/Desktop/here/A.jpeg");
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
        editor.saveImage(originale, "provolazza");
//        originale =  editor.rotateImage(originale, Math.PI+Math.PI/2);
//        editor.saveImage(originale, "provolazzaRuotata");
        
        editor.setStartHeight(66);
        editor.setFinalHeight(157);
        editor.setStartWidth(40);
        
        editor.setFinalWidth(610);
        
        System.out.println("inizio...");
        
        for(int i=0; i<5; i++){
        	
        	editor.cutImages("A"+i);
        	editor.setStartHeight(66+170+(170*i));
        }
        
        editor.setStartHeight(1010);
        
        for(int i=0; i<5; i++){
        	
        	editor.cutImages("A"+(i+5));
        	editor.setStartHeight(1010+(170*(i+1)));
        }
        for(int i=0; i<10; i++){
        	try {
				Process p = Runtime.getRuntime().exec("tesseract /home/pi/Desktop/here/A"+i+".png /home/pi/Desktop/here/"+i);
				p.waitFor();
				System.out.println("fine tesseract "+i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        System.out.println("...fine!");
       
        

	}

}
