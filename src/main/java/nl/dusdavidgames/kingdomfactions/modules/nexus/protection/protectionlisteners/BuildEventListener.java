package nl.dusdavidgames.kingdomfactions.modules.nexus.protection.protectionlisteners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.ProtectionModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class BuildEventListener implements Listener{

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getBlock() == null)
			return;
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
		if(p.hasBuildAction()) e.setCancelled(true);
		if(p.isVanished()) return;
		if(e.isCancelled()) return;
		if(NexusModule.getInstance().isNexus(e.getBlock().getLocation())) return;
		if(!p.canBuild(e.getBlock().getLocation())) {
			e.setCancelled(!ProtectionModule.getInstance().tryInfluence(p, 200, e.getBlock()));
		} 
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
		if(p.hasBuildAction()) e.setCancelled(true);
		if(p.isVanished()) return;
		if(e.isCancelled()) return;
		if(!p.canBuild(e.getBlock().getLocation())) {
			e.setCancelled(!ProtectionModule.getInstance().tryInfluence(p, 200, e.getBlock()));
			
		} 
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(p.hasBuildAction()) e.setCancelled(true);
		if(p.isVanished()) return;
		if(e.isCancelled()) return;
		if(e.getClickedBlock() == null) return;
		if(!p.canBuild(e.getClickedBlock().getLocation())) {
			switch(e.getClickedBlock().getType()) {
			case CHEST:
				e.setCancelled(!ProtectionModule.getInstance().tryInfluence(p, 300));
				break;
			case TRAPPED_CHEST:
				e.setCancelled(!ProtectionModule.getInstance().tryInfluence(p, 300));
				break;
			case ENDER_CHEST:
				e.setCancelled(!ProtectionModule.getInstance().tryInfluence(p, 200));
				break;
			case ACACIA_DOOR:
			case BIRCH_DOOR:
			case DARK_OAK_DOOR:
			case SPRUCE_DOOR:
			case JUNGLE_DOOR:
			case WOODEN_DOOR:
			case TRAP_DOOR:
			case IRON_DOOR:
			case IRON_TRAPDOOR:
			case SPRUCE_FENCE_GATE:
			case DARK_OAK_FENCE_GATE:
			case BIRCH_FENCE_GATE:
			case FENCE_GATE:
			case ACACIA_FENCE_GATE:
			case JUNGLE_FENCE_GATE:
			case DISPENSER:
			case DROPPER:
			case FURNACE:
			case HOPPER:
			case BURNING_FURNACE:
				e.setCancelled(!ProtectionModule.getInstance().tryInfluence(p, 200));
			break;
				
		    default: break;
			}
		} 
	
	}
	@EventHandler
	public void onArmorStandManupulation(PlayerArmorStandManipulateEvent e) {
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
		if(p.isVanished()) return;
		if(e.isCancelled()) return;
		if(e.getRightClicked() == null) return;
		if(!p.canBuild(e.getRightClicked().getLocation())) {
			e.setCancelled(!ProtectionModule.getInstance().tryInfluence(p, 200));
		} 
	}
	
	
	@EventHandler
	public void soilChangePlayer(PlayerInteractEvent e) {
	      if(e.getClickedBlock() == null) return;
		if ((e.getAction() == Action.PHYSICAL) && (e.getClickedBlock().getType() == Material.SOIL)) {
			KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
			if(p.isVanished()) return;
			if(e.isCancelled()) return;
			if(!p.canBuild(e.getClickedBlock().getLocation())) {
				e.setCancelled(!ProtectionModule.getInstance().tryInfluence(p, 50));
			} 
	
		}
		}
}
