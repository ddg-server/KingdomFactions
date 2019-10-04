package nl.dusdavidgames.kingdomfactions.modules.data.types;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.data.Data;

public class DoubleData extends Data {

	
	
	public DoubleData(String key, double value) {
		super(key);
		this.value = value;

	}

	private @Getter @Setter double value;
}
