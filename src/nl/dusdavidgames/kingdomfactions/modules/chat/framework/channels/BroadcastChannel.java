package nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels;

import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ChannelType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BroadcastChannel extends ChatChannel {

	//by inheritance, the joinpermission for the broadcast channel is "kingdom.channel.joinpermission.Broadcast"

	public BroadcastChannel() {
		super(ChatColor.GREEN + "Broadcast", null, true);
		motd.clear();
		motd.add(ChatColor.WHITE + "Welkom in het BroadcastChannel!");
		motd.add(ChatColor.WHITE + "Let op: alles wat je hier zegt wordt");
		motd.add(ChatColor.WHITE + "naar de hele server gebroadcast!");
	}

	@Override
	public void message(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		message(PlayerModule.getInstance().getPlayer(event.getPlayer()), event.getMessage());
	}

	@Override
	public void message(KingdomFactionsPlayer player, String message) {
		Bukkit.broadcastMessage(
				colouredName + " " + ChatColor.RESET +
						player.getName() + ": " +
						message
		);
	}

	@Override
	public void join(KingdomFactionsPlayer player) {
		join(player, false, true);
	}

	@Override
	public ChannelType getChannelType() {
		return ChannelType.ANY;
	}

}
