package nl.dusdavidgames.kingdomfactions.modules.settings;

import lombok.Getter;
import lombok.Setter;

public enum Setting {

    BURGER_OORLOG(true),
    ALLOW_HOME(true),
    ALLOW_SPAWN(true),
    USE_DEATHBAN(true);

    Setting(boolean defaultState) {
        this.enabled = defaultState;
    }

    @Getter
	@Setter
    private boolean enabled;

}
