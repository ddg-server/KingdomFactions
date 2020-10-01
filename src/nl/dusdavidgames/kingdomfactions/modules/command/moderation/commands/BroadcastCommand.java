package nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class BroadcastCommand extends KingdomFactionsCommand {



	public BroadcastCommand(String name, String permission, String info, String usage, boolean sub,
			boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		String message = "";
		for (String part : getArgs()) {
			if (message.isEmpty())
				message += " ";
			message += part;
		}
		Bukkit.broadcastMessage(ChatColor.BLACK + "[" + ChatColor.DARK_RED +"BROADCAST" + ChatColor.BLACK + "] " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', message));
		broadcast(PlayerModule.getInstance().getPlayer(getSender()).getFormattedName() + ChatColor.YELLOW + " broadcasted: " + message);
	}

	@Override
	public void init() {
		//not in use
	}
}