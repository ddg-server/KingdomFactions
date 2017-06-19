package nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import nl.dusdavidgames.kingdomfactions.modules.chat.events.ChannelMessageEvent;
import nl.dusdavidgames.kingdomfactions.modules.chat.events.PrivateMessageEvent;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.DDGStaffChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ChannelType;

public class SpyChannel extends ChatChannel implements Listener {

	//by inheritance, the joinpermission for the spychannel is "kingdom2.channel.joinpermission.Spy"

	public SpyChannel() {
		super(ChatColor.BLUE + "Spy", null, true);
		
		setDefaultRank(new DDGStaffChannelRank(defaultRank));
		motd.clear();
		motd.add(ChatColor.WHITE + "Welkom in het SpyChannel!");
		motd.add(ChatColor.WHITE + "Hier komen alle channel messages en private messages binnen.");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onChannelMessage(ChannelMessageEvent event) {
		if (!event.isCancelled()) {
			getJoinedPlayers().stream().filter(spyer -> !event.getChannel().isJoined(spyer)).forEach(spyerNotInvolved -> whisperTo(spyerNotInvolved, event.getFormat()));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPrivateMessage(PrivateMessageEvent event) {
		if (!event.isCancelled()) {
			getJoinedPlayers().stream().filter(spyer -> (event.getSender() != spyer && event.getReceiver() != spyer)).forEach(spyerNotInvolved -> whisperTo(spyerNotInvolved, event.getFormat()));
		}
	}

	@Override
	public void join(KingdomFactionsPlayer player) {
		join(player, false, true);
	}

	@Override
	public void message(AsyncPlayerChatEvent event) {
	   event.getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je kan niet praten in het spy channel!");
	   KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(event.getPlayer());
	   event.setCancelled(true);
	   player.getChatProfile().setCurrent(player.getKingdom().getChannel());
	}
	@Override
	public ChannelType getChannelType() {
		// TODO Auto-generated method stub
		return ChannelType.ANY;
	}

}
