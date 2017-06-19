package nl.dusdavidgames.kingdomfactions.modules.data;

import nl.dusdavidgames.kingdomfactions.modules.data.helper.DataResult;
import nl.dusdavidgames.kingdomfactions.modules.exception.DataException;

public class DataManager extends DataResult {

	
	public DataManager(DataList data) {
		super(data);
		
	}
	
	public void addData(Data data) throws DataException {
		if (this.dataList.contains(getData(data.getKey()))) {
			throw new DataException("Duplicate Data key! Data key already exists.");
		} else {
			this.dataList.add(data);
		}
	}

	public void removeData(Data data) {
		this.dataList.remove(data);
	}
	public void removeData(String key) {
          removeData(getData(key));
	}
	
}
