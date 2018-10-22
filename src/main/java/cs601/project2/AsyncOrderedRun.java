package cs601.project2;

import java.util.ArrayList;


public class AsyncOrderedRun extends Thread {
	ArrayList<Subscriber<Object>> subscribersList;
	BlockingQueueImpl<Object> queue;
	

	public AsyncOrderedRun(BlockingQueueImpl<Object> queue, ArrayList<Subscriber<Object>> subscribersList) {
		// TODO Auto-generated constructor stub
		this.subscribersList = subscribersList;
		this.queue = queue;
		
	}

	public void run() {
		// for each subcriber in subcribersList call the onEvent function with jobj as
		// the argument.
		Object obj;
		while (true) {
			obj = queue.poll(3000);
			if(obj != null) {
				for (Subscriber<Object> sub : subscribersList) {
					sub.onEvent(obj);
				}
			} else {
				break;
			}
		}
	}
}