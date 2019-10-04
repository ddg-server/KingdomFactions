package nl.dusdavidgames.kingdomfactions.modules.data.types;

import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.data.Data;

public class BooleanData extends Data {

	public BooleanData(String key, boolean value) {
		super(key);
		this.value = value;

	}

	private @Setter boolean value;

	public boolean getValue() {
		return value;
	}
}
