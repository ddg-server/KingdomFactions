package nl.dusdavidgames.kingdomfactions.modules.data.types;

import java.util.ArrayList;

import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.data.Data;

public class ArrayData extends Data {

	public ArrayData(String key, ArrayList<String> list) {
		super(key);
		this.value = list;

	}

	private @Setter ArrayList<String> value;

	public ArrayList<String> getValue() {
		return value;
	}
}
