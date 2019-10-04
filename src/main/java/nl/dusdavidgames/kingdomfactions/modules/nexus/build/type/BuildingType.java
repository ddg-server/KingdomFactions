package nl.dusdavidgames.kingdomfactions.modules.nexus.build.type;

import org.bukkit.Material;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.configuration.ConfigModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;

public enum BuildingType {
	NEXUS(1, Material.COAL_BLOCK),
	BLACKSMITH(2, Material.ANVIL),
	BAKERY(3, Material.BREAD),
	MAGETOWER(4, Material.ENCHANTMENT_TABLE),
	FORGE(5, Material.FURNACE);
	
	private @Getter int level;
	private @Getter Material material;
	
	BuildingType(int level, Material m) {
	  this.level = level;
	  this.material = m;
	}
	
	
	public  static BuildingType getType(Material m) {
		switch(m) {
		case BREAD:
			return BAKERY;
		case FURNACE:
			return FORGE;
		case ENCHANTMENT_TABLE:
			return MAGETOWER;
		case COAL_BLOCK:
			return NEXUS;
		case ANVIL:
			return BLACKSMITH;
		default:
			return NEXUS;
		}

	}
	public String getName(BuildingType type) {
		switch(type) {
		case BAKERY:
		    return "Bakery";
		case BLACKSMITH:
		    return "Blacksmith";
		case FORGE:
			return "Forge";
		case MAGETOWER:
			return "Magetower";
		case NEXUS:
			return "Nexus";
		
		}
		return "Nexus";
	}
	
	
	public int getPrice(KingdomType type, BuildLevel level) {
		return ConfigModule.getInstance().getFile(ConfigModule.PRICES).getConfig()
				.getInt("price." + type.toString() + "." + this.toString() + "_" + level.getLevel());
	}
}
