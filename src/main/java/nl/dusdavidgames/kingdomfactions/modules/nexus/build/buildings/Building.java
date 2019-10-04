package nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.BuildingDatabase;
import nl.dusdavidgames.kingdomfactions.modules.exception.nexus.BuildingException;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildingType;
import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.ProtectionModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;

public @Data class Building {

	private Location location;
	private BuildingType type;
	private Nexus nexus;
	private BuildLevel level;
	private KingdomType style;

	private @Getter @Setter Location pasteLocation;

	public Building(Location location, Location paste, BuildingType type, Nexus nexus, BuildLevel level) {
	
		this.location = location;
		this.type = type;
		this.nexus = nexus;
		this.level = level;

		this.style = ProtectionModule.getInstance().getKingdomTerritory(nexus.getPasteLocation());
		this.pasteLocation = paste;
		// Messages.getInstance().debug("Set paste location to " +
		// Utils.getInstance().getLocationString(paste));
	}

	public Building(Location location, Location paste, BuildingType type, Nexus nexus, BuildLevel level,
			KingdomType style) {
		this.location = location;
		this.type = type;
		this.nexus = nexus;
		this.level = level;
		this.style = style;
		this.pasteLocation = paste;
		// Messages.getInstance().debug("Set paste location to " +
		// Utils.getInstance().getLocationString(paste));
	}

	public Building(BuildingType type, Location paste, Nexus nexus, BuildLevel level) {
		this.type = type;
		this.nexus = nexus;
		this.level = level;
			this.style = ProtectionModule.getInstance().getKingdomTerritory(nexus.getPasteLocation());
	
		this.pasteLocation = paste;
//		Messages.getInstance().debug("Set paste location to " + Utils.getInstance().getLocationString(paste));
	}

	public double getDistance(KingdomFactionsPlayer p) {
		Location player = Utils.getInstance().getNewLocation(p.getLocation());
		Location nexus = Utils.getInstance().getNewLocation(this.getLocation());
		player.setY(0);
		nexus.setY(0);
		return nexus.distance(player);
	}

	public double getDistance(Location location) {
		Location loc = Utils.getInstance().getNewLocation(location);
		Location nexus = Utils.getInstance().getNewLocation(this.getLocation());
		loc.setY(0);
		nexus.setY(0);
		return nexus.distance(loc);
	}

	public void save() {
		updateLocation();

		if (BuildingDatabase.getInstance().check(this.nexus.getNexusId(), type)) {
			BuildingDatabase.getInstance().save(this);
		} else {
			BuildingDatabase.getInstance().createBuilding(this.nexus.getNexusId(), this.type, 1, this.location,
					this.pasteLocation);
		}
	}

	public BuildAction upgrade(KingdomFactionsPlayer player) throws BuildingException {

		int i = BuildLevel.getLevel(getLevel());
		int a = i + 1;
		if (a <= 8) {
			this.level = BuildLevel.getLevel(a);
			return new BuildAction(this, player);
		} else {
			throw new BuildingException("Building is already max level!");
		}

	}

	// public Shop getShop() {
	// return
	// ShopModule.getInstance().getShop(ShopLevel.getLevel(this.level.getLevel()),
	// this.type);
	// }

	void updateLocation() {

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
						Block block2 = block.getLocation().clone().subtract(0, 1, 0).getBlock();
						if (block2.getType().equals(Material.FENCE) || block2.getType().equals(Material.COBBLE_WALL)) {

							Location pos1 = block.getLocation().clone();
							pos1.setY(0);

							Location pos2 = getPasteLocation().clone();
							pos2.setY(0);

							int distance = (int) pos1.distance(pos2);

							if (distance < currentDistanceToPasteLocation) {
								setLocation(block.getLocation());
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
							if (block2.getType().equals(Material.FENCE)
									|| block2.getType().equals(Material.COBBLE_WALL)) {

								Location pos1 = block.getLocation().clone();
								pos1.setY(0);

								Location pos2 = getPasteLocation().clone();
								pos2.setY(0);

								int distance = (int) pos1.distance(pos2);

								if (distance < currentDistanceToPasteLocation) {
									setLocation(block.getLocation());
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
							if (block2.getType().equals(Material.FENCE)
									|| block2.getType().equals(Material.COBBLE_WALL)) {

								Location pos1 = block.getLocation().clone();
								pos1.setY(0);

								Location pos2 = getPasteLocation().clone();
								pos2.setY(0);

								int distance = (int) pos1.distance(pos2);

								if (distance < currentDistanceToPasteLocation) {
									setLocation(block.getLocation());
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
							if (block2.getType().equals(Material.FENCE)
									|| block2.getType().equals(Material.COBBLE_WALL)) {

								Location pos1 = block.getLocation().clone();
								pos1.setY(0);

								Location pos2 = getPasteLocation().clone();
								pos2.setY(0);

								int distance = (int) pos1.distance(pos2);

								if (distance < currentDistanceToPasteLocation) {
									setLocation(block.getLocation());
									currentDistanceToPasteLocation = distance;
								}
							}
						}
					}
				}
			}
		}
	}

	public BuildLevel getLevel(int i) {
		for (BuildLevel l : BuildLevel.values()) {
			if (l.getLevel() == i) {
				return l;
			}
		}
		return null;
	}

	
}
