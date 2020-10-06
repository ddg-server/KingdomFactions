package nl.dusdavidgames.kingdomfactions.modules.command.time;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.player.player.offline.OfflineKingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.time.TimeHelper;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import org.bukkit.command.CommandSender;

public class TimeCommand extends KingdomFactionsCommand {



	public TimeCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws UnkownPlayerException {
       CommandSender sender = getSender();
		if(getArgs().length == 1) {
		     if(!getPlayer().isStaff()) {
		    	 getPlayer().sendMessage(Messages.getInstance().getNoPerm());
		    	 return;
		     }
				IPlayerBase base = PlayerModule.getInstance().getPlayerBase(getArgs()[0]);
			if(base instanceof KingdomFactionsPlayer) {
				KingdomFactionsPlayer player = PlayerModule.getInstance().getOnlinePlayer(base);
				int[] time = TimeHelper.getInstance().translateTime(player.getStatisticsProfile().getSecondsConnected());
				sender.sendMessage(ChatColor.RED + "--------------------");
				sender.sendMessage(ChatColor.RED + "Naam: " + player.getName());
				sender.sendMessage(ChatColor.RED + "Status: " + ChatColor.GREEN + "Online");
				sender.sendMessage(ChatColor.RED + "Dagen: " + time[0]);
				sender.sendMessage(ChatColor.RED + "Uren: " + time[1]);
				sender.sendMessage(ChatColor.RED + "Minuten: " + time[2]);
				sender.sendMessage(ChatColor.RED + "Seconden: " + time[3]);
		        sender.sendMessage(ChatColor.RED + "--------------------");
				} else if(base instanceof OfflineKingdomFactionsPlayer) {
				OfflineKingdomFactionsPlayer player = PlayerModule.getInstance().getOfflinePlayer(base);
				int[] time = player.getPlaytime();
				sender.sendMessage(ChatColor.RED + "--------------------");
				sender.sendMessage(ChatColor.RED + "Naam: " + player.getName());
				sender.sendMessage(ChatColor.RED + "Status: " + ChatColor.DARK_RED + "Offline");
				sender.sendMessage(ChatColor.RED + "Dagen: " + time[0]);
				sender.sendMessage(ChatColor.RED + "Uren: " + time[1]);
				sender.sendMessage(ChatColor.RED + "Minuten: " + time[2]);
				sender.sendMessage(ChatColor.RED + "Seconden: " + time[3]);
				sender.sendMessage(ChatColor.RED + "--------------------");
			}
		} else {
			KingdomFactionsPlayer player = getPlayer();
			int[] time = TimeHelper.getInstance().translateTime(player.getStatisticsProfile().getSecondsConnected());
			sender.sendMessage(ChatColor.RED + "--------------------");
			sender.sendMessage(ChatColor.RED + "Naam: " + player.getName());
			sender.sendMessage(ChatColor.RED + "Dagen: " + time[0]);
			sender.sendMessage(ChatColor.RED + "Uren: " + time[1]);
			sender.sendMessage(ChatColor.RED + "Minuten: " + time[2]);
			sender.sendMessage(ChatColor.RED + "Seconden: " + time[3]);
			sender.sendMessage(ChatColor.RED + "--------------------");
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
