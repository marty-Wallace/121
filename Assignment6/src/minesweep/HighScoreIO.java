package minesweep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HighScoreIO {
	
	public Score[][] scores;
	private static final String path = "res/highscores.txt";
	
	public HighScoreIO() {
		this.scores = new Score[3][5];
		//readHighScores();
	}
	

	public String getDifficultyInfo(int difficulty){
		String ret = "";
		
		for(int i = 0; i < scores[difficulty].length; i++){
			ret += (i+1) + ") " + scores[difficulty][i].name + ": " + scores[difficulty][i].score + "\n";
		}
		return ret;
	}
	
	public void readHighScores() {
		File file = new File(path);
		Scanner fScan = null;
		try {
			fScan= new Scanner(file);
		} catch (FileNotFoundException e) { }
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 5; j++){
				String line = fScan.nextLine();
				String[]parts = line.split("::");
				scores[i][j] = new Score(parts[0], Integer.parseInt(parts[1]));
			}
		}
		fScan.close();
	}
	
	public void addScore(int difficulty, int time, String name){
		Score temp1 = null;
		Score temp2 = null;
		for(int i = 0; i < 5; i++){
			if(temp1 != null){
				temp2 = scores[difficulty][i];
				scores[difficulty][i] = temp1;
				temp1 = temp2;
			}
			if(time < scores[difficulty][i].score){
				temp1 = scores[difficulty][i];
				scores[difficulty][i] = new Score(name, time);
			}
		}
	}
	
	public boolean isHighScore(int difficulty, int time) {
		for(Score s : scores[difficulty]){
			if( time < s.score){
				return true;
			}
		}
		return false;
	}
	
	class Score{ 
		
		String name; 
		int score;
		
		public Score(String name, int score){
			this.score = score; 
			this.name = name;
		}
		
		public String toString(){
			return this.name + "::" + this.score;
		}
	}
	
	public void rewriteFile( ){
		PrintWriter pw = null;
		File file = new File(path);
		
		try{
			pw = new PrintWriter(file);
		}catch(FileNotFoundException e) { }
		

		for(int i = 0; i < 3; i++){
			for(Score s : scores[i]){
				pw.write(s.toString());
			}
		}
		
		pw.flush();
		pw.close();
	}

}
