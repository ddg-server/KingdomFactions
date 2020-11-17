package nl.dusdavidgames.kingdomfactions.modules.mine.listeners;

import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.util.ArrayList;

public class PortalProtection implements Listener{
	
	@EventHandler
	public void onBucketEmpty(PlayerBucketEmptyEvent event){
		if(!canBuild(event.getPlayer(), event.getBlockClicked().getLocation()))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void blockPlace(BlockPlaceEvent event){
		if(!canBuild(event.getPlayer(), event.getBlock().getLocation()))
			event.setCancelled(true);
	}
	
	
	@EventHandler
	public void bloceBreak(BlockBreakEvent event){
		if(!canBuild(event.getPlayer(), event.getBlock().getLocation()))
			event.setCancelled(true);
	}
	
	
	
	
	public boolean canBuild(Player player, Location loc){
		
		if(PlayerModule.getInstance().getPlayer(player).getSettingsProfile().hasAdminMode())
			return true;
		
		ArrayList<Location> locations = new ArrayList<>();
		boolean canBuild = true;
		
		locations.add(Utils.getInstance().getNewLocation(loc));
		locations.add(Utils.getInstance().getNewLocation(loc).add(0, 1, 0));//above
		locations.add(Utils.getInstance().getNewLocation(loc).subtract(0, 1, 0));//under
		locations.add(Utils.getInstance().getNewLocation(loc).add(1, 0, 0));//side
		locations.add(Utils.getInstance().getNewLocation(loc).add(0, 0, 1));//side
		locations.add(Utils.getInstance().getNewLocation(loc).subtract(1, 0, 0));//side
		locations.add(Utils.getInstance().getNewLocation(loc).subtract(0, 0, 1));//side
		
		for(Location location : locations){
			if(location.getBlock() != null){
				if(location.getBlock().getType().equals(Material.PORTAL)){
					if(canBuild)
						canBuild = false;
				}
			}
		}
		
		locations.clear();
		
		if(!canBuild)
			player.sendMessage(ChatColor.RED + "Je hebt geen toestemming om deze actie uit te voeren!");
		
		return canBuild;
	}
}