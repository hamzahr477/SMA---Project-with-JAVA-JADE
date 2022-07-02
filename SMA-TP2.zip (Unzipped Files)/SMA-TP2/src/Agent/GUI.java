package Agent;

import java.awt.Color;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import model.Grid;

public class GUI extends Agent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	public static Grid grid;
	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		frame = new JFrame("SMA");
        grid = new Grid();
		frame.getContentPane().add(grid);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0,520, 550);
		addBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				ACLMessage message = receive();
				// TODO Auto-generated method stub
				grid.update();
			}
		});
		
	}
	
	

}
