package nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks;

import org.bukkit.ChatColor;

import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.utils.IRank;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ChannelType;

public class DDGStaffChannelRank implements ChannelRank {

	private ChannelRank wrappedRank;

	public DDGStaffChannelRank(ChannelRank wrapped){
		wrappedRank = wrapped;
	}

	@Override
	public int getCooldown(){
		return 0;
	}

	@Override
	public ChatColor getMessageColour(){
		return ChatColor.WHITE;
	}
	
	@Override
	public boolean canDelete(){
		return true;
	}
	
	@Override
	public boolean canKick(){
		return true;
	}
	
	@Override
	public boolean canInvite(){
		return true;
	}
	
	@Override
	public boolean canEditMotd(){
		return wrappedRank.canEditMotd();
	}
	
	@Override
	public boolean canSetRank(){
		return wrappedRank.canEditMotd();
	}
	
	@Override
	public boolean canSetPassword(){
		return wrappedRank.canSetPassword();
	}
	
	@Override
	public boolean showName(){
		return wrappedRank.showName();
	}
	
	@Override
	public String getName() {
		return wrappedRank.getName();
	}

	@Override
	public String getRawName() {
		// TODO Auto-generated method stub
		return "DDG STAFF";
	}
	
	@Override
	public ChannelType getChannelType() {
		// TODO Auto-generated method stub
		return ChannelType.ANY;
	}

	
	@Override
	public boolean canPromoteToOwner() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void recalcPrefix(IRank rank) {
		this.wrappedRank.recalcPrefix(rank);
	}
}
