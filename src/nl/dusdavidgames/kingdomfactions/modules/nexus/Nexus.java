package nl.dusdavidgames.kingdomfactions.modules.nexus;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.configuration.ConfigModule;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.NexusDatabase;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.monster.IGuard;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings.Building;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildingType;
import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.ProtectionModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.detection.TerritoryUpdateEvent;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.NexusType;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;

public class Nexus implements INexus{

	private @Getter String ownerId;
	private @Getter String tempOwner;
	private @Getter String nexusId;
	private @Getter @Setter Location nexusLocation;
	private @Getter @Setter int health;
	private @Getter @Setter int level;
	private @Getter int protectedRadius;
	private @Getter KingdomType kingdom;
	private @Getter @Setter Location pasteLocation;

	private @Getter ArrayList<Building> buildings = new ArrayList<Building>();

	private @Getter ArrayList<IGuard> guards = new ArrayList<IGuard>();

	public Nexus(int health, String nexus_id, Location paste, String f, int x, int y, int z, int level, World world, boolean protect) {
		this.ownerId = f;
		this.nexusLocation = new Location(world, x, y, z);
		this.pasteLocation = paste;
		this.protectedRadius = ConfigModule.getInstance().getFile(ConfigModule.CONFIG).getConfig()
				.getInt("nexus.protected_region");
		this.nexusId = nexus_id;
		this.health = health;
		this.level = level;
		if(this.level == 0) {
			this.level = 1;
		}
		this.protect = protect;
		this.kingdom = ProtectionModule.getInstance().getKingdomTerritory(nexusLocation);
	}

	public void setOwner(Faction f) {
		if (f == null) {
			this.ownerId = "Wilderness";
		} else {
		this.ownerId = f.getFactionId();
		}
		NexusDatabase.getInstance().updateOwner(ownerId, nexusId);
		this.regenerate();
		for(KingdomFactionsPlayer player : PlayerModule.getInstance().getPlayers()) {
			if(player.getCurrentNexus() == this) {
				   Bukkit.getPluginManager().callEvent(new TerritoryUpdateEvent(player));
			}
		}

	
	}

	public void regenerate() {
		this.health = 600;
	}

	
	private boolean protect;
	
	
	public boolean isProtected() {
		return protect;
	}
	public void setProtected(boolean protect) {
		this.protect = protect;
		this.save();
	}
	public void save() {
		updateLocation();

		if (NexusDatabase.getInstance().check(this.nexusId)) {
			NexusDatabase.getInstance().save(this);
		} else {
			NexusDatabase.getInstance().createNexus(this.nexusId, this.ownerId, this.nexusLocation.getBlockX(),
					this.nexusLocation.getBlockY(), this.nexusLocation.getBlockZ(),
					this.nexusLocation.getWorld().getName(), this.pasteLocation.getBlockX(),
					this.pasteLocation.getBlockY(), this.pasteLocation.getBlockZ());
		}

		if (!NexusModule.getInstance().getNexuses().contains(this)) {
			NexusModule.getInstance().getNexuses().add(this);
	
		}
	}

	private void updateLocation() {

		int range = 40;
		Location loc = getPasteLocation();

		boolean result = false;

		
		int currentDistanceToPasteLocation = 1000;

		for (int x = (loc.getBlockX() - range); x <= (loc.getBlockX() + range); x++) {
			for (int y = (loc.getBlockY() - range); y <= (loc.getBlockY() + range); y++) {
				for (int z = (loc.getBlockZ() - range); z <= (loc.getBlockZ() + range); z++) {
					result = true;
					Block block = new Location(loc.getWorld(), x, y, z).getBlock();
					if (block.getType().equals(Material.COAL_BLOCK)) {
				
						Block block2 = Utils.getInstance().getNewLocation(block.getLocation()).subtract(0, 1, 0).getBlock();
						if (block2.getType().equals(Material.FENCE) || block2.getType().equals(Material.COBBLE_WALL)) {
			
							
							Location pos1 = block.getLocation().clone();
							pos1.setY(0);
							
							Location pos2 = getPasteLocation().clone();
							pos2.setY(0);
							
							int distance = (int) pos1.distance(pos2);
							
							if(distance < currentDistanceToPasteLocation){
								setNexusLocation(block.getLocation());
								currentDistanceToPasteLocation = distance;
							
							}					
						}
					}
				}
			}
		}

		if (!result) {
			for (int x = (loc.getBlockX() + range); x <= (loc.getBlockX() - range); x++) {
				for (int y = (loc.getBlockY() + range); y <= (loc.getBlockY() - range); y++) {
					for (int z = (loc.getBlockZ() + range); z <= (loc.getBlockZ() - range); z++) {
						result = true;
						Block block = new Location(loc.getWorld(), x, y, z).getBlock();
						if (block.getType().equals(Material.COAL_BLOCK)) {
					
							Block block2 = block.getLocation().clone().subtract(0, 1, 0).getBlock();
							if (block2.getType().equals(Material.FENCE) || block2.getType().equals(Material.COBBLE_WALL)) {
							
								
								Location pos1 = Utils.getInstance().getNewLocation(block.getLocation());
								pos1.setY(0);
								
								Location pos2 = Utils.getInstance().getNewLocation(getPasteLocation());
								pos2.setY(0);
								
								int distance = (int) pos1.distance(pos2);
								
								if(distance < currentDistanceToPasteLocation){
									setNexusLocation(block.getLocation());
									currentDistanceToPasteLocation = distance;
								
								}					
							}
						}
					}
				}
			}
		}
		
		if (!result) {
			for (int x = (loc.getBlockX() + range); x <= (loc.getBlockX() - range); x--) {
				for (int y = (loc.getBlockY() + range); y <= (loc.getBlockY() - range); y--) {
					for (int z = (loc.getBlockZ() + range); z <= (loc.getBlockZ() - range); z--) {
						result = true;
						Block block = new Location(loc.getWorld(), x, y, z).getBlock();
						if (block.getType().equals(Material.COAL_BLOCK)) {
					
							Block block2 = block.getLocation().clone().subtract(0, 1, 0).getBlock();
							if (block2.getType().equals(Material.FENCE) || block2.getType().equals(Material.COBBLE_WALL)) {
				
								
								Location pos1 = Utils.getInstance().getNewLocation(block.getLocation());
								pos1.setY(0);
								
								Location pos2 = Utils.getInstance().getNewLocation(getPasteLocation());
								pos2.setY(0);
								
								int distance = (int) pos1.distance(pos2);
								
								if(distance < currentDistanceToPasteLocation){
									setNexusLocation(block.getLocation());
									currentDistanceToPasteLocation = distance;
							
								}					
							}
						}
					}
				}
			}
		}
		
		if (!result) {
			for (int x = (loc.getBlockX() - range); x <= (loc.getBlockX() + range); x--) {
				for (int y = (loc.getBlockY() - range); y <= (loc.getBlockY() + range); y--) {
					for (int z = (loc.getBlockZ() - range); z <= (loc.getBlockZ() + range); z--) {
						result = true;
						Block block = new Location(loc.getWorld(), x, y, z).getBlock();
						if (block.getType().equals(Material.COAL_BLOCK)) {
			
							Block block2 = block.getLocation().clone().subtract(0, 1, 0).getBlock();
							if (block2.getType().equals(Material.FENCE) || block2.getType().equals(Material.COBBLE_WALL)) {
						
								Location pos1 = Utils.getInstance().getNewLocation(block.getLocation());
								pos1.setY(0);
								
								Location pos2 = Utils.getInstance().getNewLocation(getPasteLocation());
								pos2.setY(0);
								
								int distance = (int) pos1.distance(pos2);
								
								if(distance < currentDistanceToPasteLocation){
									setNexusLocation(block.getLocation());
									currentDistanceToPasteLocation = distance;
							
								}					
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private int getPos(int x, int range) {
		if (x > 0) {
			return x + range;
		} else
			return x - range;
	}

	@SuppressWarnings("unused")
	private int getPos2(int x, int range) {
		if (x < 0) {
			return x + range;
		} else
			return x - range;
	}

	public BuildLevel getLevel(int i) {
		for (BuildLevel l : BuildLevel.values()) {
			if (l.getLevel() == i) {
				return l;
			}
		}
		return null;
	}

	public double getDistance(KingdomFactionsPlayer p) {
		Location player = Utils.getInstance().getNewLocation(p.getLocation());
		Location nexus = Utils.getInstance().getNewLocation(this.getPasteLocation());
		player.setY(0);
		nexus.setY(0);
		return nexus.distance(player);
	}

	public double getDistance(Location location) {
		Location loc = Utils.getInstance().getNewLocation(location);
		Location nexus = Utils.getInstance().getNewLocation(this.getPasteLocation());
		loc.setY(0);
		nexus.setY(0);
		return nexus.distance(loc);
	}

	public Building getBuilding(BuildingType t) {
		for (Building b : buildings) {
			if (b.getType() == t) {
				return b;
			}
		}
		return null;
	}

	public boolean hasBuilding(BuildingType t) {
		return getBuilding(t) != null;
	}

	public Faction getOwner() {
		return FactionModule.getInstance().getFaction(ownerId);
	}

	@Override
	public NexusType getType() {
		return NexusType.FACTION;
	}

	public boolean hasOwner() {
		if (this.ownerId == "Wilderness") {
			return false;
		}
		if(getOwner() == null) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return Utils.getInstance().getLocationString(this.nexusLocation) + "/" + this.nexusId;
	}

	
	public void delete() {
		Logger.INFO.log("Destroying Nexus...");
		Logger.INFO.log("Nexus ID: " + this.nexusId);
		Logger.INFO.log("Nexus Level: " + this.level);
		Logger.INFO.log("Nexus Owner: " + this.ownerId);
		Logger.INFO.log("Location: "  + Utils.getInstance().getLocationString(this.getPasteLocation()));
		for(Building b : this.buildings) {
			Logger.INFO.log("Building Type: " + b.getType());
			Logger.INFO.log("  Building Level: " + b.getLevel());
			Logger.INFO.log("  Location: "  + Utils.getInstance().getLocationString(b.getPasteLocation()));
		}
       MySQLModule.getInstance().insertQuery("DELETE FROM KingdomFactions.building WHERE nexus_id='" + this.nexusId + "'");
       MySQLModule.getInstance().insertQuery("DELETE FROM KingdomFactions.nexus WHERE nexus_id='"+this.nexusId+"'");
       this.buildings.clear();
       for(IGuard g : this.guards) {
    	   g.kill();
       }
       NexusModule.getInstance().getNexuses().remove(this);
	}
	}
