package minesweep;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Assignment 6 for Cosc 121. 
 * Objective: Learn GUI's by creating a functional MineSweeper game 
 * Main class that runs the MineSweeper panel, creates all game components and runs the game loop. 
 * @author Martin Wallace 
 *
 */
public class MineSweeper {

	private final static int  BEGINNER = 0, INTERMEDIATE = 1, EXPERT = 2; // index of each of the difficulties available
	private final static int ROW = 0, COL = 1, MINES = 2;  // indexes of the information in the VALUES array
	private final static int [][] VALUES = {{8, 8, 10}, {16, 16, 40}, {16, 32, 99}}; // rows, cols, and mines for each difficulty
	
	private final static int DEFAULT = 1; // change this to change the default starting difficutly
	
	private int difficulty;
	public static boolean gameOver;
	private boolean newGame; //flag for if the player has requested a new game, runs a continue in the game loop
	private boolean firstClick; // flag for when the player 
	private JFrame frame;
	private JPanel all, top, center;
	private JButton face, timer, mines;
	private JMenuBar menu;
	private JMenu fileMenu, newGameSubMenu;
	private JMenuItem newBeginner, newIntermediate, newExpert, exit;
	private MineSweeperButton[][] grid; 
	private int[][] map;
	private int totalMines;
	
	/**
	 * Main Method to start up program 
	 */
	public static void main(String[]args) {
		MineSweeper assignment6 = new MineSweeper();
		assignment6.execute();
	}
	
	/**
	 * Main Game loop that runs until Program finishes. 
	 * Initializes frame then adds the menu and components. 
	 * Updates the timer and mines left text items and checks for game-over conditions. 
	 */
	public void execute() {
		difficulty = DEFAULT;
		initializeFrame();
		initializeMenu();
		while(true) {

			gameOver = false;
			this.newGame = false;
			this.firstClick = true;
			setMap();
			setTopButtons();
			frame.pack();
			frame.validate();
			frame.repaint();
			long startTime = System.currentTimeMillis();
			int timeCounter = 0;
			timer.setText("0");
			while(!update()){ 
				// game running while update returns false
				if(System.currentTimeMillis() - startTime > 1000 && !firstClick && !gameOver){
					timeCounter++;
					timer.setText(String.valueOf(timeCounter));
					startTime = System.currentTimeMillis();
				}
			}
			clearComponents();
		}
	}
	
	/**
	 * Clears components from frame and invalidates 
	 */
	public void clearComponents(){
		frame.getContentPane().removeAll();
		frame.invalidate();
	}
	
	/**
	 * Parses over game board and checks for game-over conditions. 
	 * @return
	 * False if game is over. Else True
	 */
	public boolean update(){
		
		boolean mineClicked = false;
		int flagCount = 0, minesFlagged = 0, uncoveredCount = 0;
		for(int i = 0; i < VALUES[difficulty][ROW]; i++) {
			for(int j = 0; j < VALUES[difficulty][COL]; j++) {
				if(gameOver) { break; }
				if(grid[i][j].getState() == MineSweeperButton.FLAG){
					flagCount ++;
					if(grid[i][j].IS_A_MINE){
						minesFlagged ++;
					}
				}
				if(grid[i][j].getState() <= 8) {  //means that it has been uncovered 
					uncoveredCount ++;
				}
				if(grid[i][j].getState() == MineSweeperButton.MINE_RED){
					mineClicked = true;
				}
			}
		}
		
		if(uncoveredCount > 0){
			firstClick = false;
		}
		
		/////////////////Handling Face-Icon for Game-over////////////////////////////
		if(mineClicked){
			gameOver = true;
			face.setIcon(new ImageIcon(MineSweeperButton.ICON_PATHS[MineSweeperButton.FACE_DEAD]));
			showGameLostScreen();
		}else if(minesFlagged == VALUES[difficulty][MINES]){
			gameOver = true;
			face.setIcon(new ImageIcon(MineSweeperButton.ICON_PATHS[MineSweeperButton.FACE_WIN]));
			JOptionPane.showMessageDialog(null, "Congratulations you won!");
		}else if(uncoveredCount == VALUES[difficulty][ROW] * VALUES[difficulty][COL] - VALUES[difficulty][MINES]) {
			gameOver = true;
			face.setIcon(new ImageIcon(MineSweeperButton.ICON_PATHS[MineSweeperButton.FACE_WIN]));
			JOptionPane.showMessageDialog(null, "Congratulations you won!");
		}
		
		mines.setText(String.valueOf(Math.max((VALUES[difficulty][MINES] - flagCount), 0)));
		
		try {Thread.sleep(20);} catch (InterruptedException e) {}
		
		return gameOver && newGame;
	}
	
	
	/**
	 * Initializes frame sets title/visible/default close operation
	 */
	public void initializeFrame(){
		frame = new JFrame();
		frame.setTitle("MineSweeper");
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	
	/**
	 * Initializes the Buttons for the minesweeper grid and sets them to be mines or numbers 
	 */
	public void setMap() {
		generateMap();
		all = new JPanel(new BorderLayout());
		center = new JPanel(new GridLayout(VALUES[difficulty][ROW],VALUES[difficulty][COL]));
		grid = new MineSweeperButton[VALUES[difficulty][ROW]][VALUES[difficulty][COL]];

		for(int i = 0; i < VALUES[difficulty][ROW]; i++) {
			for(int j = 0; j < VALUES[difficulty][COL]; j++) {
				int num = 0;
				boolean north = true, east = true, south = true, west = true; 
				/////flags to avoid going out of bounds /////////////
				if(i == 0){ north = false; }
				if(i == map.length -1){ south = false;}
				if(j == 0){ west = false;}
				if(j == map[0].length -1){ east = false; }
				/////////check up down left and right///////////////
				if(north && map[i-1][j] == 1){ num++; }
				if(east && map[i][j+1] == 1) { num++; }
				if(south && map[i+1][j] == 1) {num++; }
				if(west && map[i][j-1] == 1) { num++; }
				///////////check corners /////////////////
				if(north && east && map[i-1][j+1] == 1){ num++; }
				if(north && west && map[i-1][j-1] == 1){ num++; }
				if(south && east && map[i+1][j+1] == 1){ num++; }
				if(south && west && map[i+1][j-1] == 1){ num++; }
				//////check if square is a bomb///////////////
				if(map[i][j] == 0){
					grid[i][j] = new MineSweeperButton(num, false);
					center.add(grid[i][j]);
				}else{
					grid[i][j] = new MineSweeperButton(num, true);
					center.add(grid[i][j]);
				}
			}
		}
		/////////////adding neighbor buttons to each button///////////
		for(int i = 0; i < VALUES[difficulty][ROW]; i++) {
			for(int j = 0; j < VALUES[difficulty][COL]; j++) {
				boolean north = true, east = true, south = true, west = true; /////flags to avoid going out of bounds 
				if(i == 0){ north = false; }
				if(i == map.length -1){ south = false;}
				if(j == 0){ west = false;}
				if(j == map[0].length -1){ east = false; }
				////////////adding up down left and right neighbors///////
				if(north){grid[i][j].addNeighbor(grid[i-1][j]); }
				if(east) {grid[i][j].addNeighbor(grid[i][j+1]); }
				if(south){grid[i][j].addNeighbor(grid[i+1][j]); }
				if(west) {grid[i][j].addNeighbor(grid[i][j-1]); }
				//////adding diagonal neighbors ///////////////////
				if(north && east) { grid[i][j].addNeighbor(grid[i-1][j+1]); }
				if(north && west) { grid[i][j].addNeighbor(grid[i-1][j-1]); }
				if(south && east) { grid[i][j].addNeighbor(grid[i+1][j+1]); }
				if(south && west) { grid[i][j].addNeighbor(grid[i+1][j-1]); }
				
			}
		}
		
		all.add(center, BorderLayout.CENTER);  // finaly add everything to the frame 
		frame.add(all);

	}
	
	/**
	 * Sets the face, timer and mines left buttons 
	 */
	public void setTopButtons(){
		top = new JPanel(new GridLayout(1,3));
		mines = new JButton(String.valueOf(totalMines));
		timer = new JButton("0");
		face = new JButton();
		
		face.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				gameOver = true;
				newGame = true;
				face.setIcon( new ImageIcon(MineSweeperButton.ICON_PATHS[MineSweeperButton.FACE_SMILE]));
			}
		});
		face.setIcon( new ImageIcon(MineSweeperButton.ICON_PATHS[MineSweeperButton.FACE_SMILE]));
		top.add(mines);
		top.add(face);
		top.add(timer);
		all.add(top, BorderLayout.NORTH);
	}

	/**
	 * Generates a random map based on the current difficulty 
	 */
	public void generateMap() {
		map = new int[VALUES[difficulty][ROW]] [VALUES[difficulty][COL]];
		this.totalMines = VALUES[difficulty][MINES];
		Random ran = new Random();
		for(int i = 0; i < totalMines; i++) {
			int row = ran.nextInt(VALUES[difficulty][ROW]);
			int col = ran.nextInt(VALUES[difficulty][COL]);
			if(map[row][col] == 1) { i--;  continue; } 
			map[row][col] = 1; 
		}
	}
	
	/**
	 * Sets the menu for the frame with a new game option for different difficulties and and exit option 
	 */
	public void initializeMenu() {
		menu = new JMenuBar();
		fileMenu = new JMenu("File");
		newGameSubMenu = new JMenu("New Game");
		exit = new JMenuItem("Exit");
		newBeginner = new JMenuItem("Beginner");
		newIntermediate = new JMenuItem("Intermediate");
		newExpert = new JMenuItem("Expert");

		newBeginner.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameOver = true;
				newGame = true;
				difficulty = BEGINNER;
			}
		});
		newIntermediate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameOver = true;
				newGame = true;
				difficulty = INTERMEDIATE;
			}
			
		});
		newExpert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newGame = true;
				gameOver = true;
				difficulty = EXPERT;
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		newGameSubMenu.add(newBeginner);
		newGameSubMenu.add(newIntermediate);
		newGameSubMenu.add(newExpert);
		fileMenu.add(newGameSubMenu);
		fileMenu.add(exit);
		menu.add(fileMenu);
		frame.setJMenuBar(menu);
	}
	
	/**
	 * Displays all mines that were not flagged or were misflagged after the game is lost 
	 */
	public void showGameLostScreen() { 
		for(int i = 0; i < VALUES[difficulty][ROW]; i++) {
			for(int j = 0; j < VALUES[difficulty][COL]; j++) {
				MineSweeperButton temp = grid[i][j];
				if(temp.getState() == MineSweeperButton.COVER ) {
					if(temp.IS_A_MINE) {
						temp.setState(MineSweeperButton.MINE_GREY); 
					}
				}else if(temp.getState() == MineSweeperButton.FLAG) { 
					if(!temp.IS_A_MINE) { temp.setState(MineSweeperButton.MINE_MISFLAGGED); }
				}
			}
		}
			
	}
}
