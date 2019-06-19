package unical.libreria.capture;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageEditor {
		
BufferedImage primaImmagine;

int startHeight = 0;
int startWidth = 0;
int finalHeight = 0;
int finalWidth = 0;





public ImageEditor(String FirstImage) {
	
	primaImmagine = loadFirstImage(FirstImage);
}



public BufferedImage getPrimaImmagine() {
	return primaImmagine;
}



public void setPrimaImmagine(BufferedImage primaImmagine) {
	this.primaImmagine = primaImmagine;
}


public int getStartHeight() {
	return startHeight;
}

public void setStartHeight(int startHeight) {
	this.startHeight = startHeight;
}

public int getStartWidth() {
	return startWidth;
}

public void setStartWidth(int startWidth) {
	this.startWidth = startWidth;
}

public int getFinalHeight() {
	return finalHeight;
}

public void setFinalHeight(int finalHeight) {
	this.finalHeight = finalHeight;
}

public int getFinalWidth() {
	return finalWidth;
}

public void setFinalWidth(int finalWidth) {
	this.finalWidth = finalWidth;
}

public void cutImages(String newImageName) {
	BufferedImage originalImage = primaImmagine;
	BufferedImage ritaglio;

	//Settore ritaglio
	ritaglio = originalImage.getSubimage(startHeight,startWidth,finalHeight,finalWidth);
	ritaglio = rotateImage(ritaglio, Math.PI+Math.PI/2);
	saveImage(ritaglio, newImageName );
		
}

//public BufferedImage rotateImage(BufferedImage image) {
//	AffineTransform tx = new AffineTransform();
//	//tx.scale(scalex, scaley);
//	//tx.shear(shiftx, shifty);
//	//tx.translate(x, y);
//	tx.rotate(Math.PI+Math.PI/2, image.getHeight()/2,  image.getWidth()/2);
//	AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//	image = op.filter(image, null);
//	
//	return image;
//	
//}

public static BufferedImage rotateImage(BufferedImage image, double angle) {
    double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
    int w = image.getWidth(), h = image.getHeight();
    int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
    GraphicsConfiguration gc = getDefaultConfiguration();
    BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
    Graphics2D g = result.createGraphics();
    g.translate((neww - w) / 2, (newh - h) / 2);
    g.rotate(angle, w / 2, h / 2);
    g.drawRenderedImage(image, null);
    g.dispose();
    return result;
}

private static GraphicsConfiguration getDefaultConfiguration() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gd = ge.getDefaultScreenDevice();
    return gd.getDefaultConfiguration();
}

	public BufferedImage loadFirstImage(String FirstImage) {
		BufferedImage originalImage = null;

		try {
			originalImage = ImageIO.read(new File("C:\\Users\\ricky\\Desktop\\test\\" +FirstImage+".png" ));
		} catch (IOException e) {
			System.out.println("Caricamento immagine originale Fallito");
			e.printStackTrace();
		}
		return originalImage;
	}

	public void saveImage(BufferedImage image, String name) {
		try {
			ImageIO.write(image, "png", new File("C:\\Users\\ricky\\Desktop\\test\\"+name+".png"));
		} catch (IOException e) {
			System.out.println("Salvataggio Fallito");
			e.printStackTrace();
		}

	}
	
	public BufferedImage loadImage(String name) {
		BufferedImage originalImage = null;

		try {
			originalImage = ImageIO.read(new File("C:\\Users\\ricky\\Desktop\\test\\"+name+".png"));
		} catch (IOException e) {
			System.out.println("Caricamento immagine ritagliata Fallito");
			e.printStackTrace();
		}
		return originalImage;
	}


}
