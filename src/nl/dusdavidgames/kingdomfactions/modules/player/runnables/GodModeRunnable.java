package nl.dusdavidgames.kingdomfactions.modules.player.runnables;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.GodMode;
import org.bukkit.Bukkit;

public class GodModeRunnable {

	public GodModeRunnable() {
		slowGodModeRunnable();
	}

	private void slowGodModeRunnable() {
		Bukkit.getScheduler().runTaskTimer(KingdomFactionsPlugin.getInstance(), new Runnable() {
			@Override
			public void run() {
				for(KingdomFactionsPlayer p : PlayerModule.getInstance().getPlayers()) {
					if(p.getSettingsProfile().getGodMode().equals(GodMode.FAKEDAMAGE) || p.getSettingsProfile().getGodMode().equals(GodMode.NODAMAGE)) {
						p.sendActionbar(ChatColor.RED +""+ ChatColor.BOLD + "Je zit momenteel in GodMode! Type: " + ChatColor.GRAY +""+ ChatColor.BOLD + p.getSettingsProfile().getGodMode());
					}
				}				
			}
		}, 0, 100); 
	}

}
