package nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks;

import org.bukkit.ChatColor;

import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ChannelType;

public class AdminChannelRank implements ChannelRank {

	@Override
	public String getName() {
		return ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "Admin" + ChatColor.GRAY + "]";
	}

	public ChatColor getMessageColour() {
		return ChatColor.WHITE;
	}

	public boolean showName() {
		return true;
	}

	public boolean canKick() {
		return true;
	}

	public boolean canMute() {
		return true;
	}

	public boolean canBan() {
		return true;
	}

	public boolean canInvite() {
		return true;
	}

	public boolean canEditMotd() {
		return true;
	}

	public boolean canSetRank() {
		return true;
	}

	public int getCooldown() {
		return 0;
	}

	@Override
	public String getRawName() {
		// TODO Auto-generated method stub
		return "ADMIN";
	}

	@Override
	public ChannelType getChannelType() {
		// TODO Auto-generated method stub
		return ChannelType.CUSTOM;
	}

}
