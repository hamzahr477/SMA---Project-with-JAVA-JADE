package model;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;



public class Grid extends JPanel  implements ActionListener {

	private final int GRID_SIZE = 10;
	private final int CELL_SIZE = 50;
	private Color[] C ;
	List<List<Integer>> current_pos=new ArrayList(); 
	Timer t = new Timer(20,this);



	/**
	 * Create the panel.
	 */
	public Grid(Object[][] pos) {
		C=new Color[pos.length];
		for(int i=0;i<pos.length;i++) {
			C[i]=getRandomColor();
			current_pos.add(Arrays.asList(i,i));
		}
	}
	
	private Color getRandomColor() {
    	int R = (int) (Math.random() * 200);
        int G = (int) (Math.random() * 200);
        int B = (int) (Math.random() * 200);
    	Color c = new Color(R, G, B);
    	return c;
    }
	
	
	public void setPos(List<List<Integer>> pos) {
		current_pos=pos;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw each grid element
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				/*** fill the grid ***/
				g.setColor(Color.white);
	            g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
	            g.setColor(Color.black);
                g.drawRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);

		     }
		}
		
		for(int i=0;i<current_pos.size();i++) {
			//System.out.println(current_pos.get(i).get(0)+" : "+current_pos.get(i).get(1));
			g.setColor(C[i]);
			g.fillRoundRect(current_pos.get(i).get(0)* CELL_SIZE,current_pos.get(i).get(1)* CELL_SIZE, CELL_SIZE, CELL_SIZE, CELL_SIZE, CELL_SIZE);

		}
		g.setColor(Color.black);
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
