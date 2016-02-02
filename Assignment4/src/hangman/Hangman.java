package hangman;
import java.util.*;
import java.io.*;

/**
 * Assignment 4 for Cosc 121 
 * <p>Objective: Create a hangman game that utilizes File IO to select words by reading
 *  from a dictionary file and writing highscores to a file. Must handle all exceptions appropriately 
 *  
 * @author Martin Wallace
 * <p>marty.wallace@ymail.com
 * <p>Feb 1 2016
 *
 */
public class Hangman {

	private Scanner dicReader, input, hsReader;
	private File dictionaryFile, highScoreFile;
	private final String dicFileName = "res/dictionary.txt";  // change this if you move dictionary file
	private final String highScoreFileName = "res/highScores.txt"; // change this if you move highScore file
	private ArrayList<String> dictionary;
	
	/**
	 * Private constructor. This program is self-contained. Initialize File Readers and handle any file exceptions
	 */
	private Hangman(){
		this.dictionaryFile = new File(dicFileName);
		this.highScoreFile = new File(highScoreFileName);
		try{
			this.dicReader = new  Scanner(dictionaryFile);
		}catch(FileNotFoundException e){
			System.out.println("Dictionary could not be found in path " + dicFileName);
			System.exit(1);
		}
		try{
			this.hsReader = new Scanner(highScoreFile);		
		}catch(FileNotFoundException e){
			System.out.println("HighScore file could not be found in path: " + highScoreFileName);
			System.exit(2);
		}
		this.input = new Scanner(System.in);
	}

	/**
	 * Driver for program
	 * 
	 */
	public static void main(String[]args){
		Hangman assignment4 = new Hangman();
		assignment4.execute();
	}


	/**
	 * Main program loop. Loads words into dictionary then selects a random word and starts a game. After game completion the user is prompted to play again. 
	 */
	private void execute(){
		boolean running = true;
		loadWordsIntoDictionary();
		while(running){
			String word = selectRandomWord();
			int score = playGame(word, 0);
			handleHighScores(score);
			break;
		}
		dicReader.close();
		hsReader.close();
		
	}

	/**
	 * Game loop for single game of hang man. Sets guesses to 7 then prompts user for a guess. 
	 * <p>
	 * Upon completion of a game if the user managed to guess the word before running out of guesses then this function 
	 * is called again with a new word and the users total score from the last game.
	 * If the user did not manage to get the word then their score is sent back to the main game loop
	 * @param word
	 * {@code String} representation of the word the user will be guessing
	 * @param score
	 * {@code int} current score of user before beginning to guess this word.
	 * @return
	 * Returns an {@code int} representing the final score of the user after the game is over.
	 */
	private int playGame(String word, int score){
		int guesses = 7, correct = 0;
		int wordLength = word.length();
		char[]wordArray = word.toCharArray();
		ArrayList<Character> correctLetters = new ArrayList<Character>();
		ArrayList<Character> incorrectLetters = new ArrayList<Character>();
		boolean gameOver = false;
		while(!gameOver){
			System.out.print("Hidden Word: ");
			for(char c : wordArray){
				if(correctLetters.contains(c)){
					System.out.print(Character.toUpperCase(c)+ " ");
				}else{
					System.out.print("_" + " " );
				}
			}
			System.out.print("\nIncorrect Guesses: ");
			String comma = "";
			for(char c : incorrectLetters){
				System.out.print(comma + Character.toUpperCase(c) + " ");
				comma = ",";
			}
			System.out.println("\nGuesses left: " + guesses);
			System.out.println("Score: "+score);
			System.out.println("Enter next guess: ");
			char guess = getInput(correctLetters, incorrectLetters);
			boolean correctGuess = false;
			for(char c : wordArray){
				if(c == guess){
					correctGuess = true;
					correct ++;
				}
			}
			if(correctGuess){
				correctLetters.add(guess);
				score += 10;
			}else{
				incorrectLetters.add(guess);
				guesses --;
			}
			if(correct == wordLength){
				gameOver = true;
			}

			if(guesses == 0){
				gameOver = true;
			}

		}// end of single game loop

		if(guesses == 0){
			System.out.println("You lost!");
			System.out.println("The word was: " + word.toUpperCase());
			System.out.println("Your final score was: " + score);
			return score;
		}else{
			score += 100;
			System.out.println("You won!");
			System.out.println("The word was: " + word.toUpperCase());
			System.out.println("Your current score is: " + score + "\nYou get to play again!");
			return playGame(selectRandomWord(), score);
		}
	}

	/**
	 * Method to take input from the player. Rejects any non alphabetic characters. Rejects multiple characters. Handles any input exceptions. 
	 * Does not check if player has already guess the letter.
	 * @return
	 * Returns {@code char} representing players next guess
	 */
	private char getInput(ArrayList<Character> incorrect, ArrayList<Character> correct){
		boolean badInput = true;
		String ret = "";
		do{
			try{
				ret = input.nextLine().toLowerCase();
				badInput = false;
			}catch(Exception e){
				System.out.println("Please enter a valid guess");
			}
			if(ret.length() > 1){
				System.out.println("You can only enter 1 letter at a time \nPlease try again");
				badInput = true;
			}else if(ret.length() == 0){
				System.out.println("You must enter a letter");
				badInput = true;
			}else if(ret.charAt(0) > 'z' || ret.charAt(0) < 'a'){
				System.out.println("You must enter a letter \nPlease try again");
				badInput = true;
			}else if(incorrect.contains(ret.charAt(0)) || correct.contains(ret.charAt(0))){
				System.out.print("You have already guessed \"" + Character.toUpperCase(ret.charAt(0)) + "\" please guess again: ");
				badInput = true;
			}

		}while(badInput);
		
		return ret.charAt(0);
	}
	
	
	/**
	 * Reads the highscores off of the highScores.txt file and assesses if the player's score is to be added to the high score list. 
	 * Prints out the highscores to the user 
	 * Since FileNotFound was already handled in the constructor we know that highScores.txt is found therefore we can leave the catch block empty
	 * @param score
	 * {@code int} Final score of player
	 */
	private void handleHighScores(int score){
		ArrayList<Score> highScoreList = new ArrayList<Score>();
		
		while(hsReader.hasNext()){
			String hs = hsReader.nextLine();
			highScoreList.add(new Score(hs.split("-")[0], Integer.parseInt(hs.split("-")[1])));
		}
		for(int i = highScoreList.size(); i < 5; i++){
			highScoreList.add(new Score("Empty", 0));
		}

		for(int i = 0; i < highScoreList.size(); i++){
			if(score > highScoreList.get(i).value){
				System.out.println("Congratulations, you made the highscore list!");
				System.out.print("Please enter your name: " );
				String name = input.nextLine();
				highScoreList.add(new Score(name, score));
				break;
			}
		}
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(highScoreFile);
		} catch (FileNotFoundException e) {
		}
		Collections.sort(highScoreList);
		System.out.println("HighScore list!");
		for(int i = 0; i < 5; i++){
			Score s = highScoreList.get(i);
			pw.println(s);
			System.out.println((i+1) + ") " +  s);
			
		}
		pw.flush();
		pw.close();
		
	}

	/**
	 * Selects random word from dictionary
	 * @return
	 * {@code String} representation of single word from dictionary
	 */
	private String selectRandomWord(){
		int randomIndex =(int) (Math.random() * dictionary.size());
		return dictionary.get(randomIndex);
	}

	/**
	 * Initializes ArrayList dictionary and reads all the words off of the dictionary.txt file into the dictionary ArrayList  
	 */
	private void loadWordsIntoDictionary(){
		dictionary = new ArrayList<String>();
		while(dicReader.hasNext()){
			dictionary.add(dicReader.nextLine());
		}
	}
	
	
	/**
	 * 
	 * Simple class to hold name and score for a single HighScore on the list. 
	 * Implements comparable interface for easy sorting
	 * @author Martin Wallace
	 */
	class Score implements Comparable<Score>{
		int value;
		String name;
		
		public Score(String name, int value){
			this.name = name;
			this.value = value;
		}
		
		@Override
		public String toString(){
			return name+"-"+value;
		}

		@Override
		public int compareTo(Score s) {
			return s.value - this.value;
		}
	}
}
