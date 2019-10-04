package nl.dusdavidgames.kingdomfactions.modules.data;

import lombok.Getter;

public abstract class Data {

	public Data(String key) {
		this.key = key;

	}

	private @Getter String key;

}
