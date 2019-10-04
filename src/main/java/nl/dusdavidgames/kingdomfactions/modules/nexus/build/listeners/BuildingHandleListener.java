package nl.dusdavidgames.kingdomfactions.modules.nexus.build.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.CapitalNexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.BuildModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings.Building;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildingType;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.shops.ShopsModule;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class BuildingHandleListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if (e.getClickedBlock() == null)
			return;
		if(NexusModule.getInstance().isNexus(e.getClickedBlock())) {
		   INexus ne = NexusModule.getInstance().getNexus(e.getClickedBlock());
		   if(ne instanceof Nexus) {
			   Nexus n = (Nexus) ne;
			   if(n.hasOwner()) {
				   if(p.hasFaction()) {
				   if(n.getOwner() == p.getFaction()) {
					   p.getPlayer().closeInventory();
					   p.openInventory(ShopsModule.getInstance().getShop(BuildingType.NEXUS, BuildLevel.getLevel(n.getLevel())).getShopInventory());
				   }
				   }
			   }
		   } else {
			   CapitalNexus nexus = (CapitalNexus) ne;
			   if(p.getKingdom().getType() == nexus.getKingdom()) {
				   p.getPlayer().closeInventory();
				   p.openInventory(ShopsModule.getInstance().getShop(BuildingType.NEXUS, BuildLevel.getLevel(6)).getShopInventory());
			
			   }
		   }
		}
		if (BuildModule.getInstance().isBuilding(e.getClickedBlock())) {
			Building b = BuildModule.getInstance().getBuilding(e.getClickedBlock());
			if (b.getNexus().getOwner() == p.getFaction()) {
				p.getPlayer().closeInventory();
				p.openInventory(ShopsModule.getInstance()
						.getShop(b.getType(), BuildLevel.getLevel(b.getLevel().getLevel())).getShopInventory());
			} else {
				p.sendMessage(Messages.getInstance().getPrefix() + "Dit is niet jouw gebouw!");
			}
		} else {
			return;
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
		if (e.getBlock() == null)
			return;
		if (!BuildModule.getInstance().isBuilding(e.getBlock()))
			return;
		e.setCancelled(true);
		if (BuildModule.getInstance().getBuilding(e.getBlock()).getNexus().getOwner() == p.getFaction()) {
			p.sendMessage(Messages.getInstance().getPrefix() + "Je kan dit blok niet breken.");
		} else {
			p.sendMessage(Messages.getInstance().getPrefix() + "Je moet de Nexus hebben. Niet dit blok!");
		}
	}
	
	@EventHandler
	public void onPiston(BlockPistonExtendEvent e) {
		if(BuildModule.getInstance().isBuilding(e.getBlock())) { 
			e.setCancelled(true);
		}
		
	}

}
