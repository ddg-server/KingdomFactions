package nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks;

import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ChannelType;

public class ListenerChannelRank implements ChannelRank{

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean canTalk() {
		return false;
	}
	
	@Override
	public boolean showName() {
		return false;
	}

	@Override
	public String getRawName() {
		// TODO Auto-generated method stub
		return "LISTENER";
	}
	@Override
	public ChannelType getChannelType() {
		// TODO Auto-generated method stub
		return ChannelType.CUSTOM;
	}
}
