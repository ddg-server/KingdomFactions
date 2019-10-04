package nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.player.player.console.CommandUser;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;

public class InfoCommand extends KingdomFactionsCommand{

	public InfoCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() throws KingdomFactionsException {
		IPlayerBase base = PlayerModule.getInstance().getPlayerBase(getArgs()[0]);
		
		CommandUser user = getUser();
		
		user.sendMessage(ChatColor.BLUE +""+ ChatColor.STRIKETHROUGH + "---------------------");
	    user.sendMessage(ChatColor.BLUE + "Naam: " + ChatColor.WHITE + base.getName());
	    user.sendMessage(ChatColor.BLUE + "UUID: " + ChatColor.WHITE + base.getUuid());
	    user.sendMessage(ChatColor.BLUE + "Status: " + ChatColor.WHITE +getOnline(base));
	    if(base.hasFaction()) {
	    user.sendMessage(ChatColor.BLUE + "Faction: ");
	    user.sendMessage(ChatColor.BLUE + "  Naam: " + ChatColor.WHITE +base.getFaction().getName());
	    user.sendMessage(ChatColor.BLUE + "  Faction Rank: " + ChatColor.WHITE +base.getFactionRank() + "");
	    user.sendMessage(ChatColor.BLUE + "  ID: " + ChatColor.WHITE +base.getFaction().getFactionId());
	    user.sendMessage(ChatColor.BLUE + "  Home: " +ChatColor.WHITE +getHome(base.getFaction()));
	    }
	    user.sendMessage(ChatColor.BLUE + "Kingdom: " + ChatColor.WHITE +base.getKingdom().getType().getPrefix());
	    user.sendMessage(ChatColor.BLUE + "Kingdom Rank: " + ChatColor.WHITE +base.getKingdomRank() + "");
	    int[] time = base.getPlaytime(); 
	    user.sendMessage(ChatColor.BLUE + "Coins: " + ChatColor.WHITE + base.getCoins());
	    user.sendMessage(ChatColor.BLUE + "Influence: " +ChatColor.WHITE + base.getInfluence());

	    user.sendMessage(ChatColor.BLUE + "Speeltijd: " + ChatColor.WHITE + time[0] + " dagen, " + time[1] + " uren, " + time[2] + " minuten, " + time[3] + " seconden");
		user.sendMessage(ChatColor.BLUE +""+ ChatColor.STRIKETHROUGH + "---------------------");
	} 

	
	private String getOnline(IPlayerBase base) {
		if(base.isOnline()) {
			return ChatColor.GREEN + "Online";
		} else {
			return ChatColor.RED + "Offline";
		}
	}
	
	private String getHome(Faction f) {
		if(f.hasHome()) {
			return  Utils.getInstance().getLocationString(f.getHome().getLocation());
		} 
		return "Geen home gezet!";
		}
}
