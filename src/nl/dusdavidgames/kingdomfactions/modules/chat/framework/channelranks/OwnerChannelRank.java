package nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks;

import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ChannelType;
import org.bukkit.ChatColor;

public class OwnerChannelRank implements ChannelRank {

	@Override
	public String getName() {
		return ChatColor.GRAY + "[" + ChatColor.DARK_RED + "Owner" + ChatColor.GRAY + "]";
	}
	
	@Override
	public ChatColor getMessageColour(){
		return ChatColor.WHITE;
	}

	public boolean canKick() {
		return true;
	}

	public boolean canMute(){
		return true;
	}

	public boolean canInvite(){
		return true;
	}

	public boolean canBan(){
		return true;
	}

	public boolean canEditMotd(){
		return true;
	}

	public boolean canSetRank(){
		return true;
	}

	public boolean canSetMode(){
		return true;
	}

	public boolean canSetPassword(){
		return true;
	}

	public boolean canDelete(){
		return true;
	}

	public boolean showName(){
		return true;
	}

	public int getCooldown(){
		return 0;
	}

	@Override
	public String getRawName() {
		// TODO Auto-generated method stub
		return "OWNER";
	}
	@Override
	public ChannelType getChannelType() {
		// TODO Auto-generated method stub
		return ChannelType.CUSTOM;
	}
	
	@Override
	public boolean canPromoteToOwner() {
		// TODO Auto-generated method stub
		return true;
	}
}
