package nl.dusdavidgames.kingdomfactions.modules.war.command;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.SubCommand;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.war.WarModule;
import org.bukkit.ChatColor;

public class WarCommand extends KingdomFactionsCommand {

	public WarCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		this.registerSub(new SubCommand("start", "kingdomfactions.command.war.start", "Start de oorlog!") {

			@Override
			public void execute(String[] args) {
				try {
					WarModule.getInstance().start(Integer.valueOf(getArgs()[1]));
					broadcast(PlayerModule.getInstance().getPlayer(getSender()).getFormattedName()
							+ ChatColor.YELLOW + " is een oorlog begonnen!");
				} catch (NumberFormatException e) {
					getSender().sendMessage(Messages.getInstance().getPrefix() + "Gelieve geldige nummers gebruiken!");
				}

			}
		});
		this.registerSub(new SubCommand("stop", "kingdomfactions.command.war.stop", "Stop de oorlog!") {

			@Override
			public void execute(String[] args) {
				if (WarModule.getInstance().isWarActive()) {
					WarModule.getInstance().getWar().end();
					broadcast(PlayerModule.getInstance().getPlayer(getSender()).getFormattedName()
							+ ChatColor.YELLOW + " heeft de oorlog gestopt!");
				} else {
					getSender().sendMessage(Messages.getInstance().getPrefix() + "Er is op dit moment geen oorlog!");
				}

			}
		});
	}

	@Override
	public void execute() {
		return;

	}

}
