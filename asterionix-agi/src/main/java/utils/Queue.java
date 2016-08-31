package utils;
import java.util.ArrayList;
import java.util.List;

public class Queue {

		private String queueName;
		
		private List<Agent> agents;
		
		public Queue(String queueName){
			
			this.queueName = queueName;
			
			this.agents = new ArrayList<Agent>();
		}
		
		public String getQueueName(){
			
			return this.queueName;
		}
		
		public void addQueueMember(Agent agent){
			
			agents.add(agent);
			
		}
		public List<Agent> getAgents(){
			
			return this.agents;
		}
		public void setAgent(List<Agent> agent){
			
			this.agents = agent;
			
		}
		
}
