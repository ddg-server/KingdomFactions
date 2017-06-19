package nl.dusdavidgames.kingdomfactions.modules.utils.enums;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.data.Data;
import nl.dusdavidgames.kingdomfactions.modules.data.types.ArrayData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.BooleanData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.DoubleData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.FloatData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.IntegerData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.LongData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.ObjectData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.StringData;

public enum DataType {

	INT(IntegerData.class),
	STRING(StringData.class),
	BOOLEAN(BooleanData.class),
	FLOAT(FloatData.class),
	DOUBLE(DoubleData.class),
	OBJECT(ObjectData.class),
	LONG(LongData.class),
	LIST(ArrayData.class);
	
	DataType(Class<? extends Data> clazz) {
		this.clazz = clazz;
	}
	private @Getter Class<? extends Data> clazz;
}
