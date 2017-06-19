package nl.dusdavidgames.kingdomfactions.modules.nexus.runnables;

import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.exception.DataException;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.CapitalNexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;

public class Regeneration implements Runnable {

	public void run() {
     for(INexus n : NexusModule.getInstance().getNexuses()) {
    	 if(n == null) continue;
    	 switch(n.getType()) {
		case CAPITAL:
			if(n.getHealth() >= 800) {
				n.setHealth(800);
				return;
			} else {
				if(n.getHealth() < 800) {
				   CapitalNexus ne = (CapitalNexus) n;
				   if(ne.isDestroyed()) {
					   try {
						if(KingdomFactionsPlugin.getInstance().getDataManager().getBoolean("Test.enabled")) {
							   if(n.getHealth() >= 750) {
									n.setHealth(800);
									ne.setDestroyed(false);
									Utils.getInstance().strikeLighting(ne.getLocation(), false);
							   } else {
								   n.setHealth(n.getHealth() + 50);
							   }
							   return;
						   }
					} catch (DataException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					   if(n.getHealth() >= 795) {
							n.setHealth(800);
							ne.setDestroyed(false);
							Utils.getInstance().strikeLighting(ne.getLocation(), false);
					   } else {
						   n.setHealth(n.getHealth() + 5);
					   }
				   } else {
					   n.setHealth(n.getHealth() + 1);
				   }
				}
			}
			
			break;
		case FACTION:
			if(n.getHealth() >= 600) {
				n.setHealth(600);
				return;
			}
			if(n.getHealth() < 600) {
				n.setHealth(n.getHealth() + 1);
			}
			
			break;
		default:
			break;
    	 
    	 }
    	 
     }
     }

}
