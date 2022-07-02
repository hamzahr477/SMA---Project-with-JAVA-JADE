package com.behaviours;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Arrays;
import java.util.List;

import com.agents.AgentAvatar;
import com.agents.AgentPlayer;
import com.env.*;
public class PlayerBehaviour extends OneShotBehaviour {

	AgentAvatar player;
	List<Integer> movedir;
	static long startTime ;
	public PlayerBehaviour(AgentAvatar player, List<Integer> movedir ) {
		this.player=player;
		this.movedir=movedir;
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		List<Integer> move = Environment.getRealMove(player.getX(),player.getY(),movedir);
		if(System.nanoTime()-startTime>=Environment.timeout*500000000) {
			player.setId(2);
		}
		if(Environment.getMat_env()[player.getX()+move.get(0)][player.getY()+move.get(1)]!=1 && Environment.getMat_env()[player.getX()+move.get(0)][player.getY()+move.get(1)]!=-1) {
			Environment.getMat_env()[player.getX()][player.getY()]=0;
			if(Environment.getMat_env()[player.getX()+move.get(0)][player.getY()+move.get(1)]==3) {
				//traitemnt
				player.setId(-2);
				startTime=System.nanoTime();
			}
			else if(Environment.getMat_env()[player.getX()+move.get(0)][player.getY()+move.get(1)]==4) {
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.addReceiver(new AID("Agent_Env_Manager", AID.ISLOCALNAME));
				msg.setContent("I ate banana");
				player.send(msg);
			}
    	 	player.setY(player.getY()+move.get(1));
    	 	player.setX(player.getX()+move.get(0));
          
    	}
		Environment.getMat_env()[player.getX()][player.getY()]=player.getId();

	}
	

}


