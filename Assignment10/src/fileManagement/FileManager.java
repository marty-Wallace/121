package fileManagement;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import image.Tile;
import web.ModifiableImage;

/**
 * Class to manipulate images 
 * @author Martin
 *
 */
public class FileManager {
	
	
	/**
	 * Load images from a given directory and resize them down by 1/4
	 * @param tilesDir - the directory to load from 
	 * @return - an ArrayList of type Tile of all images from the given directory 
	 * @throws IOException
	 */
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

	/**
	 * load an image from the disk 
	 * @param imageFile - the path to the image 
	 * @return - BufferedImage 
	 */
	public static BufferedImage loadImage(File imageFile) {
		try{
			BufferedImage image = ImageIO.read(imageFile);
			if(image != null){
				return image;
			}
		}catch(IOException exc){
			
		}
		return null;
	}

	/**
	 * Save an image to the disk
	 * @param image - Image to save 
	 * @param file - The path of the file being saved
	 * @param IMAGE_FORMAT - The image format type 
	 */
	public static void saveImage(BufferedImage image, File file, String IMAGE_FORMAT){		
		try{
			ImageIO.write(image, IMAGE_FORMAT, file);
		}
		catch(IOException e) {
			return;
		}
	}
	
	/**
	 * Resize an image 
	 * @param img - The original image to be resized
	 * @param newW - The desired height of the new image
	 * @param newH - The desired Width of the new image 
	 * @return The resized image 
	 */
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  

}	

