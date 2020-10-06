package nl.dusdavidgames.kingdomfactions.modules.kingdom;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.utils.IRank;
import org.bukkit.ChatColor;

public enum KingdomRank implements IRank{

	SPELER(""),
	WACHTER(ChatColor.GRAY + "[" + ChatColor.YELLOW + "Wachter" + ChatColor.GRAY + "] "),
	KONING(ChatColor.GRAY + "[" + ChatColor.GOLD + "Koning" + ChatColor.GRAY + "] ");
	
	private @Getter String prefix;
	KingdomRank(String prefix) {
		this.prefix = prefix;
	}
	
	
	public static String getRank(KingdomRank rank) {
		return rank.toString().toLowerCase();
	}
	
	public static KingdomRank getRank(String rank) {
	    for(KingdomRank k : KingdomRank.values()) {
	    	if(k.toString().equalsIgnoreCase(rank)) {
	    		return k;
	    	}
	    }
		return SPELER;//to prevent errors (original null by freek)
	}
}
