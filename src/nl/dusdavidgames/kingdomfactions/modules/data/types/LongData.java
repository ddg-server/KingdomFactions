package nl.dusdavidgames.kingdomfactions.modules.data.types;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.data.Data;

public class LongData extends Data {

	
	
	public LongData(String key, long value) {
		super(key);
		this.value = value;

	}
	private @Getter @Setter long value;
}
