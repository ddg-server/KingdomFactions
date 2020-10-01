package nl.dusdavidgames.kingdomfactions.modules.chat.framework;

import nl.dusdavidgames.kingdomfactions.modules.utils.IRank;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ChannelType;
import org.bukkit.ChatColor;

/**
 * To be used as MetaDataValue for players for a specific channel.
 */
public interface ChannelRank {


	public ChannelType getChannelType();
	
	public String getRawName();
	
	public String getName();

	default public ChatColor getMessageColour(){
		return ChatColor.GRAY;
	}

	default public boolean canTalk(){
		return true;
	}

	default public boolean canKick() {
		return false;
	}

	default public boolean canMute(){
		return false;
	}

	default public boolean canInvite(){
		return false;
	}

	default public boolean canBan(){
		return false;
	}

	default public boolean canEditMotd(){
		return false;
	}

	default public boolean canSetRank(){
		return false;
	}
	
	default public boolean canSetMode(){
		return false;
	}

	default public boolean canSetPassword(){

		return false;
	}

	default public boolean canDelete(){
		return false;
	}

	default public boolean showName(){
		return true;
	}
	
	default public int getCooldown(){
		return 5;
	}	
	
	default public boolean canPromoteToOwner() {
		return false;
	}
	
	default public void recalcPrefix(IRank rank) {
	return;
	}

}
