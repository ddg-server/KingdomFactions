package nl.dusdavidgames.kingdomfactions.modules.war.runnable;

import org.bukkit.Bukkit;

import nl.dusdavidgames.kingdomfactions.modules.war.War;
import nl.dusdavidgames.kingdomfactions.modules.war.WarModule;
import nl.dusdavidgames.kingdomfactions.modules.war.event.WarDurationChangeEvent;

public class WarRunnable implements Runnable {

	@Override
	public void run() {
		if (WarModule.getInstance().isWarActive()) {
			War war = WarModule.getInstance().getWar();
			Bukkit.getPluginManager().callEvent(new WarDurationChangeEvent());
			if (System.currentTimeMillis() >= war.getTimeInMilliSeconds()) {
				war.end();
			}
		}
	}
}
