package com.agents;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.behaviours.PlayerBehaviour;
import com.env.Environment;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.*;
import java.awt.*;


import java.awt.event.*;
public class AgentPlayer extends AgentAvatar{
	
	private int score;
	private List<Integer> movedir;	
	
	@Override
	protected void setup() {
		List<Integer> pos = Environment.findRandomEmptyCell();
		super.x=pos.get(0);
		super.y=pos.get(1);
		Environment.getMat_env()[x][y]=2;
		System.out.println("test");

		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
		    @Override
		    public void eventDispatched(final AWTEvent event) {
		        if (event.getID() == KeyEvent.KEY_PRESSED)
		        {
		            final KeyEvent evt = (KeyEvent) event;
		            switch(evt.getKeyCode()){
		            case KeyEvent.VK_UP:
			            movedir= Arrays.asList(0,-1);
			            break;
			        case KeyEvent.VK_DOWN:
			        	movedir= Arrays.asList(0,1);
			            break;
			        case KeyEvent.VK_LEFT:
			        	movedir= Arrays.asList(-1,0);
			            break;
			        case KeyEvent.VK_RIGHT :
			        	movedir=Arrays.asList(1,0);
			            break;
			        case KeyEvent.VK_SPACE :
			        	Environment.gamestatus=!Environment.gamestatus;
			        	
			        	System.out.println(Environment.gamestatus ? "PLAY" : "PAUSE");
			            break;
		            }
		        }   
		    }
		    }, AWTEvent.KEY_EVENT_MASK);
		addBehaviour(new CyclicBehaviour() {
			
			
			@Override
			public void action() {
				// TODO Auto-generated method stub
				ACLMessage   msg = receive();
				if(msg!=null) takeDown();
				try {
					TimeUnit.MILLISECONDS.sleep(70);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
				
				
				if(movedir!=null && Environment.gamestatus && !Environment.gameend) {
					addBehaviour(new PlayerBehaviour(getAgentAvatar() , movedir));
				}
			}
		});
		

		
	}
	
	
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}


	
	
	

}
