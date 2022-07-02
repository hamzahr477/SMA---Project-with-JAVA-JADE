package com.agents;

import java.util.Arrays;
import java.util.List;

import com.behaviours.HuntingBehaviour;
import com.behaviours.RunningBehaviour;
import com.env.Environment;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class HunterAgent extends AgentAvatar {

	public static boolean win = false;
	
	private  List<Integer> posAvatar;
	
	@Override
	protected void setup() {
		List<Integer> pos ;
		while(getPlayerPos()==null) {
			continue;
		}
		do {
			pos = Environment.findRandomEmptyCell();
		}
		while(Math.abs(pos.get(0)-getPlayerPos().get(0))+Math.abs(pos.get(1)-getPlayerPos().get(1))<Environment.distmin);

		super.x=pos.get(0);
		super.y=pos.get(1);
		Environment.getMat_env()[x][y]=-1;
		
		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				// TODO Auto-generated method stub
				ACLMessage   msg = receive();
				if(msg!=null) takeDown();
				if(!win && Environment.gamestatus && !Environment.gameend) {
					if(getPlayerState() == 0)
						addBehaviour(new HuntingBehaviour(getAgentAvatar()));
					else if(getPlayerState() == 1)
						addBehaviour(new RunningBehaviour(getAgentAvatar()));
				}
				
			}
		});
		
	}
	
	
	
}
