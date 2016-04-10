package minesweep;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MineSweeperButton extends JButton implements MouseListener{


	// Array of paths to the Icons 
	public static String[] ICON_PATHS = {"res/0.png","res/1.png","res/2.png","res/3.png","res/4.png","res/5.png",
			"res/6.png","res/7.png","res/8.png","res/cover.png", "res/face-dead.png", "res/face-smile.png", "res/face-win.png"
			, "res/flag.png", "res/mine-grey.png", "res/mine-misflagged.png", "res/mine-red.png", "res/face-oh.png"};

	// Constants for the possible states of the buttons 
	public static final int ZERO = 0, ONE = 1, TWO = 2, THREE = 3, FOUR = 4, FIVE = 5, SIX = 6, SEVEN = 7, EIGHT = 8, 
			COVER = 9, FACE_DEAD = 10, FACE_SMILE = 11, FACE_WIN = 12, FLAG = 13, MINE_GREY = 14, 
			MINE_MISFLAGGED = 15, MINE_RED = 16, FACE_OH = 17;

	private ArrayList<MineSweeperButton>neighbors; // all of the buttons neighbors 
	private int NUM; // the number of surrounding mines 
	boolean IS_A_MINE; // true if buttons is a mine 
	private int state; // integer representing the current state of the button 
	private MineSweeper game; // the board and frame 
	public int i, j;

	/**
	 * Constructor for MineSweeperButton 
	 * @param NUM - The number of neighbor mines to this button  
	 * @param IS_A_MINE - represents if this is a mine or not 
	 */
	public MineSweeperButton(MineSweeper game, int i, int j) {
		this.NUM = -1;
		this.neighbors = new ArrayList<MineSweeperButton>();
		setState(COVER);
		this.addMouseListener(this);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.game = game;
		this.i = i;
		this.j = j;
	}

	/**
	 * Set the number of mines This button has a neighbor
	 * @param NUM - The number of mines 
	 */
	public void setNum(int NUM){
		this.NUM = NUM;
	}

	/**
	 * Sets this button to be a mine
	 */
	public void IS_A_MINE(){
		this.IS_A_MINE = true;
	}


	/**
	 * Add Neighboring button to list of neighbors 
	 * @param m - a neighboring button
	 */
	public void addNeighbor(MineSweeperButton m ){
		this.neighbors.add(m);
	}

	/**
	 * Get the current state of this button. Will return 0-16
	 * @return - int representing current state. Also an index to the image path string 
	 */
	public int getState() {
		return this.state;
	}

	/**
	 * Sets the state and updates the icon of the button 
	 * @param state - The State the button is changing to 
	 */
	public void setState(int state) {
		this.state = state;
		this.setIcon(new ImageIcon(ICON_PATHS[state]));
	}

	@Override
	/**
	 *  Handles the mouse pressed event on this button and contains all the logic for click actions in minesweeper
	 */
	public void mousePressed(MouseEvent e) {

		if(MineSweeper.gameOver){
			return;
		}

			game.setFace(new ImageIcon(ICON_PATHS[FACE_OH]));


		// no right clicks before right clicks 
		if(game.isFirstClick() && e.getButton() == MouseEvent.BUTTON3) {
			return;
		}

		if(game.isFirstClick() && e.getButton() == MouseEvent.BUTTON1){
			game.generateMap(this ,e);
			return;
		}
		/////////Right click toggles between flag and cover //////
		if(e.getButton() == MouseEvent.BUTTON3){
			if(this.state == COVER){
				this.state = FLAG;
			}else if(this.state ==FLAG) {
				this.state = COVER;
			}
			
			//////////Left clicks ///////////////////
		}else if(e.getButton() == MouseEvent.BUTTON1){
			if(this.state == COVER){

				if(this.IS_A_MINE){
					state = MINE_RED;
				}else{
					state = NUM;
					if(this.state == ZERO){
						for(MineSweeperButton m : neighbors){ // If this button is empty fire the action event to all neighbors 
							m.mousePressed(e);
						}
					}
				}
			}else if(this.state < 8){ // If this button has already been revealed
				int flagCount = 0; 
				for(MineSweeperButton m : neighbors){ 
					if(m.getState() == FLAG){
						flagCount ++;  // count neighbors flagged 
					}
				}
				if(flagCount == this.NUM){   // if the right number of neighbors have been flagged 
					for(MineSweeperButton m : neighbors){
						if(m.getState() == COVER)  // send action event to unflagged neighbors 
							m.mousePressed(e);
					}
				}
			}
		}
		setState(state); 
	}

	/**
	 * Get the number of neighbors of this button that are mines 
	 * @return - the number of mines 
	 */
	public int getNum(){
		return this.NUM;
	}

	public ArrayList<MineSweeperButton>getNeighbors(){
		return this.neighbors;
	}


	@Override
	public void mouseReleased(MouseEvent e) {

		if(!game.gameOver){
			game.setFace(new ImageIcon(ICON_PATHS[FACE_SMILE]));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) { }
	
	@Override
	public void mouseEntered(MouseEvent e) { }
	
	@Override
	public void mouseExited(MouseEvent e) {	}
	
}