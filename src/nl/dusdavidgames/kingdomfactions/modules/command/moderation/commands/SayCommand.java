package nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;

public class SayCommand extends KingdomFactionsCommand {

	public SayCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		String message = "";
		for (String part : getArgs()) {
			if (message != "")
				message += " ";
			message += part;
		}
		Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.GREEN +"Broadcast" + ChatColor.GRAY + "] "  + ChatColor.WHITE + getPlayerName() +": " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', message));
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	private String getPlayerName() {
		if(getSender() instanceof Player) {
			return getSender().getName();
		}
		return "CONSOLE";
	}
	

}
