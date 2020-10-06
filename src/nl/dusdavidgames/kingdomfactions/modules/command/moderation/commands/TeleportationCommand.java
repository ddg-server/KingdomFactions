package nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import org.bukkit.ChatColor;

public class TeleportationCommand extends KingdomFactionsCommand {



	public TeleportationCommand(String name, String permission, String info, String usage, boolean sub,
			boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws UnkownPlayerException {
		KingdomFactionsPlayer t = PlayerModule.getInstance().getPlayer(getArgs()[0]);
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(getSender());
		if (t != null) {
          p.teleport(t.getLocation());
          p.sendMessage(Messages.getInstance().getPrefix() + "Je bent naar " + t.getName() + " geteleporteerd!");
		   broadcast(p.getFormattedName() + ChatColor.YELLOW +  " teleporteerde naar " + t.getFormattedName());
		} else {
			p.sendMessage(Messages.getInstance().getPrefix() + "Deze speler is niet online!");
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
