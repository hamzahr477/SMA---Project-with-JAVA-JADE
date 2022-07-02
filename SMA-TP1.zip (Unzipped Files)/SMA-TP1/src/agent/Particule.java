	package agent;
	
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.List;
	import java.util.Random;
	import java.util.concurrent.TimeUnit;
	
	import jade.core.AID;
	import jade.core.Agent;
	import jade.core.behaviours.CyclicBehaviour;
	import jade.lang.acl.ACLMessage;
	import jade.lang.acl.MessageTemplate;
	
	public class Particule extends Agent {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int x,y;
		private int move_deriction_x;
		private int move_deriction_y;
	
	
		@Override
		protected void setup() {
			// TODO Auto-generated method stub
			Object[] args = getArguments();
			this.setX(Integer.parseInt((String) args[0]));
			this.setY(Integer.parseInt((String) args[1]));
			List<Integer> direction = getRandomMoveDirection();
			move_deriction_x=direction.get(0);
			move_deriction_y=direction.get(1);
			addBehaviour(new CyclicBehaviour() {
				@Override
				public void action() {
					
					// TODO Auto-generated method stub
					//receive request message of board manager
					ACLMessage m1 = blockingReceive(MessageTemplate.MatchSender(new AID("AgentBoard",AID.ISLOCALNAME)));
					//.....................
					
					
					//Send real move to BoardManager
					List<Integer> realmv = getRealMove(getX(), getY(), Arrays.asList((move_deriction_x),(move_deriction_y)));
					ACLMessage msg = m1.createReply();
					msg.setContent(getX()+":"+getY()+":"+(realmv.get(0))+":"+(realmv.get(1)));
					//
					send(msg);
					//..................
					
					//Waiting for response of Board manager 
					ACLMessage m2 = blockingReceive(MessageTemplate.MatchSender(new AID("AgentBoard",AID.ISLOCALNAME)));
					
					
					//Update new values
					List<Integer> new_position = Arrays.asList(Integer.parseInt(m2.getContent().split(":")[0]),Integer.parseInt(m2.getContent().split(":")[1]));
					List<Integer> new_direction = Arrays.asList(Integer.parseInt(m2.getContent().split(":")[2]),Integer.parseInt(m2.getContent().split(":")[3]));
					move_deriction_x=getMoveFromRM(new_direction).get(0);
					move_deriction_y=getMoveFromRM(new_direction).get(1);
					
					//Move to new position
					move(new_position);
					
				}
			});
			
	
		}
		private List<Integer> getRandomMoveDirection() {
			Random r = new Random();
			List<List<Integer>> mvList = Arrays.asList(Arrays.asList(0,1),
					Arrays.asList(1,0),
					Arrays.asList(-1,0),
					Arrays.asList(0,-1),
					Arrays.asList(1,1),
					Arrays.asList(-1,1),
					Arrays.asList(1,-1),
					Arrays.asList(-1,-1));
			List<Integer> mv_direction = mvList.get(r.nextInt(mvList.size()));
			return mv_direction;
		}
		
		
		public static List<Integer> getMoveFromRM(List<Integer> rm){
			int mvd_y = rm.get(1);
			int mvd_x=rm.get(0);
			if(mvd_x==9)
				mvd_x=-1;
			else if(mvd_x==-9)
				mvd_x=1;
			if(mvd_y==9)
				mvd_y=-1;
			else if(mvd_y==-9)
				mvd_y=1;
			List<Integer> mvdirection=Arrays.asList(mvd_x,mvd_y);
			return mvdirection;
		}
		
		public static List<Integer> getRealMove(int x,int y,List<Integer> moveold){
			List<Integer> move=new ArrayList();
			move.add(moveold.get(0));
			move.add(moveold.get(1));
	
			if(x + move.get(0)== 10){
				move.set(0, -9);
			}
			else if(x + move.get(0)== -1){
				move.set(0, 9);
			}
			if(y + move.get(1)== 10){
				move.set(1, -9);
			}
			else if(y + move.get(1)== -1){
				move.set(1, 9);
			}
			return move;
		}
		
	    public void move(List<Integer> np) {
			//List<Integer> realMoeDirection=getRealMove(getX(),getY(),Arrays.asList(move_deriction_x,move_deriction_y));
			setX(np.get(0));
			setY(np.get(1));
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getMove_deriction_y() {
			return move_deriction_y;
		}
		public void setMove_deriction_y(int move_deriction_y) {
			this.move_deriction_y = move_deriction_y;
		}
		public int getMove_deriction_x() {
			return move_deriction_x;
		}
		public void setMove_deriction_x(int move_deriction_x) {
			this.move_deriction_x = move_deriction_x;
		}
	
	}
