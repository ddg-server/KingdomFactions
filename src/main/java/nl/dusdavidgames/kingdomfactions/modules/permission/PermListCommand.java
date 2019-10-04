package nl.dusdavidgames.kingdomfactions.modules.permission;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.command.CommandModule;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.SubCommand;

public class PermListCommand extends KingdomFactionsCommand {

	public PermListCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
	}

	@Override
	public void init() {
	}

	@Override
	public void execute() {
		for (KingdomFactionsCommand cmd : CommandModule.getInstance().getCommand()) {
			if(cmd == this) continue;
			if (cmd.getSubCommands().isEmpty()) {
				getSender().sendMessage("/" + cmd.getName() + " - " + cmd.getPermission() + " - " + allowConsole(cmd.allowConsole()));
			} else {
				for (SubCommand s : cmd.getSubCommands()) {
					getSender().sendMessage(
							"/" + cmd.getName() + " " + s.getMainCommand() + " - " + s.getPermission() + " - " + allowConsole(s, cmd));
				}
			}
		}

	}

	private String allowConsole(boolean allow) {
		if (allow) {
			return ChatColor.GREEN + "CONSOLE";
		} else {
			return ChatColor.RED + "CONSOLE";
		}
	}

	private String allowConsole(SubCommand command, KingdomFactionsCommand supercmd) {
		if (supercmd.allowConsole()) {
			if (command.allowConsole()) {
				return ChatColor.GREEN + "CONSOLE";
			} else {
			}
		}
		return ChatColor.RED + "CONSOLE";
	}
}
