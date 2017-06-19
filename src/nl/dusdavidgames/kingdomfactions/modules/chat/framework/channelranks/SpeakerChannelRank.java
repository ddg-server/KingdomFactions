package nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks;

import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ChannelType;

public class SpeakerChannelRank implements ChannelRank {

	@Override
	public String getName() {
		return null;
	}
	
	@Override
	public boolean showName() {
		return false;
	}

	@Override
	public String getRawName() {
		// TODO Auto-generated method stub
		return "SPEAKER";
	}
	@Override
	public ChannelType getChannelType() {
		// TODO Auto-generated method stub
		return ChannelType.CUSTOM;
	}
}
