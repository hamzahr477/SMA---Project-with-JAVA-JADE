package Agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import model.Water;

public class Shark extends Agent {

	private int breedTime = 10;
	private int straveTime = 3;
	private int currentstraveTime = 0;
	private int currentbreedTime = 0;
	private int x, y;
	
	@Override
	protected void setup() {
		System.out.println("I m first shark");
		Object[] args = getArguments();
		this.setX(Integer.parseInt((String) args[0]));
		this.setY(Integer.parseInt((String) args[1]));
		Water.water[x][y] = -1;
		// TODO Auto-generated method stub
		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				if(straveTime <= currentstraveTime )
					takeDown();
				currentstraveTime+=1;
				try {
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				List<Integer> newPos = getNewPos();
				System.out.println(Water.getumbOfShark());
				if(newPos.get(0) != -2 ) {
//					ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//					message.setContent(newPos.get(0)+":"+newPos.get(0));
//					message.addReceiver(new AID("AgentWATER",AID.ISLOCALNAME));
					try {
						move(newPos);
					} catch (StaleProxyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	protected void takeDown() {
	    // Printout a dismissal message
		Water.water[x][y]=0;
	    System.out.println("Agent-1 "+getAID().getName()+" terminating.");
	}

	private List<Integer> getNewPos(){
		Random r = new Random();
		List<List<Integer>> free = new ArrayList(new ArrayList());
		List<List<Integer>> fish = new ArrayList(new ArrayList());

		for (int i=-1 ;i<2 ; i++) 
			for(int j=-1 ; j<2;j++)
				if( x+i<Water.water.length && x+i>=0 && y+j>=0 && y+j<Water.water.length) {
					if(Water.water[x+i][y+j] == 0)
						free.add(Arrays.asList(x+i,y+j));
					else if(Water.water[x+i][y+j] > 0) fish.add(Arrays.asList(x+i,y+j));
				}
		if(fish.size()!=0) {
			List<Integer> newPos =fish.get(r.nextInt(fish.size()));
			eat(newPos);
			return newPos;
		}
		
		if(free.size() != 0) {
			List<Integer> newPos =free.get(r.nextInt(free.size()));
			return newPos;
		}
		return Arrays.asList(-2,-2);
	}
	private void eat(List<Integer> nPos) {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContent("eat");
		msg.addReceiver(new AID("AgentFish:"+Water.water[nPos.get(0)][nPos.get(1)],AID.ISLOCALNAME));
		send(msg);
		currentstraveTime = 0;
	}
	private void move(List<Integer> newPos) throws StaleProxyException {
		//System.out.println("My current pos is "+newPos.get(0)+" and "+newPos.get(1));
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContent(newPos.get(0)+":"+newPos.get(1));
		msg.addReceiver(new AID("AgentGUI",AID.ISLOCALNAME));
		send(msg);
		
		if(currentbreedTime==breedTime) {
			currentbreedTime=0;
			Water.updateWithBreed_Shark(newPos);
			breed();
		}
		else
			Water.update_Shark(newPos,Arrays.asList(x,y));
		setX(newPos.get(0));
		setY(newPos.get(1));
		currentbreedTime+=1;


	}
	private void breed() throws StaleProxyException {
		AgentController ac2;
		Object [] pos = new Object[2];
		pos[0]=""+x;
		pos[1]=""+y;
		try {
			ac2= this.getContainerController().createNewAgent("AgentShark"+(Water.getumbOfShark()+1), "Agent.Shark", pos);
			ac2.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public int getBreedTime() {
		return breedTime;
	}
	public void setBreedTime(int breedTime) {
		this.breedTime = breedTime;
	}
	public int getStraveTime() {
		return straveTime;
	}
	public void setStraveTime(int straveTime) {
		this.straveTime = straveTime;
	}
	public int getCurrentstraveTime() {
		return currentstraveTime;
	}
	public void setCurrentstraveTime(int currentstraveTime) {
		this.currentstraveTime = currentstraveTime;
	}
	public int getCurrentbreedTime() {
		return currentbreedTime;
	}
	public void setCurrentbreedTime(int currentbreedTime) {
		this.currentbreedTime = currentbreedTime;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
