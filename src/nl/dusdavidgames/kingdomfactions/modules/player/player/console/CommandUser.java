package nl.dusdavidgames.kingdomfactions.modules.player.player.console;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;

public class CommandUser{

	
	
	public CommandUser(CommandSender sender) {
		this.sender = sender;
	}
	
	private CommandSender sender;
	
	public String getFormattedName() {
		if(isConsole()) {
		return ChatColor.DARK_RED  + "CONSOLE";
		}
		return PlayerModule.getInstance().getPlayer(sender).getFormattedName();
	}

	public boolean isConsole() {
		return sender instanceof ConsoleCommandSender;
	}


	public CommandSender getSender() {
		return sender;
	}

	public void sendMessage(String message) {
		this.sender.sendMessage(message);
	}

}
