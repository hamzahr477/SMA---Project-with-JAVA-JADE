package com.agents;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.env.Environment;
import com.starter.Starter;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.introspection.AddedBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.StaleProxyException;

public class AgentEnvManager extends Agent {

	long startTimeFruit;
	int winfruitCount=-1; 
	boolean reach4 = false;
	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		startTimeFruit = System.nanoTime();

		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				// TODO Auto-generated method stub
				ACLMessage msgfromhunter = receive(MessageTemplate.MatchSender(new AID("Agent_Hunter", AID.ISLOCALNAME)));
				ACLMessage msgfromplayer = receive(MessageTemplate.MatchSender(new AID("Agent_Player", AID.ISLOCALNAME)));
				if(msgfromhunter!=null) {
					System.out.println("You DIED !!");
					endGame("You DIED !!");
				}

				if(msgfromplayer!=null) {
					System.out.println("You WIN !!");
					endGame("You WIN !!");
				}
				if(winfruitCount==Environment.scoreforprice && !reach4) {
					List<Integer> fruit_pos = Environment.findRandomEmptyCell();
					Environment.getMat_env()[fruit_pos.get(0)][fruit_pos.get(1)]=4;
					reach4=true;
				}
				if(fruitAte() || timePassed()) {
					clear();					
					List<Integer> fruit_pos = Environment.findRandomEmptyCell();
					Environment.getMat_env()[fruit_pos.get(0)][fruit_pos.get(1)]=3;
					startTimeFruit = System.nanoTime();
				}
				
			}

		});
	}
	protected void takeDown() {
	    // Printout a dismissal message
	    System.out.println("Agent - "+getAID().getName()+" terminating.");
		System.exit(0);
	}
	void endGame(String message) {
		Environment.gameend=true;
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContent("GameOver");
		msg.addReceiver(new AID("Agent_GUI", AID.ISLOCALNAME));
		msg.addReceiver(new AID("Agent_Player", AID.ISLOCALNAME));
		msg.addReceiver(new AID("Agent_Hunter", AID.ISLOCALNAME));
		send(msg);
	    Object[] options = {"OK"};
	    JOptionPane pane = new JOptionPane(message,JOptionPane.CLOSED_OPTION, JOptionPane.OK_OPTION,null,
                options,
                options[0]);
	    JDialog d = pane.createDialog(null, "END");
	    d.pack();
	    d.setModal(false);
	    d.setVisible(true);	    
	    while (pane.getValue() == JOptionPane.UNINITIALIZED_VALUE) {
	      try {
	        Thread.sleep(1);
	      } catch (InterruptedException ie) {
	      }
	    }
	    System.exit(0);

	}

	boolean timePassed() {
		if(System.nanoTime()-startTimeFruit>=Environment.timeout*1000000000)
			return true;
		return false;
		
	}
	boolean fruitAte() {
		for(int i=0;i<Environment.getLength();i++ ) 
			for(int j=0;j<Environment.getWidth();j++ ) 
				if (Environment.getMat_env()[i][j] == 3) 
					return false;
		winfruitCount+=1;
		return true;
	}
	void clear() {
		for(int i=0;i<Environment.getLength();i++ ) 
			for(int j=0;j<Environment.getWidth();j++ ) 
				if (Environment.getMat_env()[i][j] == 3) 
					Environment.getMat_env()[i][j]=0;
	}
}
