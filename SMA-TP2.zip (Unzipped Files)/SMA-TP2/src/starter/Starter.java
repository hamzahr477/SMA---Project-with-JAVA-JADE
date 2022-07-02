package starter;
import jade.core.Runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import model.Water;

public class Starter {

	public static void main(String [] args) {
		Runtime rt = Runtime.instance();
		Profile p = new ProfileImpl();
		p.setParameter(Profile.MAIN_HOST, "localhost");
		p.setParameter(Profile.GUI, "true");
		ContainerController  cc = rt.createMainContainer(p);
		List<AgentController> ac = new ArrayList<AgentController>() ;
		AgentController ac2;
		AgentController ac3;
		int nb=20;
		Water.setup();
		Object [][] posS = new Object[nb][2];
		Object [][] posF = new Object[nb][2];

		Random r = new Random();

		try {
			ac3=cc.createNewAgent("AgentGUI", "Agent.GUI",null);
			ac3.start();
			for(int i=0;i<nb;i++) {
				posF[i][0]=""+r.nextInt(Water.water.length-1);
				posF[i][1]=""+r.nextInt(Water.water.length-1);
				posS[i][0]=""+r.nextInt(Water.water.length-1);
				posS[i][1]=""+r.nextInt(Water.water.length-1);
				ac.add(cc.createNewAgent("AgentFish:"+(i+1), "Agent.AgentFish",posF[i]));
				ac.get(i).start();
				ac2=cc.createNewAgent("AgentShark"+(i+1), "Agent.Shark",posS[i]);
				ac2.start();
			}
			
			
			
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
