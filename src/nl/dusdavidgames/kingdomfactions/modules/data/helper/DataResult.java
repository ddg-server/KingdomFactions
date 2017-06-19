package nl.dusdavidgames.kingdomfactions.modules.data.helper;

import java.util.ArrayList;

import nl.dusdavidgames.kingdomfactions.modules.data.DataList;
import nl.dusdavidgames.kingdomfactions.modules.exception.DataException;

public class DataResult extends DataEditor {

	
	public DataResult(DataList data) {
		super(data);
	}

	public boolean getBoolean(String key) throws DataException {
		return getBooleanData(key).getValue();
	}

	public long getLong(String key) throws DataException {
	   return getLongData(key).getValue();
	}

	public int getInteger(String key) throws DataException {
		return getIntegerData(key).getValue();
	}

	public String getString(String key) throws DataException {
	 return getStringData(key).getValue();
	}

	public float getFloat(String key) throws DataException {
     return getFloatData(key).getValue();
	}
	public double getDouble(String key) throws DataException {
     return getDoubleData(key).getValue();
	}
	public ArrayList<String> getArrayList(String key) throws DataException {
		return getArrayData(key).getValue();
	}
}
