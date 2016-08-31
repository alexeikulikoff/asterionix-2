package utils;
import java.util.ArrayList;
import java.util.List;

public class Queues {

private List<Queue> queues;
	
	public Queues(){
		
		queues = new ArrayList<Queue>();
		
	}
	public  List<Queue> getQueues(){
		
		return this.queues;
		
	}
	public void addQueue(Queue queue){
		
		queues.add(queue);
	}
}
