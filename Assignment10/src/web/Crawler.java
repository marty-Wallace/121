package web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Crawler {

	private static final String FOLDER_PATH = "res/";
	private static final String TARGET_HEIGHT = "480", TARGET_WIDTH = "640";

	public void scrapeImages(String url, String folderName) {
		
		File file = new File(FOLDER_PATH + folderName);
		if(!file.exists()){
			file.mkdirs();
		}
		int count = 0; 
		if(folderName.charAt(folderName.length()-1) != '/'){
			folderName += '/';
		}
		System.out.println(++count);
		try{
			Document doc = Jsoup.connect(url).get();
			System.out.println(++count);
			Elements img = doc.getElementsByTag("img");
			System.out.println(++count);
			for(Element el : img) {

				String src = el.absUrl("src");

				System.out.println("Image Found");
				
	//			System.out.println("Image height " + el.attr("height"));
				if(el.attr("height") != TARGET_HEIGHT || el.attr("width") != TARGET_WIDTH) {
					System.out.println("Target height: " + TARGET_HEIGHT + "\n"
							+ "Actual height: " + el.attr("height") + "\n"
							+ "Target Width: " + TARGET_WIDTH + "\n"
							+ "Actual width: " + el.attr("width"));
					
					continue;
				}
				try{
					getImages(src, folderName);
				}catch(IOException e){
					System.out.println("Could not write " + src + " to disk");
				}
			}
			System.out.println(++count);
		}catch(IOException ex){
			System.err.println("There was an error");
		}
	}

	private static void getImages(String src, String folderName) throws IOException {

		String folder = folderName; 

		int indexName = src.lastIndexOf("/");

		if(indexName == src.length()) {
			src = src.substring(1, indexName);
		}

		indexName = src.lastIndexOf("/");
		String name = src.substring(indexName, src.length());

		System.out.println(name);

		URL url = new URL(src);
		InputStream in = url.openStream();

		OutputStream out = new BufferedOutputStream(new FileOutputStream(FOLDER_PATH + folder + name));

		for(int b; (b = in.read()) != -1;){
			out.write(b);
		}
		out.close();
		in.close();
	}
}
