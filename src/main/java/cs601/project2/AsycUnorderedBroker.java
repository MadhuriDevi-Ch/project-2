package cs601.project2;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsycUnorderedBroker implements Broker<Object> {

	private ExecutorService pool;
	private ArrayList<Subscriber<Object>> subscribersList;
	
	public AsycUnorderedBroker() {
		// TODO Auto-generated constructor stub
		pool = Executors.newFixedThreadPool(100); 
		this.subscribersList = new ArrayList<Subscriber<Object>>();
	}

	@Override
	public void timer() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void subscribe(Subscriber<Object> subscriber) {
		// TODO Auto-generated method stub
		this.subscribersList.add(subscriber);
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
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
