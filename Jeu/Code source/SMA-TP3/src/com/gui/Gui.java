package com.gui;
import com.env.*;

import jade.core.Agent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import jade.core.Agent;
public class Gui extends JPanel implements ActionListener {
	
	Timer time = new Timer(70,this);
	int CELL_SIZE = 13;
	Color player_Color = new Color(102, 0, 102);
	Color hunter_Color = Color.red;
	Color anti_Hanter_Color = Color.green;
	Color fruit_Color = Color.blue;
	Color golden_fruit_Color = Color.yellow;
	Color wall_Color = new Color(204, 102, 0);
	Color void_Color = new Color(255, 255, 255);
	public Gui() { 
		
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		// paint the grid
		for (int i = 0; i < Environment.getLength(); i++) {
			for (int j = 0; j < Environment.getLength(); j++) {
				switch (Environment.getMat_env()[i][j]) {
				case 0:
					g.setColor(void_Color);
		            g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
		            break;
				case 1:
					g.setColor(wall_Color);
		            g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
		            break;
				case 2:
					g.setColor(player_Color);
		            g.fillRoundRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE,CELL_SIZE,CELL_SIZE);
		            break;
				case 3:
					g.setColor(fruit_Color);
		            g.fillRoundRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE,CELL_SIZE,CELL_SIZE);
		            break;
				case 4:
					g.setColor(golden_fruit_Color);
		            g.fillRoundRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE,CELL_SIZE,CELL_SIZE);
		            break;
				case -1:
					g.setColor(hunter_Color);
		            g.fillRoundRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE,CELL_SIZE,CELL_SIZE);
		            break;
				case -2:
					g.setColor(anti_Hanter_Color);
		            g.fillRoundRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE,CELL_SIZE,CELL_SIZE);
		            break;
				}
				
	            g.setColor(Color.black);
	            g.drawRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
               
               
		     }
		}
		

		
		time.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		repaint();
		
	}

	


}
