package nl.dusdavidgames.kingdomfactions.modules.war.command;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.SubCommand;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.war.WarModule;

public class OorlogCommand extends KingdomFactionsCommand {

	public OorlogCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		this.registerSub(
				new SubCommand("info", "kingdomfactions.command.oorlog.info", "Verkrijg informatie over de oorlog!") {

					@Override
					public void execute(String[] args) {
						for (Kingdom k : KingdomModule.getInstance().getKingdoms()) {
							if (k.getType() == KingdomType.ERROR || k.getType() == KingdomType.GEEN)
								continue;
							getPlayer().sendMessage(k.getType().getPrefix() + k.getType().getColor() + " "
									+ WarModule.getInstance().getWar().getSoldiers(k)
									+ displayKingdomInfo(k, getPlayer()));
						}
						

					}
				});
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	private String displayKingdomInfo(Kingdom k, KingdomFactionsPlayer p) {
		if (p.hasPermission("kingdomfactions.command.oorlog.info.extra")) {
			return "/" + k.getMembers();
		}
		return "";
	}

}
