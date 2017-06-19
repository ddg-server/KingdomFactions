package nl.dusdavidgames.kingdomfactions.modules.pvplog;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.vanish.event.VanishStatusChangeEvent;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.permission.PermissionModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.CombatTracker;

public class VanishListener implements Listener{

	
	@EventHandler
	public void onVanish(VanishStatusChangeEvent e) {
		CombatTracker p = PlayerModule.getInstance().getPlayer(e.getPlayer()).getCombatTracker();
		if(p.isInCombat()) {
			if(e.isVanishing()) {
				PermissionModule.getInstance().getStaffMembers().broadcast(p.getPlayer().getFormattedName() + ChatColor.YELLOW + " is in vanish gegaan, terwijl hij/zij in gevecht was!");
			}
		}
	}
}
