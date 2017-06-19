package nl.dusdavidgames.kingdomfactions.modules.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.chat.commands.ChannelCommand;
import nl.dusdavidgames.kingdomfactions.modules.chat.commands.MsgCommandExecutor;
import nl.dusdavidgames.kingdomfactions.modules.chat.commands.ReplyCommandExecutor;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels.BroadcastChannel;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels.FactionChannel;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels.KingdomChannel;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels.RadiusChannel;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels.SpyChannel;
import nl.dusdavidgames.kingdomfactions.modules.chat.listeners.AsyncPlayerChatEventListener;
import nl.dusdavidgames.kingdomfactions.modules.chat.listeners.ChannelMessageEventListener;
import nl.dusdavidgames.kingdomfactions.modules.chat.listeners.ChannelPasswordTryEventListener;
import nl.dusdavidgames.kingdomfactions.modules.chat.listeners.ChannelSwitchEventListener;
import nl.dusdavidgames.kingdomfactions.modules.chat.listeners.ChatKingdomSwitchEventListener;
import nl.dusdavidgames.kingdomfactions.modules.chat.listeners.PrivateMessageEventListener;
import nl.dusdavidgames.kingdomfactions.modules.chat.listeners.RankChangeEventListener;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.ChannelNotFoundException;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.DuplicateChannelException;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ChannelType;

/**
 * @authors Jannyboy11, Steenooo
 *         <p>
 *        (almost) All classes in this module are designed and written by me^^
 * 
 */
public class ChatModule {

	public static final String CHAT_ALWAYS_PERMISSION = "kingdomfactions.chat.always";

	private static @Getter @Setter ChatModule instance;
	
	public @Getter ArrayList<ChatChannel> channels = new ArrayList<ChatChannel>();
	 
	
	public ChatModule() {
		setInstance(this);
		initChannels();
		new ChannelCommand("channel", "kingdomfactions.command.channel", "Channel commands", "channel [sub]", true, false).registerCommand();
		new ReplyCommandExecutor("reply", "kingdomfactions.command.reply", "Reageer op een Privé Bericht", "r [bericht]", false, false).registerCommand();
		new MsgCommandExecutor("msg", "kingdomfactions.command.msg", "Stuur een Privé Bericht", "msg [speler] [bericht]", false, false).registerCommand();
	    KingdomFactionsPlugin pl = KingdomFactionsPlugin.getInstance();
	    pl.registerListener(new AsyncPlayerChatEventListener());
	    pl.registerListener(new ChannelMessageEventListener());
	    pl.registerListener(new ChannelPasswordTryEventListener());
	    pl.registerListener(new ChatKingdomSwitchEventListener());
	    pl.registerListener(new PrivateMessageEventListener());
	    pl.registerListener(new ChannelSwitchEventListener());
	    pl.registerListener(new RankChangeEventListener());
	}
	


	/**
	 * initialises the persistent channels.
	 */
	private void initChannels() {
		// add the Radius channel
		try {
			addChatChannel(new RadiusChannel("RADIUS", null, true));
		} catch (DuplicateChannelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// add the Spy channel'
		SpyChannel spy = new SpyChannel();
		Bukkit.getPluginManager().registerEvents(spy, KingdomFactionsPlugin.getInstance());
		try {
			addChatChannel(spy);
		} catch (DuplicateChannelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// add the global roleplay and trade channels
	
		// add the kingdom channels
		for (KingdomType kingdom : KingdomType.values()) {
			if (kingdom == KingdomType.ERROR) continue;
			try {
				addChatChannel(new KingdomChannel(kingdom.getPrefix(),
						KingdomModule.getInstance().getKingdom(kingdom), ChannelType.KINGDOM));
			} catch (DuplicateChannelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(Faction f : FactionModule.getInstance().getFactions()) {
			try {
				addChatChannel(new FactionChannel(f, null, true));
			} catch (DuplicateChannelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//add the broadcast channel
		try {
			addChatChannel(new BroadcastChannel());
		} catch (DuplicateChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	/**
	 * Add an chat channel to the list of chat channels.
	 *
	 * @param channel the channel
	 * @throws DuplicateChannelException 
	 */
	public void addChatChannel(ChatChannel channel) throws DuplicateChannelException {
		if(channels.contains(channel)) {
			throw new DuplicateChannelException("Channel "+channel.getName()+" already exists!");
		}
		channels.add(channel);
	}

	/**
	 * Remove an channel from the list of chat channels.
	 *
	 * @param channel the channel
	 */
	
	
	public ChatChannel getRadiusChannel() {
		try {
			return getChannelByName("RADIUS");
		} catch (ChannelNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void removeChannel(ChatChannel channel) {
		channels.remove(channel);
	}

	/**
	 * Wipes channels
	 *
	 * @param removePersistentOnesAsWell selfexplanatory
	 */
	public void wipeChannels(boolean removePersistentOnesAsWell){
		if (removePersistentOnesAsWell) {
			for (KingdomFactionsPlayer player : PlayerModule.getInstance().getPlayers()) {
				ChatChannel.removeCurrent(player);
			}
			channels.clear();
		} else {
			List<ChatChannel> persistentChannels = channels.stream().filter(ChatChannel::isPersistent).collect(Collectors.toList());
			for (KingdomFactionsPlayer player : PlayerModule.getInstance().getPlayers()) {
				ChatChannel current = ChatChannel.getCurrent(player);
				if (!current.isPersistent()) {
					ChatChannel.removeCurrent(player);
				}
			}
			channels.clear();
			persistentChannels.forEach(channel -> addChatChannelIgnoreDuplicate(channel));
		}
	}

	/**
	 * Gets channel by name.
	 *
	 * @param channelName the channel name
	 * @return the channel by name
	 * @throws ChannelNotFoundException
	 */
	public ChatChannel getChannelByName(String channelName) throws ChannelNotFoundException {
		ChatChannel c = null;
		
		for(ChatChannel ch : channels) {
			if(ch.getName().equalsIgnoreCase(channelName)) {
				c = ch;
			}
		}
		if(c == null) {
			throw new ChannelNotFoundException("Unable to find channel " + channelName);
		}
		return c;
	}
	
	public ChatChannel getChannelById(String channelId) throws ChannelNotFoundException {
		ChatChannel c = null;
		for(ChatChannel ch : channels) {
			if(ch.getId().equalsIgnoreCase(channelId)) {
				c = ch;
			}
		}
		if(c == null) {
			throw new ChannelNotFoundException("Unable to find channel " + channelId);
		}
		return c; 
	}

	@Override public String toString() {
		return "ChatModule{" +
				"channels=" + channels +
				'}';
	}
	
	public void addChatChannelIgnoreDuplicate(ChatChannel channel) {
		this.channels.add(channel);
	}
}
