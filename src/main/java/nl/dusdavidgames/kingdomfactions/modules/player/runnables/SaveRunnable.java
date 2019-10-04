package nl.dusdavidgames.kingdomfactions.modules.player.runnables;

import org.bukkit.Bukkit;

import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

public class SaveRunnable implements Runnable {

	@Override
	public void run() {
		if (PlayerModule.getInstance().getQueue().isEmpty())
			return;
		KingdomFactionsPlayer p = PlayerModule.getInstance().getQueue().peek();
		
		p.save();
		PlayerModule.getInstance().getQueue().poll();
		
		
		if(!Bukkit.getOnlinePlayers().contains(p.getPlayer())) {
			Logger.MEMLEAK.log("Discovered empty KingdomFactionsPlayer object which should NOT exist.");
		}
		
		if(PlayerModule.getInstance().saving) {
			if(PlayerModule.getInstance().getQueue().isEmpty()) {
				PlayerModule.getInstance().saving = false;
				Logger.INFO.log("Auto save finished!");
			}
		}
	}

}
