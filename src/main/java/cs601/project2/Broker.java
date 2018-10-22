package cs601.project2;

public interface Broker<T> {
	
	

	void timer();
	void publish(T obj);
	void subscribe(Subscriber<T> subscriber);
	void shutdown();

}
