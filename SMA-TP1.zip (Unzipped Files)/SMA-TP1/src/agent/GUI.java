package agent;

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
		Object[][] pos =(Object[][]) getArguments();
		frame = new JFrame("SMA");
        grid = new Grid(pos);
		frame.getContentPane().add(grid);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0,520, 550);

		addBehaviour(new CyclicBehaviour() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				List<List<Integer>> current_pos = new ArrayList<List<Integer>>();
				// TODO Auto-generated method stub
				
				//Recive new positions of all particuls
				ACLMessage msg = receive();
				if(msg!=null) {
					for(int i=0;i<pos.length;i++) {
						current_pos.add(Arrays.asList(Integer.parseInt(msg.getContent().split("-")[i].split(":")[0]),Integer.parseInt(msg.getContent().split("-")[i].split(":")[1])));
					}
					
					//update positions value of grid 
					grid.setPos(current_pos);
				}
			}
		});
		
	}
	
	

}
