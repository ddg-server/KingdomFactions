package nl.dusdavidgames.kingdomfactions.modules.player;

import java.util.ArrayList;

import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class PlayerList extends ArrayList<KingdomFactionsPlayer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4017062724445242490L;

	
	
	public void broadcast(String message) {
		for(KingdomFactionsPlayer t : this) {
			t.sendMessage(message);
		}
	}
	
}
