package nl.dusdavidgames.kingdomfactions.modules.data.helper;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.data.Data;
import nl.dusdavidgames.kingdomfactions.modules.data.DataList;
import nl.dusdavidgames.kingdomfactions.modules.data.types.ArrayData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.BooleanData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.DoubleData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.FloatData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.IntegerData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.LongData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.ObjectData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.StringData;
import nl.dusdavidgames.kingdomfactions.modules.exception.DataException;

public class DataMethods {

	public DataMethods(DataList data) {
		this.dataList = data;
	}

	protected @Getter DataList dataList;

	public boolean contains(String key) {
		return getData(key) != null;
	}

	protected Data getData(String key) {
		for (Data s : dataList) {
			if (s.getKey().equalsIgnoreCase(key)) {
				return s;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param key
	 * @return String
	 * @throws DataException
	 */
	public StringData getStringData(String key) throws DataException {
		if (getData(key) == null) {
			throw new DataException("Data " + key + " does not exists!");
		}
		if (getData(key) instanceof StringData) {
			return (StringData) getData(key);
		} else {
			throw new DataException("Data " + key + " is not a valid StringData");
		}
	}

	/**
	 * 
	 * @param key
	 * @return float
	 * @throws DataException
	 */
	public FloatData getFloatData(String key) throws DataException {
		if (getData(key) == null) {
			throw new DataException("Data " + key + " does not exists!");
		}
		if (getData(key) instanceof FloatData) {
			return (FloatData) getData(key);
		} else {
			throw new DataException("Data " + key + " is not a valid FloatData");
		}
	}

	/**
	 * 
	 * @param key
	 * @return boolean
	 * @throws DataException
	 */
	public BooleanData getBooleanData(String key) throws DataException {
		if (getData(key) == null) {
			throw new DataException("Data " + key + " does not exists!");
		}
		if (getData(key) instanceof BooleanData) {
			return (BooleanData) getData(key);
		} else {
			throw new DataException("Data " + key + " is not a valid BooleanData");
		}
	}

	/**
	 * 
	 * @param key
	 * @return int
	 * @throws DataException
	 */
	public IntegerData getIntegerData(String key) throws DataException {
		if (getData(key) == null) {
			throw new DataException("Data " + key + " does not exists!");
		}
		if (getData(key) instanceof IntegerData) {
			return (IntegerData) getData(key);
		} else {
			throw new DataException("Data " + key + " is not a valid IntegerData");
		}
	}

	/**
	 * 
	 * @param key
	 * @return int
	 * @throws DataException
	 */
	public DoubleData getDoubleData(String key) throws DataException {
		if (getData(key) == null) {
			throw new DataException("Data " + key + " does not exists!");
		}
		if (getData(key) instanceof DoubleData) {

			return (DoubleData) getData(key);
		} else {
			throw new DataException("Data " + key + " is not a valid DoubleData");
		}
	}

	/**
	 * 
	 * @param key
	 * @return long
	 * @throws DataException
	 */
	public LongData getLongData(String key) throws DataException {
		if (getData(key) == null) {
			throw new DataException("Data " + key + " does not exists!");
		}
		if (getData(key) instanceof LongData) {

			return (LongData) getData(key);
		} else {
			throw new DataException("Data " + key + " is not a valid LongData");
		}
	}

	public ObjectData<?> getObjectData(String key) throws DataException {
		if (getData(key) == null) {
			throw new DataException("Data " + key + " does not exists!");
		}
		if (getData(key) instanceof ObjectData<?>) {
			return (ObjectData<?>) getData(key);
		} else {
			throw new DataException("Data " + key + " is not a valid ObjectData");
		}
	}

	public ArrayData getArrayData(String key) throws DataException {
		if (getData(key) == null) {
			throw new DataException("Data " + key + " does not exits!");
		}
		if (getData(key) instanceof ArrayData) {
			return (ArrayData) getData(key);
		} else {
			throw new DataException("Data " + key + " is not a valid ArrayData");
		}
	}
}
