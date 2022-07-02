package com.starter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.gui.*;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import com.env.*;
public class Starter {
	
	
	public static void main(String [] args) throws StaleProxyException {
		// init environment - GUI
		Environment e = new Environment(40, 40);
		String[] choices = { "Easy", "Medium", "Hard"};
		String[] options = new String[] {"Start", "Exit"};
	    String input = (String) JOptionPane.showInputDialog(null, "Level of game",
	        "SMA TP3 - PACMAN GAME", JOptionPane.YES_NO_CANCEL_OPTION, null, 
	        choices, // Array of choices
	        choices[1]); // Initial choice
	    if(input==null)input="";
	    switch (input) {
	    case "Easy":{
			System.out.println("Level : Easy");
			Environment.level=200;
			break;
	    }
		case "Medium":{
			System.out.println("Level : Medium");
			Environment.level=100;
			break;
			    }
		case "Hard":{
			System.out.println("Level : Hard");
			Environment.level=70;
			break;
		}
	    	
	    default : {
			System.out.println("Exit");
			System.exit(0);
		}
			
		}
		Runtime rt = Runtime.instance();
		Profile p = new ProfileImpl();
		p.setParameter(Profile.MAIN_HOST, "localhost " );
		int port =  (int) (Math.random() * 80)+10;

		p.setParameter(Profile.MAIN_PORT, "300"+port);
		//p.setParameter(Profile.GUI, "true");
		ContainerController  cc = rt.createMainContainer(p);
		AgentController ac0;
		AgentController ac1;
		AgentController ac2;
		AgentController ac3;
		ac0 = cc.createNewAgent("Agent_Env_Manager", "com.agents.AgentEnvManager",null);
		ac1 = cc.createNewAgent("Agent_GUI", "com.agents.AgentGUI",null);
		ac2 = cc.createNewAgent("Agent_Player", "com.agents.AgentPlayer",null);
		ac3 = cc.createNewAgent("Agent_Hunter", "com.agents.HunterAgent",null);

		
		ac0.start();
		ac2.start();		
		ac1.start();
		ac3.start();
		
//		List<Integer> blassa=Environment.findRandomEmptyCell();
	}

}
