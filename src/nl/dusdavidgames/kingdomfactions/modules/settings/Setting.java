package nl.dusdavidgames.kingdomfactions.modules.settings;

import lombok.Getter;
import lombok.Setter;

public enum Setting {

	BURGER_OORLOG(true),
	ALLOW_HOME(true),
	ALLOW_SPAWN(true),
	USE_DEATHBAN(true);

	private Setting(boolean defaultState) {
		this.enabled = defaultState;
	}
	private @Setter @Getter boolean enabled;
	
}
