package cs601.project2;

import java.util.ArrayList;

public class AsyncOrderedBroker implements Broker<Object> {
	private BlockingQueueImpl<Object> orderedQueue;
	Thread runningClass;
	private ArrayList<Subscriber<Object>> subscribersList;
	private int pubCount;
	private long execTime;

	public AsyncOrderedBroker() {
		// TODO Auto-generated constructor stub
		System.out.println("Broker initializing");
		this.orderedQueue = new BlockingQueueImpl<Object>(10000);
		System.out.println("Blocking Queue Initialized");
		this.subscribersList = new ArrayList<Subscriber<Object>>();
		this.runningClass = new AsyncOrderedRun(orderedQueue, subscribersList);
//		runningClass = new AsyncOrderedRun(orderedQueue, timer);
		runningClass.start();
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
	public void subscribe(Subscriber<Object> subscriber) {
		// TODO Auto-generated method stub
		this.subscribersList.add(subscriber);

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		try {
			runningClass.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Didn't shutdown properly");
		}
		this.timer();
		return;
	}

	@Override
	public void publish(Object obj) {
		orderedQueue.put(obj);
	}
}
