package com.agents;

import javax.swing.JFrame;
import javax.swing.SwingConstants;

import com.env.Environment;
import com.gui.Gui;

import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AgentGUI extends Agent {
	
	private Gui gui;
	private JFrame frame;
	
	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		super.setup();
		gui = new Gui();
		frame = new JFrame("SMA_TP3 - PACMAN");
		frame.getContentPane().add(gui);
		frame.setVisible(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(537, 560);
		frame.setLocationRelativeTo(null);
		
		addBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				// TODO Auto-generated method stub
				ACLMessage   msg = receive();
				if(msg!=null) takeDown();
			}
		});
	}
	

}
