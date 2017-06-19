package nl.dusdavidgames.kingdomfactions.modules.data.types;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.data.Data;

public class IntegerData extends Data {

	
	
	public IntegerData(String key, int value) {
		super(key);
		this.value = value;

	}

	private @Getter @Setter int value;
}
