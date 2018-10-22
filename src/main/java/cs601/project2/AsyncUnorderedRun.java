package cs601.project2;

import java.util.ArrayList;

public class AsyncUnorderedRun implements Runnable {
	private ArrayList<Subscriber<Object>> subscribersList;
	private Object obj;
	public AsyncUnorderedRun(ArrayList<Subscriber<Object>> subscribersList, Object obj) {
		// TODO Auto-generated constructor stub
		this.subscribersList = subscribersList;
		this.obj = obj;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		if (subscribersList.isEmpty()) {
			System.out.println("No subscribers");
			return;
		} else {
			// for each subcriber in subcribersList call the onEvent function with obj as the argument.
			for(Subscriber<Object> sub : subscribersList ) {
				sub.onEvent(obj);
			}
		}
		
	}

}
