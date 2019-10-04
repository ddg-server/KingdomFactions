package nl.dusdavidgames.kingdomfactions.modules.data.types;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.data.Data;

public class ObjectData<E> extends Data {

	public ObjectData(String key, E oj) {
		super(key);
		this.value = oj;
	}
	
	private @Getter E value;

}
