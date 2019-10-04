package nl.dusdavidgames.kingdomfactions.modules.data.types;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.data.Data;

public class FloatData extends Data {

	
	
	public FloatData(String key, float value) {
		super(key);
		this.value = value;

	}

	private @Getter @Setter float value;
}
