package nl.dusdavidgames.kingdomfactions.modules.permission;

import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

import java.util.ArrayList;
import java.util.Iterator;

public class StaffList extends ArrayList<KingdomFactionsPlayer> {

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7549445362771048417L;

	public void broadcast(String message) {
		Iterator<KingdomFactionsPlayer> i = this.iterator();
		while(i.hasNext()) {
			i.next().sendMessage(message);
		}
	}
}
