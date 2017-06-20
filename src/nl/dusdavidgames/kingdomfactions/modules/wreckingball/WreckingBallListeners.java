package nl.dusdavidgames.kingdomfactions.modules.wreckingball;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.BuildModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class WreckingBallListeners implements Listener {

	
	@EventHandler
	public void onUse(PlayerInteractEvent e) {

		try {
			if (e.getAction() != Action.LEFT_CLICK_BLOCK) {
				return;
			}
			if (e.getItem() == null) return;
			if (!e.getItem().hasItemMeta()) return;
			if (!e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(WreckingBallModule.WRECKINGBALL_NAME))
				return;
			if (e.isCancelled()) return;
			KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(e.getPlayer());
			if (player.isVanished()) return;
			if (!player.isStaff()) {
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), null);
				player.sendMessage(Messages.getInstance().getPrefix() + "Het is voor jou niet toegestaan om dit item te gebruiken");
				return;
			}

			if (e.getClickedBlock() == null) return;
			if (e.isCancelled()) return;

			BlockWreckedEvent ev = new BlockWreckedEvent(e.getClickedBlock(), player.getPlayer());
			Bukkit.getPluginManager().callEvent(ev);

			e.getClickedBlock().breakNaturally();

		} catch (NullPointerException npe) {
			//bad practise!
		}

	}
	
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		try {
	  		ItemStack i = e.getItemDrop().getItemStack();
	  		if(i.getType() != Material.FIREBALL) return;
	  		if(!i.hasItemMeta()) return;
	  		if(i.getItemMeta().getDisplayName().equalsIgnoreCase(WreckingBallModule.WRECKINGBALL_NAME)) {
		  		e.getItemDrop().remove();
	  		}
		} catch(NullPointerException ex) {
			//bad practise!
		}
	}
	
	@EventHandler
	public void onWreck(BlockWreckedEvent e) {
		if(e.getBlock().getType() == Material.CHEST || e.getBlock().getType() == Material.TRAPPED_CHEST) {
			e.getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je mag geen kisten breken met een WreckingBall!");
			return;
		}
		if(NexusModule.getInstance().isNexus(e.getBlock())) return;
		if(BuildModule.getInstance().isBuilding(e.getBlock())) return;
	}

}
