package nl.dusdavidgames.kingdomfactions.modules.time;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.Bukkit;

public class TimeModule {
    private static @Getter @Setter TimeModule instance;
	public TimeModule() {
		setInstance(this);
		new TimeHelper();
		savePlayerTimes();

	}
	
	private void savePlayerTimes() {
		Bukkit.getScheduler().runTaskTimer(KingdomFactionsPlugin.getInstance(), new Runnable() {
			@Override
			public void run() {
				for(KingdomFactionsPlayer p : PlayerModule.getInstance().getPlayers()) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomFactionsPlugin.getInstance(), new Runnable() {
						@Override
						public void run() {
							TimeHelper.getInstance().updateTime(p);							
						}
					});
				}				
			}
		}, 0L, 20L * 20); //Save once every 20 seconds
	}
}
