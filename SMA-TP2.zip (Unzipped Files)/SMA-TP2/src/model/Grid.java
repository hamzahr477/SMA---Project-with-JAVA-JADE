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

	private final int GRID_SIZE = Water.water.length;
	private final int CELL_SIZE = 10;
	private Color[] C ;
	Timer t = new Timer(20,this);



	/**
	 * Create the panel.
	 */
	public Grid() {
		C=new Color[3];
		for(int i=0;i<3;i++) {
			C[i]=getColor(i);
		}
	}
	
	private Color getColor(int i) {
		if(i==0)
			return new Color(255,0,0);
		else if(i==1)
			return new Color(0,255,0);
		
		return new Color(0,0,255);
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
		
		for(int i=0;i<Water.water.length;i++) {
			//System.out.println(current_pos.get(i).get(0)+" : "+current_pos.get(i).get(1));
			for(int j=0; j<Water.water[i].length;j++) {
				if(Water.water[i][j]!=0) {
					if(Water.water[i][j]==-1)
						g.setColor(C[0]);
					else if(Water.water[i][j]==1)
						g.setColor(C[1]);
					else g.setColor(C[2]);
				g.fillRoundRect(i* CELL_SIZE,j* CELL_SIZE, CELL_SIZE, CELL_SIZE, CELL_SIZE, CELL_SIZE);
				}
			}
		}
		g.setColor(Color.black);
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void update() {
		// TODO Auto-generated method stub
		repaint();
	}

}
