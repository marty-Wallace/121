package image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import fileManagement.FileManager;
import gui.MosaicGUI;

/**
 * Class that does most of the work mosaic-ifying an image 
 * @author Martin
 *
 */
public class Mosaic {
	
	private static final String TILE_PATH = "res/jpg"; // path to tileset 
	private static final float RED_WEIGHT = .6f; // how important is red avg 
	private static final float GREEN_WEIGHT = 1.0f; // how important is green avg 
	private static final float BLUE_WEIGHT = 1.0f; // how important is blue avg 
	
	private BufferedImage image;
	private BufferedImage newImage;
	private Tile[][] mosaic;
	private List<Tile> tiles;
	MosaicGUI gui;

	/**
	 * Loads up tileset and informs user if tileset could not be found 
	 * @param gui - pass in the gui to allow MessageDialog 
	 */
	public Mosaic(MosaicGUI gui){
		this.gui = gui;
		try {
			 this.tiles = FileManager.getImagesFromTiles(new File(TILE_PATH));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(gui.getFrame(), "Could not load tiles from the path " + TILE_PATH, "ERROR!" , JOptionPane.ERROR_MESSAGE);
			gui.enableButton(false);
		}
	}
	
	/**
	 * Creates the mosaic 
	 * @param image - The image to be mosaic-ified 
	 * @return - new Image 
	 */
	public BufferedImage createMosaic(BufferedImage image) {
		this.image = image;

		this.mosaic = new Tile[image.getWidth()/tiles.get(0).image.getWidth()][image.getHeight()/tiles.get(0).image.getHeight()];
		
		return createImage();
	}
	
	
	/**
	 * Loops through image stepping by the width/height of the tile image creating a Tile of the sub-image 
	 * in the current spot then finds the Tile that has the closest RGB average to that subimage then draws it onto the newImage 
	 * @return
	 */
	private BufferedImage createImage() {
		
		//grab our width and height parameters 
		int tileHeight = tiles.get(0).image.getHeight();
		int tileWidth = tiles.get(0).image.getWidth();
		
		// loop through creating sub-images and then matching them 
		for(int i = 0; i < mosaic.length; i++) {
			for(int j = 0; j < mosaic[i].length; j ++) {
				Tile imagePiece = new Tile(image.getSubimage(i * tileWidth, j * tileHeight,  tileWidth, tileHeight));
				mosaic[i][j] = matchImagePiece(imagePiece);
			}
		}
		
		// now we have our whole image matched we will draw the image out 
		
		// grab width and height of new image 
		int width = mosaic.length * mosaic[0][0].image.getWidth();
		int height = mosaic[0].length * mosaic[0][0].image.getHeight();
		newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		//create graphics 
		Graphics2D g = newImage.createGraphics();
        Color oldColor = g.getColor();//
        g.setPaint(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(oldColor);
        //draw out image 
		for(int i = 0; i < mosaic.length; i++){
			for(int j = 0; j < mosaic[0].length; j++) {
				g.drawImage(mosaic[i][j].image,null, i * mosaic[i][j].image.getWidth(), j * mosaic[i][j].image.getHeight());
			}
		}
        g.dispose();
        
        return newImage;
	}
	
	/**
	 *  Here we will loop through all of potential tiles and score them by their average, sort them and select the best one 
	 * @param imagePiece - The subimage we are matching 
	 * @return
	 */
	private Tile matchImagePiece(Tile imagePiece) {
		
		int targetBlue = imagePiece.averageBlue;
		int targetRed = imagePiece.averageRed;
		int targetGreen = imagePiece.averageGreen;
		
		for(Tile t : tiles) {
			scoreTileByAverage(t, targetBlue, targetRed, targetGreen);
		}
		Collections.sort(tiles);

		Tile ret = tiles.get(0);
		return ret;
	}

	/**
	 * Score a tile based on it's the average of it's difference in color multiplied by it's weight per color 
	 * @param tile - The tile we are considering
	 * @param targetBlue - the target average blue value of the current sub-image 
	 * @param targetRed - the target average red value of the current sub-image 
	 * @param targetGreen - the target average green value of the current sub-image 
	 */
	private  void scoreTileByAverage(Tile tile, int targetBlue, int targetRed, int targetGreen) {
		
		int score = 0;
		score += (int) (Math.abs(tile.averageBlue - targetBlue) * BLUE_WEIGHT);
		score += (int)(Math.abs(tile.averageRed - targetRed) * RED_WEIGHT);
		score += (int)(Math.abs(tile.averageGreen - targetGreen) * GREEN_WEIGHT);
		
		
		tile.score = score;
	}

}
