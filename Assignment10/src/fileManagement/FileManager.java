package fileManagement;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import image.ModifiableImage;
import image.Tile;

public class FileManager {
	
	
	
    public static ArrayList<Tile> getImagesFromTiles(File tilesDir) throws IOException{
    	System.out.println("Reading files...");
		ArrayList<Tile> tileImages = new ArrayList<Tile>();
		File[] files = tilesDir.listFiles();
		for(File file : files){
			BufferedImage img = ImageIO.read(file);
			if (img != null){

				tileImages.add(new Tile(resize(img, img.getWidth()/4, img.getHeight()/4)));
			}
		}
		System.out.println("Done reading files...");
		return tileImages;
	}

	
	public static ModifiableImage loadImage(File imageFile) {
		try{
			BufferedImage image = ImageIO.read(imageFile);
			if(image != null){
				return new ModifiableImage(image);
			}
		}catch(IOException exc){
			
		}
		return null;
	}

	public static void saveImage(BufferedImage image, File file, String IMAGE_FORMAT){		
		try{
			ImageIO.write(image, IMAGE_FORMAT, file);
		}
		catch(IOException e) {
			return;
		}
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  

}	

