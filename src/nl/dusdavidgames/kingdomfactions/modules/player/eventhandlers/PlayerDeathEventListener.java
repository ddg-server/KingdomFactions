package nl.dusdavidgames.kingdomfactions.modules.player.eventhandlers;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.deathban.DeathBanModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.settings.Setting;
import nl.dusdavidgames.kingdomfactions.modules.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathEventListener implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {


		KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(e.getEntity());

		
		e.setKeepInventory(false);
		
		 if(player.getKingdom().getType().equals(KingdomType.GEEN)) {
			   player.getInventory().clear();
			   player.getInventory().addItem(Item.getInstance().getItem(Material.COMPASS, ChatColor.RED + "Selecteer jouw kingdom", 1));
			   player.updateInventory();
			   player.teleport(player.getKingdom().getSpawn());
		   }

		if (e.getEntity().getKiller() != null) {
			KingdomFactionsPlayer killer = PlayerModule.getInstance().getPlayer(e.getEntity().getKiller());
				e.setDeathMessage(getName(player) + " is verwond door " + getName(killer) + "!");
				if(player.getCombatTracker().isInCombat()) {
					player.getCombatTracker().clearCombat();
				}
              Bukkit.getScheduler().runTaskLater(KingdomFactionsPlugin.getInstance(), new Runnable() {
				@Override
				public void run() {
					if(!player.hasPermission("kingdomfactions.deathban.ignore")) {
						if(Setting.USE_DEATHBAN.isEnabled()) {
						DeathBanModule.getInstance().ban(player);
						}
					}
					
				}
			}, 40L);
			
		} else {
			e.setDeathMessage(null);
		}
	//	e.getEntity().spigot().respawn();
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
		Bukkit.getScheduler().runTaskLater(KingdomFactionsPlugin.getInstance(), new Runnable(){
			@Override
			public void run(){
	     
				if (p.getKingdom() != null) {
					if (p.hasFaction()) {
						if (p.getFaction().hasHome()) {
							p.teleport(p.getFaction().getHome().getLocation());
						} else {
							p.teleport(p.getKingdom().getSpawn());
						}
					} else {
						p.teleport(p.getKingdom().getSpawn());
					}
				}
			}
		}, 20);
	}
	
	
	private String getName(KingdomFactionsPlayer player) {
		StringBuilder builder = new StringBuilder();
		builder.append(player.getKingdom().getType().getPrefix());
		if(player.hasFaction()) {
			builder.append(player.getFaction().getPrefix());
		}
		builder.append(player.getName());
		return builder.toString();
	}
}
