package cs601.project2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SortingSubscriber implements Subscriber<Object> {
//	private String subscriberName;
	private Broker syncB;
	private int refUnixTime;
	private int newFlag;
	private FileWriter newFile, oldFile;
	private BufferedWriter newFileWriter, oldFileWriter;
	private int oldCount;
	private int newCount;
	private JsonObject testObj;
	private int checkingTime;

	public SortingSubscriber(Broker synB, int flag) throws IOException {
		// TODO Auto-generated constructor stub
//		this.subscriberName = subName;
		this.syncB = synB;
		this.refUnixTime = 1362268800;
		this.newFlag = flag;
		syncB.subscribe(this);
		newFile = new FileWriter("newrecords.txt");
		oldFile = new FileWriter("oldrecords.txt");
		newFileWriter = new BufferedWriter(newFile);
		oldFileWriter = new BufferedWriter(oldFile);
		this.oldCount = 0;
		this.newCount = 0;
		testObj  = new JsonObject();
	}

	@Override
	synchronized public void onEvent(Object obj) {
		
		testObj = (JsonObject) obj;
		JsonElement timeElement = testObj.get("unixReviewTime");
		checkingTime = timeElement.getAsInt();
		//System.out.println(checkingTime);
		//System.out.println((newFlag == 1) ? "NEW" : "OLD");
		// TODO Auto-generated method stub
		if (this.newFlag == 1) {

			if (checkingTime > refUnixTime) {
				try {
					newFileWriter.write(obj.toString());
					newFileWriter.newLine();
					newCount++;
					newFileWriter.flush();
					//System.out.println(newCount);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else
				return;

		} else if (checkingTime < refUnixTime) {
			try {
				oldFileWriter.write(obj.toString());
				oldFileWriter.newLine();
				oldCount++;
				oldFileWriter.flush();
				//System.out.println(oldCount);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			return;
	}

}
