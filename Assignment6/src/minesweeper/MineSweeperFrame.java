package minesweeper;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MineSweeperFrame extends JFrame {

	private static final int[][] ASSIGNMENT_GRID = {{0,0,0,0,0},{0,1,0,0,0},{0,0,0,1,0},{0,0,0,1,0},{0,0,0,0,0}}; //assignment required grid
	private static final boolean STATIC_GRID = true; // change for random mines

	private JPanel grid, top , full;
	private MineSweeperButton[] buttons;
	private MineSweeperButton face;
	private JButton time;
	private JButton minesLeft;
	int[][] map;
	private boolean gameOver;
	private int flags, actualMinesLeft, timeElapsed;
	private int rows, cols;

	public static void main(String[] args) {
		MineSweeperFrame assignment6 = new MineSweeperFrame(5, 5);
	}

	public MineSweeperFrame() {
		new MineSweeperFrame(5, 5);
	}

	public MineSweeperFrame(int rows, int cols) {
		this.gameOver = false;
		this.full = new JPanel(new BorderLayout());
		this.grid = new JPanel(new GridLayout(rows,cols));
		this.top = new JPanel(new GridLayout(1,3));

		this.map = new int[5][5];
		this.buttons = new MineSweeperButton[rows*cols];
		initializeGrid();
		initializeMenu();
		full.add(grid, BorderLayout.CENTER);
		full.add(top, BorderLayout.NORTH);

		this.add(full);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setSize((33*rows), (32*cols)+102);

		this.run();

	}

	public void run() {
		long startTime = System.currentTimeMillis();
		while(!gameOver) {

			long currentTime = System.currentTimeMillis();
			long elapsedTime = currentTime-startTime;

			if(elapsedTime > 1000){
				elapsedTime = 0;
				timeElapsed++;
				update();
				time.setText(String.valueOf(timeElapsed));
				minesLeft.setText(String.valueOf(Math.max(actualMinesLeft-flags, 0)));
				startTime = System.currentTimeMillis();

			}

			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}


	public void initializeGrid() {
		this.actualMinesLeft = 0;
		if(STATIC_GRID){
			this.map = ASSIGNMENT_GRID;
		}else{
			this.map = createNewMap();
		}
		int counter = 0;
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){


				int num = 0;


				boolean north = true, east = true, south = true, west = true;
				if(i == 0){ north = false; }
				if(i == map.length -1){ south = false;}
				if(j == 0){ west = false;}
				if(j == map[0].length -1){ east = false; }

				if(north && map[i-1][j] == 1){ num++; }
				if(east && map[i][j+1] == 1) { num++; }
				if(south && map[i+1][j] == 1) {num++; }
				if(west && map[i][j-1] == 1) { num++; }
				if(north && east && map[i-1][j+1] == 1){ num++; }
				if(north && west && map[i-1][j-1] == 1){ num++; }
				if(south && east && map[i+1][j+1] == 1){ num++; }
				if(south && west && map[i+1][j-1] == 1){ num++; }

				if(map[i][j] == 0){
					buttons[counter]= new MineSweeperButton(num, false);
					grid.add(buttons[counter]);
				}else if(map[i][j] == 1) {
					actualMinesLeft++;
					buttons[counter] = (new MineSweeperButton(num, true));
					grid.add(buttons[counter]);
				}

				counter ++;

			}
		}
	}


	public void initializeMenu() {

		face = new MineSweeperButton(0,false);
		face.setState(MineSweeperButton.FACE_SMILE);
		time = new JButton(String.valueOf(time));
		minesLeft = new JButton(String.valueOf(actualMinesLeft));
		top.add(minesLeft);
		top.add(face);
		top.add(time);

	}


	public int[][] createNewMap(){
		int[][] newMap = new int[map.length][map[0].length];
		//TODO add randomized map
		return newMap;
	}


	public void update(){

		int counter = 0;
		int currentFlags = 0, currentActualMinesLeft = actualMinesLeft, actualMinesFound = 0;;
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){

				if(buttons[counter].getState() == MineSweeperButton.FLAG){
					currentFlags++;
				}

				if(map[i][j] == 1 && buttons[counter].getState() == MineSweeperButton.MINE_RED ) {
					face.setState(MineSweeperButton.FACE_DEAD);
					gameOver = true;
				}else if(map[i][j] == 1 && buttons[counter].getState() == MineSweeperButton.FLAG ) {
					actualMinesFound++;
				}

				counter++;
			}
		}
		if(actualMinesFound == 0){}
		this.actualMinesLeft = currentActualMinesLeft;
		this.flags = currentFlags;

	}
}

