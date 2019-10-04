package nl.dusdavidgames.kingdomfactions.modules.pvplog;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.CombatTracker;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class LogoutCommand extends KingdomFactionsCommand {

	public LogoutCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() throws KingdomFactionsException {
		CombatTracker tracker = getPlayer().getCombatTracker();
		
		if(tracker.isInCombat()) {
			if(getPlayer().hasAction()) {
				if(getPlayer().getAction() instanceof LogoutAction) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je bent momenteel al aan het uitloggen!");
					return;
				}
				}
			getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Over 20 seconden, zullen wij veilig de verbinding verbreken. Let op! Blijf stil staan! anders stopt het proces!");
			getPlayer().setAction(new LogoutAction(getPlayer()));
			} else {
			getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je bent momenteel niet in een gevecht! Je kan zelf uitloggen!");
		}
	}

}
