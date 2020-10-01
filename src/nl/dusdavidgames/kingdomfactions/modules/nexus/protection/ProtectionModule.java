package nl.dusdavidgames.kingdomfactions.modules.nexus.protection;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.configuration.ConfigModule;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.detection.MoveListener;
import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.detection.TerritoryUpdateEvent;
import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.protectionlisteners.*;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class ProtectionModule {

	private static @Getter @Setter ProtectionModule instance;
	public ProtectionModule() {
		setInstance(this);
		KingdomFactionsPlugin.getInstance().registerListener(new MoveListener());
		KingdomFactionsPlugin.getInstance().registerListener(new PlayerShearEventListener());
		KingdomFactionsPlugin.getInstance().registerListener(new PlayerLeashEventListener());
		KingdomFactionsPlugin.getInstance().registerListener(new PlayerInteractEventListener());
		KingdomFactionsPlugin.getInstance().registerListener(new PlayerBucketEmptyEventListener());
		KingdomFactionsPlugin.getInstance().registerListener(new EntityDamageByEntityEventListener());
		KingdomFactionsPlugin.getInstance().registerListener(new BuildEventListener());
	}

    
    

	public Location blockLocation(Location block) {
		Location loc = new Location(block.getWorld(), block.getX(), 0, block.getZ());
		return loc;
	}
	
	public void updateTerritory(KingdomFactionsPlayer p) {
	         String territory = getTerritoryId(p);
	         KingdomType kterritory = getKingdomTerritory(p);
	         if(kterritory != p.getKingdomTerritory()) {
	        	 p.setKingdomTerritory(kterritory);
			   Bukkit.getPluginManager().callEvent(new TerritoryUpdateEvent(p));
	         }
		   if(p.getTerritoryId() != territory) {
		   p.setTerritoryId(territory);
		   Bukkit.getPluginManager().callEvent(new TerritoryUpdateEvent(p));
		   }
		}
	public String getTerritoryId(KingdomFactionsPlayer p) {
		String territory = "~GEEN~";
             if(p.getLocation().getWorld() == Utils.getInstance().getMiningWorld()) {
            	 return "~MINING~";
             }
		   for(INexus n : NexusModule.getInstance().getNexuses()) {
			   if(n.getDistance(p) <= n.getProtectedRadius()) {
				   territory = n.getNexusId();
			   }
		   }
		return territory;
		}
	
	public String getTerritoryId(Location loc) {
		String territory = "~GEEN~";
             if(loc.getWorld() == Utils.getInstance().getMiningWorld()) {
            	 return "~MINING~";
             }
		   for(INexus n : NexusModule.getInstance().getNexuses()) {
			   if(n.getDistance(loc) <= n.getProtectedRadius()) {
				   territory = n.getNexusId();
			   }
		   }
		return territory;
		}
	public KingdomType getKingdomTerritory(KingdomFactionsPlayer p) {
		KingdomType type = KingdomType.GEEN;
		if(p.getLocation().getWorld() == Utils.getInstance().getMiningWorld()) {
			return type;
		}
		Location loc = Utils.getInstance().getNewLocation(p.getLocation());
		loc.setY(0);
		   for(Kingdom k : KingdomModule.getInstance().getKingdoms()) {
			   Location spawn = Utils.getInstance().getNewLocation(k.getNexus().getLocation());
			   spawn.setY(0);
			   if(spawn.distance(loc) < ConfigModule.getInstance().getFile(ConfigModule.CONFIG).getConfig().getInt("kingdom.total.protected_region")) {
					   type = k.getType();
			   }
		   }
		return type;
	
	
	}
	
	
	public KingdomType getKingdomTerritory(Location loc1) {
		KingdomType type = KingdomType.ERROR;
		Location loc = Utils.getInstance().getNewLocation(loc1);
		loc.setY(0);
		   for(Kingdom k : KingdomModule.getInstance().getKingdoms()) {
			   if(loc1.getWorld() == Utils.getInstance().getOverWorld()) {
			   Location spawn = Utils.getInstance().getNewLocation(k.getNexus().getLocation());
			   spawn.setY(0);
			   if(spawn.distance(loc) < ConfigModule.getInstance().getFile(ConfigModule.CONFIG).getConfig().getInt("kingdom.total.protected_region")) {
					   type = k.getType();
			   }
			   } else {
						if(k.getDistanceToMineSpawn(loc) <= 100) {
							type = k.getType();
						} else {
							type = KingdomType.GEEN;
						}
			   }
		   }
		return type;
	}
	
	public boolean canBuild(KingdomFactionsPlayer p, Location block) { 
		if(p.getSettingsProfile().hasAdminMode()) return true;
		if(block.getWorld() == Utils.getInstance().getMiningWorld()) {
		/**	for(Kingdom k : KingdomModule.getInstance().getKingdoms()) {
				if(k.getDistanceToMineSpawn(block) <= 100) {
					if(k == p.getKingdom()) {
						return true;
					} else {
					return false;
					}
				}
			}*/
			return true;
		}
		if(block.getBlockY() <= 30) {
			return false;
		}
		
		Location loc = Utils.getInstance().getNewLocation(block);
		loc.setY(0);
	   for(Kingdom k : KingdomModule.getInstance().getKingdoms()) {
		   Location spawn = Utils.getInstance().getNewLocation(k.getNexus().getLocation());
		   spawn.setY(0);
		   if(spawn.distance(loc) < ConfigModule.getInstance().getFile(ConfigModule.CONFIG).getConfig().getInt("kingdom.capital.protected_region")) {
			  if(k.getType() == KingdomType.GEEN) return false;
			   if(p.getKingdom().equals(k)) {
				   return true;
			   } 
			   
		   }
	   }
	   
	   INexus n = getCurrentNexus(Utils.getInstance().getNewLocation(block));
	    if(n == null) {

	    	return false;
	    }
		   
	   Faction f = null;
		
	   if(n instanceof Nexus){
		   Nexus ne = (Nexus) n;
		   if(!ne.hasOwner()) {
			   return false;
		   }
		   f = (Faction) n.getOwner();
	   }
		   
		    
	   if(f == null) {
		   return false;
		   	}
		   if(p.hasFaction()) {
			   if(p.getFaction() == f) {
		
				   return true;
			   } else {
			
				   return false;
			   }
		   }
	   

	return false;
	}
	
	public boolean tryInfluence(KingdomFactionsPlayer p, int influence, Block b) {
		if(b.getLocation().getBlockY() <= 30) {
			p.sendMessage(ChatColor.RED + "Je mag geen blokken onder Y 30 breken!");
			return false;
		}
		return this.tryInfluence(p, influence);
	}
	public boolean tryInfluence(KingdomFactionsPlayer p,  int influence) {
		if(p.getSettingsProfile().hasIgnoreInfluence()) {
			p.sendMessage(ChatColor.DARK_RED + "Je negeerde " + ChatColor.RED + influence +ChatColor.DARK_RED + " influence bij deze actie!");
	       return true;
		}
		if(p.getSettingsProfile().hasAdminMode()) return true;
		if(p.getKingdomTerritory().equals(p.getKingdom())) return true;
		if(p.getTerritoryId().equalsIgnoreCase("~MINING~")) return true;
		if(p.getKingdom().getType() == KingdomType.GEEN || p.getKingdom().getType() == KingdomType.ERROR) return true;
	 //  if(WarModule.getInstance().isWarActive()) {
		   if(p.getStatisticsProfile().canAffordInfluence(influence)) {
			   p.getStatisticsProfile().removeInfluence(influence, false);
			   p.sendMessage(ChatColor.RED + "Deze actie kostte je " + ChatColor.GRAY + influence + ChatColor.RED+ " influence!");
			   return true;
		   } else {
			   p.sendMessage(ChatColor.RED + "Je hebt niet genoeg influence voor deze actie!");
			   return false;
		   }
	//   } else {
	//	p.sendMessage(ChatColor.RED + "Je hebt geen toestemming om deze actie uit te voeren!");
	//	return false;
	//}
	}
	
	
	public INexus getCurrentNexus(Location location) {
		return NexusModule.getInstance().getINexus(this.getTerritoryId(location));
	}
}
