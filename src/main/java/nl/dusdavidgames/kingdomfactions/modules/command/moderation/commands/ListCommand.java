package nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.PlayerDatabase;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;

public class ListCommand extends KingdomFactionsCommand {

	public ListCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		int i = PlayerDatabase.getInstance().getRegisteredPlayers();
		getSender().sendMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "-------------------------");
		getSender().sendMessage("Geregistreerde spelers: " + i);
		getSender().sendMessage("Online spelers: " + PlayerModule.getInstance().getPlayers().size() + "/" + Bukkit.getServer().getMaxPlayers());
		getSender().sendMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "-------------------------");
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
