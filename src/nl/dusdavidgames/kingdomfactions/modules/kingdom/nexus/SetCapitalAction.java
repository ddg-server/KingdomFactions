package nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.action.IAction;
import org.bukkit.Location;

public @Data class SetCapitalAction implements IAction {

	public SetCapitalAction(Kingdom kingdom, KingdomFactionsPlayer player) {
		this.kingdom = kingdom;
		this.player = player;
	}

	private Kingdom kingdom;

	private KingdomFactionsPlayer player;

	private @Getter @Setter Location location;

	@Override
	public void execute() {
		this.kingdom.getNexus().setLocation(location);
		this.kingdom.getNexus().save();
		cancel();

	}

	@Override
	public void cancel() {
		this.player.setAction(null);
	}
}
