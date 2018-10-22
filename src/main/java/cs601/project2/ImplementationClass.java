package cs601.project2;

import java.io.IOException;

public class ImplementationClass {

	public ImplementationClass() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Broker sb = new AsyncOrderedBroker();
		sb.timer();
		Thread pub1 = new Publisher("reviews_Apps_for_Android_5.json", sb);
		Thread pub2 = new Publisher("reviews_Home_and_Kitchen_5.json", sb);
		pub1.start();
		pub2.start();
		System.out.println("Publishers running");
		SortingSubscriber newSubscriber = new SortingSubscriber(sb, 1);
		SortingSubscriber oldSubscriber = new SortingSubscriber(sb, 0);
		System.out.println("Subscribers Initialized");
		pub1.join();
		pub2.join();
		sb.shutdown();
		System.out.print("Sent all objects to Subcribers");
	}
}
