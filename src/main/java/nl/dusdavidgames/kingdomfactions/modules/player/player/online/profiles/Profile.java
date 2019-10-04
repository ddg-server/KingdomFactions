package nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class Profile {

	
	public Profile(KingdomFactionsPlayer player) {
		this.player = player;
	}
	
	
	@Getter
	protected KingdomFactionsPlayer player;
}
