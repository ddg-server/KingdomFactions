package nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FlyCommand extends KingdomFactionsCommand{





	public FlyCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		Player p = (Player) getSender();
        p.setAllowFlight(!p.getAllowFlight());
        p.sendMessage(Messages.getInstance().getPrefix() + "Vliegmodus staat nu " + (p.getAllowFlight() ? "aan": "uit")+ "!");
        broadcast(PlayerModule.getInstance().getPlayer(p).getFormattedName()  + ChatColor.YELLOW + " heeft vliegmodus " + (p.getAllowFlight() ? "aan": "uit")+ "gezet!" );
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
