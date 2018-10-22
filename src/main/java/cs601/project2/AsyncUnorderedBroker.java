package cs601.project2;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncUnorderedBroker implements Broker<Object> {

	private ExecutorService pool;
	private ArrayList<Subscriber<Object>> subscribersList;
	private int pubCount;
	private long execTime;
	
	public AsyncUnorderedBroker() {
		// TODO Auto-generated constructor stub
		pool = Executors.newFixedThreadPool(100); 
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
	public void subscribe(Subscriber<Object> subscriber) {
		// TODO Auto-generated method stub
		this.subscribersList.add(subscriber);
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		this.timer();
		pool.shutdown();
		try {
		  pool.awaitTermination(30, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
		  System.out.println("Failed to shutdown properly");
		}
		
	}

	@Override
	public void publish(Object obj) {
		// TODO Auto-generated method stub
		Runnable r1 = new AsyncUnorderedRun(subscribersList, obj); 
		pool.execute(r1);
		
	}

}
