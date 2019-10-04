package nl.dusdavidgames.kingdomfactions.modules.utils.action;

import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public interface IAction {

	
	public KingdomFactionsPlayer getPlayer();
	
	@Deprecated
	public void execute() throws KingdomFactionsException;

	default public void cancel() {
		getPlayer().setAction(null);
	}
}
