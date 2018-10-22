package cs601.project2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SortingSubscriber implements Subscriber<Object> {
//	private String subscriberName;
	private Broker broker;
	private int refUnixTime;
	private int newFlag;
	private FileWriter outputFile;
	private BufferedWriter outputFileWriter;
	private int count;
	private JsonObject testObj;
	private int checkingTime;

	public SortingSubscriber(Broker broker, int flag, String outputFileName) throws IOException {
		this.broker = broker;
		this.refUnixTime = 1362268800;
		this.newFlag = flag;
		broker.subscribe(this);
		outputFile = new FileWriter(outputFileName);
		outputFileWriter = new BufferedWriter(outputFile);
		this.count = 0;
		testObj  = new JsonObject();
	}

	@Override
	synchronized public void onEvent(Object obj) {
		
		testObj = (JsonObject) obj;
		JsonElement timeElement = testObj.get("unixReviewTime");
		checkingTime = timeElement.getAsInt();

		if (this.newFlag == 1 && checkingTime > refUnixTime) {
			try {
				outputFileWriter.write(obj.toString());
				outputFileWriter.newLine();
				count++;
				outputFileWriter.flush();
			} catch (IOException e) {
				System.out.println("Error writing to output File");
			}
		} else if(this.newFlag == 0 && checkingTime <= refUnixTime) {
			try {
				outputFileWriter.write(obj.toString());
				outputFileWriter.newLine();
				count++;
				outputFileWriter.flush();
			} catch (IOException e) {
				System.out.println("Error writing to output File");
			}
		}
	}
	
	public int getCount() {
		return this.count;
	}


}
