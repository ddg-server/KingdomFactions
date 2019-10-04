package nl.dusdavidgames.kingdomfactions.modules.utils;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class Cooldown {
	
	
	public Cooldown(String key, KingdomFactionsPlayer player, int cooldown) {
		this.key = key;
		this.player = player;
		this.cooldown = cooldown;
	}
	private @Getter String key;
	private @Getter KingdomFactionsPlayer player;
	private @Getter int cooldown;
	
	
	
	public void lower() {
		if(cooldown <= 0) {
			this.cancel(); 
		} else {
			cooldown--;
		}
	}
	
	public void cancel() {
		this.player.removeCooldown(this.key);
	}
}
