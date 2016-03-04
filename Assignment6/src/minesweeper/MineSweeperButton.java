package minesweeper;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MineSweeperButton extends JButton implements MouseListener{
	
	
	private static String[] ICON_PATHS = {"res/0.png","res/1.png","res/2.png","res/3.png","res/4.png","res/5.png",
			"res/6.png","res/7.png","res/8.png","res/cover.png", "res/face-dead.png", "res/face-smile.png", "res/face-win.png"
			, "res/flag.png", "res/mine-grey.png", "res/mine-misflagged.png", "res/mine-red.png"};
	public static final int ZERO = 0, ONE = 1, TWO = 2, THREE = 3, FOUR = 4, FIVE = 5, SIX = 6, SEVEN = 7, EIGHT = 8, 
							 COVER = 9, FACE_DEAD = 10, FACE_SMILE = 11, FACE_WIN = 12, FLAG = 13, MINE_GREY = 14, 
							 MINE_MISFLAGGED = 15, MINE_RED = 16;
	
	private boolean isMine;
	private int num;
	private ArrayList<MineSweeperButton> neighbors;
	
	private int state;
	
	public MineSweeperButton(int num, boolean mine){
		this.isMine = mine;
		this.num = num;
		this.state = COVER;
		this.setIcon(new ImageIcon(ICON_PATHS[state]));
		this.addMouseListener(this);
	}
	

	
	
	public void setMine() {
		this.isMine = true;
	}
	
	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	public void setState(int state) {
		this.state = state;
		this.setIcon(new ImageIcon(ICON_PATHS[state]));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3){
			if(this.state == COVER){
				this.state = FLAG;
			}else if(this.state ==FLAG) {
				this.state = COVER;
			}
		}else if(e.getButton() == MouseEvent.BUTTON1){
			if(this.state == COVER){
				
				if(this.isMine){
					state = MINE_RED;
				}else{
					state = num;
				}
			}else{
				int flagCount = 0;
				for(MineSweeperButton m : neighbors){
					if(m.getState() == FLAG){
						flagCount++;
					}
				}
				if(flagCount == this.state && flagCount != 0){
					for(MineSweeperButton m : neighbors){
						m.mousePressed(e);
					}
				}
			}
		}
		this.setIcon( new ImageIcon(ICON_PATHS[state]));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	public int getState() {
		return this.state;
	}


}
