package nl.dusdavidgames.kingdomfactions.modules.utils;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;

public class Messages {

	private static @Getter @Setter Messages instance;
	private @Getter String prefix = ChatColor.GRAY + "[" + ChatColor.RED + "KingdomFactions" + ChatColor.GRAY + "] "
			+ ChatColor.WHITE;
	private @Getter String noPerm = getPrefix() + ChatColor.RED + "Je hebt onvoldoende rechten voor deze actie!";
    private @Getter String warning = ChatColor.DARK_RED + "KINGDOMFACTIONS" + ChatColor.RED +""+ ChatColor.BOLD + ">> " + ChatColor.RED;
	public Messages() {
		setInstance(this);
	}
 


}
