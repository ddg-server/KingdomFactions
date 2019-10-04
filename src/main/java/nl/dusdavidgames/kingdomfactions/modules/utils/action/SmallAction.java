package nl.dusdavidgames.kingdomfactions.modules.utils.action;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.data.DataList;
import nl.dusdavidgames.kingdomfactions.modules.data.DataManager;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public abstract class SmallAction implements IAction {

	private @Getter String name;
	private KingdomFactionsPlayer player;

	public SmallAction(String name, KingdomFactionsPlayer player) {
		this.player = player;
		this.name = name;
	}

	@Override
	public KingdomFactionsPlayer getPlayer() {
		return player;
	}

	private @Getter DataManager additionalData = new DataManager(new DataList());

}
