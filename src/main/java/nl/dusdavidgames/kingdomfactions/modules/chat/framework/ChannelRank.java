package nl.dusdavidgames.kingdomfactions.modules.chat.framework;

import org.bukkit.ChatColor;

import nl.dusdavidgames.kingdomfactions.modules.utils.IRank;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ChannelType;

/**
 * To be used as MetaDataValue for players for a specific channel.
 */
public interface ChannelRank {

    ChannelType getChannelType();

    String getRawName();

    String getName();

    default ChatColor getMessageColour() {
        return ChatColor.GRAY;
    }

    default boolean canTalk() {
        return true;
    }

    default boolean canKick() {
        return false;
    }

    default boolean canMute() {
        return false;
    }

    default boolean canInvite() {
        return false;
    }

    default boolean canBan() {
        return false;
    }

    default boolean canEditMotd() {
        return false;
    }

    default boolean canSetRank() {
        return false;
    }

    default boolean canSetMode() {
        return false;
    }

    default boolean canSetPassword() {
        return false;
    }

    default boolean canDelete() {
        return false;
    }

    default boolean showName() {
        return true;
    }

    default int getCooldown() {
        return 5;
    }

    default boolean canPromoteToOwner() {
        return false;
    }

    default void recalcPrefix(IRank rank) {
    }

}
