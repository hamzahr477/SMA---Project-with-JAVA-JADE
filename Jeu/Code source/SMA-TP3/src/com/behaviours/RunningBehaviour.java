package com.behaviours;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.agents.AgentAvatar;
import com.agents.HunterAgent;
import com.env.Environment;
import com.shortpath.Djikstra;

import jade.core.behaviours.OneShotBehaviour;

public class RunningBehaviour extends OneShotBehaviour{


	AgentAvatar hunter;
	
	public RunningBehaviour(AgentAvatar agentAvatar) {
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
		
		int nextMoveNumber = Integer.MIN_VALUE;
		for(List<Integer> c : neighborsCoordinates) {
			if(dijkstra[c.get(0)][c.get(1)] != 0 && dijkstra[c.get(0)][c.get(1)]>=nextMoveNumber) {
				nextMoveNumber = dijkstra[c.get(0)][c.get(1)];
				nextMove = c;
			}
		}
		
		if(nextMove != null) {
			
			
			clear();
			hunter.setX(nextMove.get(0));
			hunter.setY(nextMove.get(1));
			Environment.getMat_env()[hunter.getX()][hunter.getY()]=-1;
		}
		
	}
	void clear(){
		for(int i=0;i<Environment.getLength();i++ ) 
			for(int j=0;j<Environment.getWidth();j++ ) 
				if (Environment.getMat_env()[i][j] == -1) 
					Environment.getMat_env()[i][j]=0;
	}

}
