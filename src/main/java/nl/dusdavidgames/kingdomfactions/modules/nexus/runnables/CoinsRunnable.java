package nl.dusdavidgames.kingdomfactions.modules.nexus.runnables;

import org.bukkit.Bukkit;

import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionRank;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class CoinsRunnable {

	
	public CoinsRunnable() {
		scheduleCoins();
	}
	
	private void scheduleCoins() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(KingdomFactionsPlugin.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for(KingdomFactionsPlayer player : PlayerModule.getInstance().getPlayers()) {
					if(player.hasFaction()) {
						if(player.getFaction().hasNexus()) {
							Nexus n = player.getFaction().getBestNexus();
							if(player.getFactionRank() == null) {
								player.setFactionRank(FactionRank.SPELER);
								player.addCoins(player.getFactionRank().getPayment(n.getLevel()));
								return;
							}
							player.addCoins(player.getFactionRank().getPayment(n.getLevel()));
						}
					}
				} 
				
			}

		}, 0, 20 * 60 * 10);
		
	}
		
}
