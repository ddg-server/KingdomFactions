package nl.dusdavidgames.kingdomfactions.modules.utils.enums;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.data.Data;
import nl.dusdavidgames.kingdomfactions.modules.data.types.*;

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
