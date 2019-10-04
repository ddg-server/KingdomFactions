package nl.dusdavidgames.kingdomfactions.modules.influence.runnable;

import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class InfluenceRunnable implements Runnable{

	@Override
	public void run() {
		for(KingdomFactionsPlayer p : PlayerModule.getInstance().getPlayers()) {
			IPlayerBase base = p;
			base.addInfluence(1);
		}
	}

}
