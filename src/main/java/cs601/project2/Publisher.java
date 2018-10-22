package cs601.project2;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;



public class Publisher extends Thread {
	private String fileName;
	private Broker synB;
//	private int unixTime;
	
	public Publisher(String inputFile, Broker synB) {
		// TODO Auto-generated constructor stub
		this.fileName = inputFile;
		this.synB = synB;
	}

	public void run() {
		JsonParser reviews = new JsonParser();

		int count = 0;
		try (BufferedReader reviewreader = Files.newBufferedReader(Paths.get(fileName), Charset.forName("ISO-8859-1"))) {
			
			String reviewLine;
			while ((reviewLine = reviewreader.readLine()) != null) {
				try {

					JsonElement element = reviews.parse(reviewLine);
					JsonObject object = element.getAsJsonObject();
					synB.publish(object);
					count++;
				} catch (JsonParseException e) {
					System.out.println("there is an error in the record");
				}
			}
			
			System.out.println("Published: " + count);

		} catch (Exception e) {
			// System.out.println(e);
			System.out.println("error in the line ");

		}
	}
}
