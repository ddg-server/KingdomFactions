package nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.gamemode;

import org.bukkit.GameMode;

public enum KingdomFactionsGameMode {

	CREATIVE,
	ADVENTURE,
	SURVIVAL,
	SPECTATOR;
	
	
	public static KingdomFactionsGameMode getGamemodeOfString(String gamemode) {
		switch(gamemode.toLowerCase()) {
		case "c":
		  return CREATIVE;
		case "crea":
		  return CREATIVE;
		case "creative":
	      return CREATIVE;
		case "a":
		  return ADVENTURE;
		case "adventure":
		  return ADVENTURE;
		case "2":
		  return ADVENTURE;
		case "1":
			return CREATIVE;
		case "0":
			return SURVIVAL;
		case "s":
			return SURVIVAL;
		case "surv":
			return SURVIVAL;
		case "survival":
			return SURVIVAL;
		case "spec":
			return SPECTATOR;
		case "spectator":
			return SPECTATOR;
		case "3":
			return SPECTATOR;
                 	
		}
		return SURVIVAL;
	}
	public static GameMode getBukkitGameMode(KingdomFactionsGameMode c) {
		switch(c) {
		case ADVENTURE:
			return GameMode.ADVENTURE;
		case CREATIVE:
			return GameMode.CREATIVE;
		case SPECTATOR:
			return GameMode.SPECTATOR;
		case SURVIVAL:
			return GameMode.SURVIVAL;
		default:
			return GameMode.SURVIVAL;
		
		}
	}
}
