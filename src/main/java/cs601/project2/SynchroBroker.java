package cs601.project2;

import java.util.ArrayList;

public class SynchroBroker implements Broker<Object> {

	private ArrayList<Subscriber<Object>> subscribersList;
	private long execTime;
	private int pubCount;

	public SynchroBroker() {
		// TODO Auto-generated constructor stub
		this.subscribersList = new ArrayList<Subscriber<Object>>();
		this.execTime = 0;
		this.pubCount = 0;
	}

	@Override
	public void timer() {
		
		// TODO Auto-generated method stub
		execTime = System.currentTimeMillis() - execTime;
		pubCount++;
		
		if (pubCount == 2) {
			System.out.println("Time taken is : " + execTime);
		}
			
	}

	@Override
	synchronized public void publish(Object jobj) {
		// TODO Auto-generated method stub
		// call onevent method in subscriber interface
		if (subscribersList.isEmpty()) {
			System.out.println("No subscribers");
			return;
		} else {
			// for each subcriber in subcribersList call the onEvent function with jobj as
			// the argument.
			for (Subscriber<Object> sub : subscribersList) {
				sub.onEvent(jobj);
			}
		}

	}

	@Override
	public void subscribe(Subscriber<Object> subscriber) {
		// TODO Auto-generated method stub

		this.subscribersList.add(subscriber);
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
