package unical.libreria.capture;

import java.awt.image.BufferedImage;

public class TestRitagli {

	public static void main(String[] args) {

		ImageEditor editor = new ImageEditor("libreria");
        BufferedImage originale = editor.getPrimaImmagine();
        editor.saveImage(originale, "provolazza");
//        originale =  editor.rotateImage(originale, Math.PI+Math.PI/2);
//        editor.saveImage(originale, "provolazzaRuotata");
        
        editor.setStartHeight(60);
        editor.setStartWidth(230);
        editor.setFinalHeight(160);
        editor.setFinalWidth(550);
        
        
        editor.cutImages("provolazzaTagliata");
		
        editor.setStartHeight(220);
        editor.setFinalWidth(550);
        editor.cutImages("provolazzaTagliata2");
        
        
        editor.setStartHeight(380);
        editor.setFinalWidth(550);
        editor.cutImages("provolazzaTagliata3");
		
       
        editor.setStartHeight(540);
        editor.setFinalWidth(550);
        editor.cutImages("provolazzaTagliata4");
        

	}

}
