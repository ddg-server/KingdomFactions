package nl.dusdavidgames.kingdomfactions.modules.chat.framework;

import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ChannelType;

public interface IChannelTypeHolder {

	
	
	public default ChannelType getChannelType() {
		return ChannelType.CUSTOM;
	}
}
