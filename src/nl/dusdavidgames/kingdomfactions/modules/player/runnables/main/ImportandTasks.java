package nl.dusdavidgames.kingdomfactions.modules.player.runnables.main;

import java.util.Iterator;

import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Cooldown;
import nl.dusdavidgames.kingdomfactions.modules.utils.TeleportationAction;
import nl.dusdavidgames.kingdomfactions.modules.utils.action.IAction;

public class ImportandTasks {

	
	
	
	public ImportandTasks(MainPlayerRunnable r) {

	    r.scheduleTask(new ScheduledPlayerTask() {
			
			@Override
			public void run(KingdomFactionsPlayer player) {
				Iterator<Cooldown> iter = player.getCooldowns().iterator();
				while(iter.hasNext()) {
					iter.next().lower();
				}
				
			}
		});
	
	    r.scheduleTask(new ScheduledPlayerTask() {
			
			@Override
			public void run(KingdomFactionsPlayer player) {
				KingdomFactionsPlayer p = player;
				if(!p.hasAction()) {
					return;
				}
				IAction a = p.getAction();
				if(a instanceof TeleportationAction) {
					TeleportationAction ta = (TeleportationAction) a;
					ta.handleDelayChange();
				}
			}
		});

	}
	
}
