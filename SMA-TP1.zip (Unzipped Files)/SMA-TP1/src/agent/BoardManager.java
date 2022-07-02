package agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BoardManager extends Agent {

	//board size 
	public static int xlength = 10; 
  	public static int ylength = 10;
  	int[][] board = new int[xlength][ylength];
  	
  	String agent_name_prefix="AgentParitcule";
  	
  	
	List<List<Integer>> old_position  = new ArrayList<List<Integer>>();
	List<List<Integer>> new_position  = new ArrayList<List<Integer>>();
	List<List<Integer>> move_direction = new ArrayList<List<Integer>>() ;
  
	@Override
	protected void setup() {
		Object[][] pos = (Object[][]) getArguments();
		for (int[] row: board)
		    Arrays.fill(row,0);
		
		//init variabls
		for(int i=0;i<pos.length;i++) {
		board[Integer.parseInt((String) pos[i][0])][Integer.parseInt((String) pos[i][1])]=i+1;
		old_position.add(Arrays.asList(0,0));
		new_position.add(Arrays.asList(0,0));
		move_direction.add(Arrays.asList(0,0));
		}
		// TODO Auto-generated method stub
		addBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				// TODO Auto-generated method stub
				
				try {
					TimeUnit.MILLISECONDS.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				ACLMessage msgsend;
				
				//Collect position of all particles...
				for(int i=0;i<pos.length;i++) {
					msgsend= new ACLMessage(ACLMessage.INFORM)  ;
					msgsend.setContent("send me your new position");
					msgsend.addReceiver(new AID(agent_name_prefix+(i+1),AID.ISLOCALNAME));
					send(msgsend);
					ACLMessage msg2=blockingReceive(MessageTemplate.MatchSender(new AID(agent_name_prefix+(i+1),AID.ISLOCALNAME)));
					old_position.set(i, Arrays.asList(Integer.parseInt(msg2.getContent().split(":")[0]),Integer.parseInt(msg2.getContent().split(":")[1])));
					move_direction.set(i,Arrays.asList(Integer.parseInt(msg2.getContent().split(":")[2]),Integer.parseInt(msg2.getContent().split(":")[3])));
					new_position.set(i, Arrays.asList(old_position.get(i).get(0)+move_direction.get(i).get(0),old_position.get(i).get(1)+move_direction.get(i).get(1)));
				}
				//........
				
				//Crash processing
					boolean crashCheck = crashVerify(new_position,old_position);
					List<Integer> changedList = new ArrayList() ;
					
					while(crashCheck) {
						List<List<Integer>> crashlist=getCrashList(new_position,old_position);
						for(int i =0;i<crashlist.size();i++) {
							if(crashlist.get(i).size()>1) {
								List<Integer> temp = move_direction.get(crashlist.get(i).get(0));
								for(int j=0;j<crashlist.get(i).size();j++) {
									
									if(j==0 && changedList.size()==0 || changedList.contains(crashlist.get(i).get(j)) && j!=crashlist.get(i).size()-1)
										move_direction.set(crashlist.get(i).get(j),Particule.getRealMove(old_position.get(crashlist.get(i).get(j)).get(0),old_position.get(crashlist.get(i).get(j)).get(1), Particule.getMoveFromRM(move_direction.get(crashlist.get(i).get(j+1)))));
									
									else if(changedList.contains(crashlist.get(i).get(j)) && j==crashlist.get(i).size()-1)
										move_direction.set(crashlist.get(i).get(j),Particule.getRealMove(old_position.get(crashlist.get(i).get(j)).get(0),old_position.get(crashlist.get(i).get(j)).get(1), Particule.getMoveFromRM((temp))));
									
									else if(j==crashlist.get(i).size()-1 && !changedList.contains(crashlist.get(i).get(j))) {
										move_direction.set(crashlist.get(i).get(j),Particule.getRealMove(old_position.get(crashlist.get(i).get(j)).get(0),old_position.get(crashlist.get(i).get(j)).get(1), Particule.getMoveFromRM(temp)));
										int newpos_x=old_position.get(crashlist.get(i).get(j)).get(0)+Particule.getRealMove(old_position.get(crashlist.get(i).get(j)).get(0),old_position.get(crashlist.get(i).get(j)).get(1), Particule.getMoveFromRM(temp)).get(0);
										int newpos_y=old_position.get(crashlist.get(i).get(j)).get(1)+Particule.getRealMove(old_position.get(crashlist.get(i).get(j)).get(0),old_position.get(crashlist.get(i).get(j)).get(1), Particule.getMoveFromRM(temp)).get(1);
										new_position.set(crashlist.get(i).get(j),Arrays.asList(newpos_x,newpos_y));
									}
									
									else {
										move_direction.set(crashlist.get(i).get(j),Particule.getRealMove(old_position.get(crashlist.get(i).get(j)).get(0),old_position.get(crashlist.get(i).get(j)).get(1), Particule.getMoveFromRM(move_direction.get(crashlist.get(i).get(j+1)))));
										int newpos_x=old_position.get(crashlist.get(i).get(j)).get(0)+Particule.getRealMove(old_position.get(crashlist.get(i).get(j)).get(0),old_position.get(crashlist.get(i).get(j)).get(1), Particule.getMoveFromRM(move_direction.get(crashlist.get(i).get(j+1)))).get(0);
										int newpos_y=old_position.get(crashlist.get(i).get(j)).get(1)+Particule.getRealMove(old_position.get(crashlist.get(i).get(j)).get(0),old_position.get(crashlist.get(i).get(j)).get(1), Particule.getMoveFromRM(move_direction.get(crashlist.get(i).get(j+1)))).get(1);
										new_position.set(crashlist.get(i).get(j),Arrays.asList(newpos_x,newpos_y));
									}
									changedList.add(crashlist.get(i).get(j));
								}
							}
						}
						crashCheck = crashVerify(new_position,old_position);
					}
					//..........
					
					String msgGUI ="";
					
					//Send validation of movement to all particles
					for(int i=0;i<pos.length;i++) {
						msgGUI+=""+new_position.get(i).get(0)+":"+new_position.get(i).get(1);
						if(i!=pos.length-1)
							msgGUI+="-";
						ACLMessage response = new ACLMessage(ACLMessage.INFORM);
						response.addReceiver(new AID(agent_name_prefix+(i+1),AID.ISLOCALNAME));
						response.setContent(""+new_position.get(i).get(0)+":"+new_position.get(i).get(1)+":"+move_direction.get(i).get(0)+":"+move_direction.get(i).get(1));
						send(response);
					}
					//.................
					
					//send new position to GUI 
					ACLMessage response = new ACLMessage(ACLMessage.INFORM);
					response.addReceiver(new AID("AgentGUI",AID.ISLOCALNAME));
					response.setContent(msgGUI);
					send(response);
					//...............
					
					
					applyyMv(new_position);				
			}
		}); 
	}
	
	
	private boolean crashVerify(List<List<Integer>> newposition,List<List<Integer>> oldposition) {
		for(int i=0;i<newposition.size();i++)
			for(int j=i+1 ; j<newposition.size();j++)
				if((newposition.get(i).get(0)==newposition.get(j).get(0) && newposition.get(i).get(1)==newposition.get(j).get(1) ) ||( oldposition.get(i).get(0)==newposition.get(j).get(0) &&  oldposition.get(i).get(1)==newposition.get(j).get(1) && oldposition.get(j).get(0)==newposition.get(i).get(0) &&  oldposition.get(j).get(1)==newposition.get(i).get(1) ) )
					return true;
				
		return false;
}
	private List<List<Integer>> getCrashList(List<List<Integer>> newposition,List<List<Integer>> oldposition){
		List<List<Integer>> crashedList = new ArrayList();
		for(int i=0;i<newposition.size();i++) {
			List<Integer> souscrashedList = new ArrayList();
			souscrashedList.add(i);
			for(int j=i+1;j<newposition.size();j++) {
				if((newposition.get(i).get(0)==newposition.get(j).get(0) && newposition.get(i).get(1)==newposition.get(j).get(1) ) || ( oldposition.get(i).get(0)==newposition.get(j).get(0) &&  oldposition.get(i).get(1)==newposition.get(j).get(1) && oldposition.get(j).get(0)==newposition.get(i).get(0) &&  oldposition.get(j).get(1)==newposition.get(i).get(1) ) ) {
					souscrashedList.add(j);
				}
			}
			crashedList.add(souscrashedList);
		}
		return crashedList;
	}
	private void applyyMv(List<List<Integer>> newp) {
		for (int[] row: board)
		    Arrays.fill(row,0);
		for(int i=0;i<newp.size();i++) {
			board[newp.get(i).get(0)][newp.get(i).get(1)]=i+1;
		}
	}
	private void showNewp(List<List<Integer>> newp) {
		for(int i=0;i<newp.size();i++) {
			System.out.println(i+" "+newp.get(i).get(0)+" : "+newp.get(i).get(1));
		}
	}
	
}
