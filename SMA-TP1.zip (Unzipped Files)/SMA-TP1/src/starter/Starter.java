package starter;
import jade.core.Runtime;

import java.util.ArrayList;
import java.util.List;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

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
		//number of particles to start
		int nb=9;
		//Position for each particles
		Object [][] pos = new Object[nb][2];
		try {	
			//Starting...
			for(int i=0;i<nb;i++) {
				pos[i][0]=""+i;
				pos[i][1]=""+i;
			ac.add(cc.createNewAgent("AgentParitcule"+(i+1), "agent.Particule",pos[i]));
			ac.get(i).start();
			}
			ac2=cc.createNewAgent("AgentBoard", "agent.BoardManager",pos);
			ac3=cc.createNewAgent("AgentGUI", "agent.GUI",pos);
			ac3.start();
			ac2.start();
		
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
