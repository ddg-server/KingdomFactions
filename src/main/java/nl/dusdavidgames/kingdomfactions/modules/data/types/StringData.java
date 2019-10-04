package nl.dusdavidgames.kingdomfactions.modules.data.types;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.data.Data;

public class StringData extends Data {

	
	
	public StringData(String key, String value) {
		super(key);
		this.value = value;
	}
	
	private @Getter @Setter String value;
}
