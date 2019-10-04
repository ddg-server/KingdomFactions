package nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands;

import org.bukkit.Location;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;

public class BackCommand extends KingdomFactionsCommand{

	public BackCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		if(getPlayer().getLastLocation() == null) {
			getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je kan momenteel niet terug keren naar jouw vorige locatie!");
			return;
		}
		
		Location loc = Utils.getInstance().getNewLocation(getPlayer().getLastLocation());
		getPlayer().sendMessage("Je bent geteleporteert naar je vorige locatie!");
		broadcast(getPlayer().getFormattedName() + ChatColor.YELLOW + " teleporteerde naar zijn vorige locatie (" + Utils.getInstance().getLocation(loc) + ")");
		getPlayer().setLastLocation(getPlayer().getLocation());
		getPlayer().teleport(loc);
	}

}
