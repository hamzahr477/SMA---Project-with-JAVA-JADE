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

public class AgentFish extends Agent {

	private int x,y;
	private int currentBreedTime=0;
	private int breedTime=2;
	private boolean dead;
	@Override
	protected void setup() {
		Object[] args = getArguments();
		this.setX(Integer.parseInt((String) args[0]));
		this.setY(Integer.parseInt((String) args[1]));
		Water.water[x][y]=Integer.parseInt(this.getAID().getLocalName().split(":")[1]);
		// TODO Auto-generated method stub
		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				ACLMessage msg = receive();
				if(msg!= null) {
					takeDown();
				}
				try {
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				List<Integer> newPos = getNewPos();
				System.out.println(Water.getumbOfFish());
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
				//System.out.println(x+" : "+y);
				//affichage(Water.water);
			}
		});
	}

	protected void takeDown() {
	    // Printout a dismissal message
	    System.out.println("Agent-1 "+getAID().getName()+" terminating.");
	}
	private List<Integer> getNewPos(){
		Random r = new Random();
		List<List<Integer>> free = new ArrayList(new ArrayList());
		for (int i=-1 ;i<2 ; i++) 
			for(int j=-1 ; j<2;j++)
				if( x+i<Water.water.length && x+i>=0 && y+j>=0 && y+j<Water.water.length) {
					if(Water.water[x+i][y+j] == 0)
						free.add(Arrays.asList(x+i,y+j));
				}
		if(free.size() == 0) {
			return Arrays.asList(-2,-2);
		}
		List<Integer> newPos =free.get(r.nextInt(free.size()));
		return newPos;
	}
	private void move(List<Integer> newPos) throws StaleProxyException {
		//System.out.println("My current pos is "+newPos.get(0)+" and "+newPos.get(1));
		setX(newPos.get(0));
		setY(newPos.get(1));
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContent(newPos.get(0)+":"+newPos.get(1));
		msg.addReceiver(new AID("AgentGUI",AID.ISLOCALNAME));
		send(msg);
		currentBreedTime+=1;
		if(currentBreedTime==breedTime) {
			currentBreedTime=0;
			Water.updateWithBreed(Arrays.asList(x,y) , newPos ,Integer.parseInt(this.getAID().getLocalName().split(":")[1]));
			breed();
		}
		else
			Water.update(newPos,Arrays.asList(x,y),Integer.parseInt(this.getAID().getLocalName().split(":")[1]));

	}
	private void breed() throws StaleProxyException {
		AgentController ac2;
		Object [] pos = new Object[2];
		pos[0]=""+x;
		pos[1]=""+y;
		try {
			Water.count_fish+=1;
			ac2= this.getContainerController().createNewAgent("AgentFish:"+(Water.getumbOfFish()+1), "Agent.AgentFish", pos);
			ac2.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private void affichage(boolean[][] water) {
		System.out.println("\n\n-------------new------------\n\n");
		for (int i=0;i<water.length;i++) {
			System.out.println("-------------------------------------------------------------");
			for(int j=0 ; j<water[i].length;j++) {
				if(!water[i][j]) {
					System.out.print("|  "+"X"+"  ");
				}
				else {
					System.out.print("|     ");
	
				}
			}
			System.out.print("|\n");
			
		}
		System.out.println("-------------------------------------------------------------");
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

	public int getBreedTime() {
		return breedTime;
	}

	public void setBreedTime(int breedTime) {
		this.breedTime = breedTime;
	}

	public int getCurrentBreedTime() {
		return currentBreedTime;
	}

	public void setCurrentBreedTime(int currentBreedTime) {
		this.currentBreedTime = currentBreedTime;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
}
