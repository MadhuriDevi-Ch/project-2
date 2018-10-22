package cs601.project2;

public class BlockingQueueImpl<T>  {


	    private T[] items;  
	    private int start;
	    private int end;
	    private int size;
	    private int capacity;

	    public BlockingQueueImpl(int capacity) {
	        this.items = (T[]) new Object[capacity];
	        this.capacity = capacity;
	        this.start = 0;
	        this.end = -1;
	        this.size = 0;
	    }

	    public synchronized void put(T item) {
	    	while(size == capacity) {
	    		try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	        int next = (end + 1) % capacity;
	        items[next] = item;
	        end = next;
	        if(size < capacity) {
	        	size++;
	        }
	    }

	    public synchronized T take() {
	    	if(size > 0) {
		        T item = items[start];
		        start = (start + 1) % capacity;
		        size--;
		        notifyAll();
		        return item;
	    	} else
	    		return null;
	    }

	    public synchronized boolean isEmpty() {
	        return size == 0;
	    }
	    
	    // duration is in milliseconds
	    public synchronized T poll(int duration) {
	    	// wait for time duration if items is empty
	    	if(size == 0) {
	    		try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	return take();
	    }

}
