package nl.dusdavidgames.kingdomfactions.modules.data.types;

import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.data.Data;

import java.util.List;

public class ArrayData extends Data {

	private @Setter List<String> value;

	public ArrayData(String key, List<String> list) {
		super(key);
		this.value = list;
	}

	public List<String> getValue() {
		return value;
	}
}
