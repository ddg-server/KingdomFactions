package nl.dusdavidgames.kingdomfactions.modules.pvplog;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.action.IAction;

public class LogoutAction implements IAction {

	public LogoutAction(KingdomFactionsPlayer player) {
		this.player = player;
		this.logoutSeconds = 10;
	}
	private KingdomFactionsPlayer player;
	
	@Override
	public KingdomFactionsPlayer getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}

	@Override
	public void execute() throws KingdomFactionsException {
		getPlayer().getCombatTracker().disconnect();
		
	}
	
	@Getter int logoutSeconds;

	public boolean mayLogOut() {
		return getLogoutSeconds() == 1 || getLogoutSeconds() < 1;
	}
}
