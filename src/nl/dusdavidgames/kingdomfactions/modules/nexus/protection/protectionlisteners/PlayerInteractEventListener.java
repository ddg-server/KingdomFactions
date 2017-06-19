package nl.dusdavidgames.kingdomfactions.modules.nexus.protection.protectionlisteners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.ProtectionModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class PlayerInteractEventListener implements Listener{
	
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.isCancelled()) return;
	     KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());        
			if(p.getSettingsProfile().hasAdminMode()) return;
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getClickedBlock() == null) return;

			Block clickedBlock = e.getClickedBlock();
		

			if (clickedBlock.getType().equals(Material.CHEST) || clickedBlock.getType().equals(Material.TRAPPED_CHEST) || clickedBlock.getType().equals(Material.BEACON)) {
				e.setCancelled(!ProtectionModule.getInstance().tryInfluence(p, 500));
				return;
			}

			if (clickedBlock.getType().equals(Material.BURNING_FURNACE) || clickedBlock.getType().equals(Material.FURNACE) || clickedBlock.getType().equals(Material.DISPENSER) || clickedBlock.getType().equals(Material.HOPPER) || clickedBlock.getType().equals(Material.DROPPER)) {
				e.setCancelled(!ProtectionModule.getInstance().tryInfluence(p, 150));
				return;
			}

			MaterialData data = clickedBlock.getState() != null ? clickedBlock.getState().getData() : null;
			if (data instanceof Openable) {
				if (clickedBlock.getType() == Material.IRON_TRAPDOOR || clickedBlock.getType() == Material.IRON_DOOR) return;
				e.setCancelled(!ProtectionModule.getInstance().tryInfluence(p, 150));
				return;
			}
		}
	}


}
