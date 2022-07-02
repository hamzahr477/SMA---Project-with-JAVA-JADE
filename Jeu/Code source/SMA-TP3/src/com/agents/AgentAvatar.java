package com.agents;

import java.util.Arrays;
import java.util.List;

import com.env.Environment;

import jade.core.Agent;

public class AgentAvatar extends Agent{
	
	protected int x;
	protected int y;
	private int id;
	
	
	protected void takeDown() {
	    // Printout a dismissal message
	    System.out.println("Agent - "+getAID().getName()+" terminating.");
	}

	int getPlayerState() {
		int state=0;
		for(int i=0;i<Environment.getLength();i++ ) 
			for(int j=0;j<Environment.getWidth();j++ ) 
				if (Environment.getMat_env()[i][j] == -2) 
					state=1;
		return state;
	}
	
	public static List<Integer> getPlayerPos() {
		for(int i=0;i<Environment.getLength();i++ ) 
			for(int j=0;j<Environment.getWidth();j++ ) 
				if (Environment.getMat_env()[i][j] == 2 || Environment.getMat_env()[i][j] == -2) 
					return Arrays.asList(i,j);
		return null;
	}
	AgentAvatar getAgentAvatar() {
		return this;
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
