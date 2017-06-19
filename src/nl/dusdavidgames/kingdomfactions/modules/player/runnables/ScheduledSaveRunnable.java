package nl.dusdavidgames.kingdomfactions.modules.player.runnables;

import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

public class ScheduledSaveRunnable implements Runnable{

	@Override
	public void run() {
		if(PlayerModule.getInstance().getPlayers().isEmpty()) return;
		Logger.INFO.log("Starting auto save...");
		Logger.INFO.log(PlayerModule.getInstance().getPlayers().size() + " players to save..");
		PlayerModule.getInstance().saving = true;
		PlayerModule.getInstance().saveAsync();
	}
	

}
