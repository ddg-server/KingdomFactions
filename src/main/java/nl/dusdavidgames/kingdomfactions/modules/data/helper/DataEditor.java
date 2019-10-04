package nl.dusdavidgames.kingdomfactions.modules.data.helper;

import java.util.ArrayList;

import nl.dusdavidgames.kingdomfactions.modules.data.DataList;
import nl.dusdavidgames.kingdomfactions.modules.exception.DataException;

public class DataEditor extends DataMethods {

	public DataEditor(DataList data) {
		super(data);
	}

	public void editData(String key, int value) {
		try {
			getIntegerData(key).setValue(value);
		} catch (DataException e) {
			e.printStackTrace();
		}
	}

	public void editData(String key, float value) {
		try {
			getFloatData(key).setValue(value);
		} catch (DataException e) {
			e.printStackTrace();
		}
	}

	public void editData(String key, long value) {
		try {
			getLongData(key).setValue(value);
		} catch (DataException e) {
			e.printStackTrace();
		}
	}

	public void editData(String key, boolean value) {
		try {
			getBooleanData(key).setValue(value);
		} catch (DataException e) {
			e.printStackTrace();
		}
	}

	public void editData(String key, String value) {
		try {
			getStringData(key).setValue(value);
		} catch (DataException e) {
			e.printStackTrace();
		}
	}

	public void editData(String key, double value) {
		try {
			getDoubleData(key).setValue(value);
		} catch (DataException e) {
			e.printStackTrace();
		}
	}

	public void editData(String key, ArrayList<String> value) {
		try {
			getArrayData(key).setValue(value);
		} catch (DataException e) {
			e.printStackTrace();

		}
	}
}
