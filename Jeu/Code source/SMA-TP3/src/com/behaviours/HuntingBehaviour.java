package com.behaviours;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.agents.AgentAvatar;
import com.agents.HunterAgent;
import com.env.Environment;
import com.shortpath.Djikstra;

import jade.core.AID;
import jade.core.Agent;
import jade.core.Timer;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class HuntingBehaviour extends OneShotBehaviour {

	
	
	AgentAvatar hunter;
	
	public HuntingBehaviour(AgentAvatar agentAvatar) {
		this.hunter=agentAvatar;
	}
	
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		try {
			TimeUnit.MILLISECONDS.sleep(Environment.level);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
		}
		List<Integer> nextMove = null;
		List<Integer> playerpos = hunter.getPlayerPos();
		Djikstra d = new Djikstra();
		List<List<Integer>> neighborsCoordinates =d.neighbors(hunter.getX(),hunter.getY());
		
		int [][] dijkstra= d.computeDijkstra(playerpos);
		
		int nextMoveNumber = Integer.MAX_VALUE;
		for(List<Integer> c : neighborsCoordinates) {
			if(dijkstra[c.get(0)][c.get(1)] != 0 && dijkstra[c.get(0)][c.get(1)]<= nextMoveNumber) {
				nextMoveNumber = dijkstra[c.get(0)][c.get(1)];
				nextMove = c;
			}
		}
		
		if(nextMove != null) {
			List<List<Integer>> coordinates = d.neighbors(hunter.getX(),hunter.getY());
			for(List<Integer> coordinate : coordinates) {
	            if(coordinate.get(0)==playerpos.get(0) & coordinate.get(1) == playerpos.get(1)) {
	            	nextMove.set(0,playerpos.get(0)); nextMove.set(1, playerpos.get(1));
	            }
	        }
			
			clear();
			hunter.setX(nextMove.get(0));
			hunter.setY(nextMove.get(1));
			Environment.getMat_env()[hunter.getX()][hunter.getY()]=-1;
		}
		
		if(playerpos!=null) {
			if(playerpos.equals(nextMove)) {
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(new AID("Agent_Env_Manager", AID.ISLOCALNAME));
			msg.setContent("I ate player");
			hunter.send(msg);
		}
		}
		
		
		
	}

	void clear(){
		for(int i=0;i<Environment.getLength();i++ ) 
			for(int j=0;j<Environment.getWidth();j++ ) 
				if (Environment.getMat_env()[i][j] == -1) 
					Environment.getMat_env()[i][j]=0;
	}
	
	
}
