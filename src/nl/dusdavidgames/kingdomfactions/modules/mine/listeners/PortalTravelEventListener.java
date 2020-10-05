package nl.dusdavidgames.kingdomfactions.modules.mine.listeners;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.mine.MineTravelEvent;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PortalTravelEventListener implements Listener {

	@EventHandler
	public void onPortal(PlayerMoveEvent e) {
		if (e.getPlayer().getEyeLocation().getBlock().getType() == Material.PORTAL) {
			KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
			if(p.getKingdomTerritory() == p.getKingdom().getType() || p.getKingdomTerritory() == KingdomType.GEEN) {
			if (e.getPlayer().getLocation().getWorld() == Utils.getInstance().getMiningWorld()) {
				e.getPlayer()
						.sendMessage(ChatColor.DARK_PURPLE + "Woosh.. Je bent geteleporteerd naar Ranos!");

		
				e.getPlayer().teleport(PlayerModule.getInstance().getPlayer(e.getPlayer()).getKingdom().getSpawn());

				new BukkitRunnable() {

					@Override
					public void run() {
						e.getPlayer()
								.teleport(PlayerModule.getInstance().getPlayer(e.getPlayer()).getKingdom().getSpawn());

					}
				}.runTaskLater(KingdomFactionsPlugin.getInstance(), 20L);
				   Bukkit.getPluginManager().callEvent(new MineTravelEvent(p));
				   PlayerModule.getInstance().getPlayer(e.getPlayer()).updateTerritory();
			} else {
				e.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "Woosh.. Je bent geteleporteerd naar Mithras!");

				e.getPlayer()
						.teleport(PlayerModule.getInstance().getPlayer(e.getPlayer()).getKingdom().getMiningSpawn());
          
				new BukkitRunnable() {

					@Override
					public void run() {
						e.getPlayer().teleport(
								PlayerModule.getInstance().getPlayer(e.getPlayer()).getKingdom().getMiningSpawn());

					}
				}.runTaskLater(KingdomFactionsPlugin.getInstance(), 20L);
				   Bukkit.getPluginManager().callEvent(new MineTravelEvent(p));
			   PlayerModule.getInstance().getPlayer(e.getPlayer()).updateTerritory();
			}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null)
			return;
		if (e.getClickedBlock().getType() != Material.OBSIDIAN)
			return;
		if (e.getPlayer().getItemInHand().getType() != Material.FLINT_AND_STEEL)
			return;
		
		if(!e.getPlayer().isOp()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPortalDestroy(BlockBreakEvent e) {
		if (e.getBlock() == null)
			return;
		if(e.getBlock().getType() == Material.PORTAL) {
			if(!e.getPlayer().isOp()) {
				e.setCancelled(true);
			}
		}
	}
}
