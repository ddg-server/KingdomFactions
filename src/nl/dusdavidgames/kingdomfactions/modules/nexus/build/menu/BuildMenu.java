package nl.dusdavidgames.kingdomfactions.modules.nexus.build.menu;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.CapitalNexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.BuildModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings.BuildAction;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings.Building;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildingType;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Item;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class BuildMenu implements Listener {

	private static @Getter @Setter BuildMenu instance;

	public BuildMenu() {
		setInstance(this);
	}

	public void openBuildMenu(KingdomFactionsPlayer p) {
		if(p.getCurrentNexus() == null) {
			p.sendMessage(Messages.getInstance().getPrefix() + "Je staat momenteel niet in een stad!");
		}
		if(p.getCurrentNexus() instanceof CapitalNexus) {
			p.sendMessage(Messages.getInstance().getPrefix() + "Je staat momenteel in de hoofdstad!");
			return;
		}
		Inventory in = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.BLUE + "Gebouwen");
		Nexus nexus = (Nexus) p.getCurrentNexus();
		for (BuildingType b : BuildingType.values()) {
			ArrayList<String> lore = new ArrayList<String>();
			if(b == BuildingType.NEXUS) {
				if(BuildLevel.getLevel(nexus.getLevel()) == BuildLevel.LEVEL_8) {
					lore.add(ChatColor.RED + "Maximaal geupgrade!");
				} else {
				lore.add(ChatColor.RED + "Level: " + BuildLevel.getLevel(nexus.getLevel()).getRoman());
				lore.add(ChatColor.RED + "Upgrade kosten: " +  BuildingType.NEXUS.getPrice(p.getKingdomTerritory(), 
						BuildLevel.getLevel(nexus.getLevel()).next()));
				}
			} else if (nexus.getBuilding(b) != null) {
				if(nexus.getBuilding(b).getLevel() == BuildLevel.LEVEL_8) {
					lore.add(ChatColor.RED + "Maximaal geupgrade!");
				} else {
					Building bu = nexus.getBuilding(b);
					lore.add(ChatColor.RED + "Level: " + bu.getLevel().getRoman());
					lore.add(ChatColor.RED + "Upgrade kosten: " +  bu.getType().getPrice(p.getKingdomTerritory(), bu.getLevel().next()));
				}
	
			} else {
				lore.add(ChatColor.RED + "Nog niet gebouwd!");
				lore.add(ChatColor.RED + "Bouw kosten: " + b.getPrice(p.getKingdomTerritory(), BuildLevel.LEVEL_1));
				lore.add(ChatColor.RED + "Benodigde Nexus Level: " + b.getLevel());
			}
	

			in.addItem(Item.getInstance().getItem(b.getMaterial(), ChatColor.RED + b.toString(), 1, lore));
		}
		p.openInventory(in);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Gebouwen")) {
			return;
		}
		Player player = (Player) e.getWhoClicked();
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(player);
		e.setCancelled(true);

		if ((e.getCurrentItem() == null)) {
			player.closeInventory();
			return;
		}
		if(p.getCurrentNexus() instanceof CapitalNexus) {
			p.getPlayer().closeInventory();
			return;
		}
	     Nexus n = (Nexus) p.getCurrentNexus();
	    if(n.getOwner() == p.getFaction() && p.getMembershipProfile().isFactionLeader()) {
	    	BuildingType type = BuildingType.getType(e.getCurrentItem().getType());
	    	if(type == BuildingType.NEXUS) {
	    		  if(n.getLevel() >= 8) { 
	 	    		 return;  
	 	    	   } 
	    	BuildModule.getInstance().openBuildBuilerMenu(p, new BuildAction(p, n));
	    	} else if(n.hasBuilding(type)) {
	    	   Building b = n.getBuilding(type);
	    	   if(b.getLevel() == BuildLevel.LEVEL_8) { 
	    		 return;  
	    	   } 
	    	  
	    	  	BuildModule.getInstance().openBuildBuilerMenu(p, new BuildAction(b, p));
	    	   } else {
	    		   if(n.getLevel() >= type.getLevel())  {
	    		    p.getPlayer().closeInventory();
	    		    p.sendMessage(Messages.getInstance().getPrefix() + "Je bent een Bouw Actie gestart! Klik op een blokje om het gebouw te bouwen!");
	    		    p.sendMessage(Messages.getInstance().getPrefix() + "Gebruik /nexus cancel om te actie te annuleren!");
	    			p.setAction(new BuildAction(n,  type, p.getKingdomTerritory(), p));
	    		   } else {
	    			   p.sendMessage(Messages.getInstance().getPrefix() + "Jouw Nexus level is niet hoog genoeg om dit gebouw te bouwen!");
	    		   }
	    		   }
	    
	    		   return;
	    	   }
	    	} 
	    	
		
	}
	
	
