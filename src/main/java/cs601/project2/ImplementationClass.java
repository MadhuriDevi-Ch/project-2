package cs601.project2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class ImplementationClass {

	public ImplementationClass() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String brokerImplement = null;
		JsonParser intial = new JsonParser();
		Broker broker = null;
		String[] inputArray = null ,outputArray = null;
		
		try (BufferedReader configReader = Files.newBufferedReader(Paths.get("ConfigFile.json"),
				Charset.forName("ISO-8859-1"))) {

			String initialLine;
			while ((initialLine = configReader.readLine()) != null) {
				try {

					JsonElement element = intial.parse(initialLine);
					JsonObject object = element.getAsJsonObject();
					JsonElement brokerElement = object.get("BrokerType");
					brokerImplement = brokerElement.getAsString();
					JsonElement insizeElement = object.get("No.of Input Files");
					JsonElement outsizeElement = object.get("No.of Output Files");
					inputArray = new String[insizeElement.getAsInt()];
					
					for(int i=0; i<inputArray.length;i++) {
						int j=i+1;
						JsonElement inputElement = object.get("InputFile"+j);
						inputArray[i] = inputElement.getAsString();
						
					}
					outputArray = new String[outsizeElement.getAsInt()];
					for(int i=0; i<outputArray.length;i++) {
						int j=i+1;
						JsonElement outputElement = object.get("OutputFile"+j);
						outputArray[i] = outputElement.getAsString();
						
					}
				

				} catch (JsonParseException e) {
					System.out.println("there is an error in the record");
				}
			}

		} catch (Exception e) {
			// System.out.println(e);
			System.out.println("error in the line ");

		}
		
		switch (brokerImplement) {
		case "SynchroBroker":
			broker = new SynchroBroker();
			break;
		case "AsynchroOrderedBroker":

			broker = new AsyncOrderedBroker();
			break;
			
		case "AsynchroUnorderedBroker":
			broker = new AsyncUnorderedBroker();
			break;
		
		default:
			System.out.println("Please specify a valid Broker Implementation");
			
		}

		 
		broker.timer();
		
		Thread pub1 = new Publisher(inputArray[0], broker);
		Thread pub2 = new Publisher(inputArray[1], broker);
		pub1.start();
		pub2.start();
		System.out.println("Publishers running");
		SortingSubscriber newSubscriber = new SortingSubscriber(broker, 1);
		SortingSubscriber oldSubscriber = new SortingSubscriber(broker, 0);
		System.out.println("Subscribers Initialized");
		pub1.join();
		pub2.join();
		broker.shutdown();
		System.out.print("Sent all objects to Subcribers");
	}
}
