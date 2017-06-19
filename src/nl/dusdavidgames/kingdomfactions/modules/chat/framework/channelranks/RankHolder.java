package nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.ChatProfile;

public class RankHolder {

	public RankHolder(ChatProfile profile, String channel, ChannelRank rank) {
		this.profile = profile;
		this.channel = channel;
		this.rank = rank;
	}

	private @Getter ChatProfile profile;
	private @Getter String channel;
	private @Getter ChannelRank rank;
	
	
	public void remove() {
		profile.getHolders().remove(this);
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{name=" + this.profile.getPlayer().getName() + ", channel=" + channel + ", rank=" + rank.getRawName() + "}"; 
	}
}
