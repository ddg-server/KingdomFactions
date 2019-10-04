package nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles;

import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.GodMode;

public class SettingsProfile extends Profile {

	
	
	public SettingsProfile(KingdomFactionsPlayer player) {
	   super(player);
	}
	

	private @Setter boolean adminMode = false;
	private @Setter boolean ignoreInfluence = false;
	private @Setter GodMode godMode = GodMode.NOGOD;
	private @Setter boolean nightvision = false;
	private @Setter boolean spy = false;
	private @Setter boolean nexusIspect;
	
	
	public boolean hasSpy() {
		return spy;
	}
	public boolean hasNightVision() { 
		return nightvision;
	}
	public GodMode getGodMode() {
		return godMode;
	}
	public boolean hasIgnoreInfluence() {
		return ignoreInfluence;
	}
	public boolean hasAdminMode() {
		return adminMode;
	}
	
	public boolean hasNexusInspect() {
		return nexusIspect;
	}
	public KingdomFactionsPlayer getPlayer() {
		return player;
	}
}
