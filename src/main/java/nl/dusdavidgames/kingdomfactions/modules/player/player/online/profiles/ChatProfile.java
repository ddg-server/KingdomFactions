package nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.event.player.AsyncPlayerChatEvent;

import io.netty.channel.ChannelException;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.chat.ChatModule;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelList;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.PasswordAttemptSession;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.DDGStaffChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.FactionChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.KingdomChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.RankHolder;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.SpeakerChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels.KingdomChannel;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.ChannelNotFoundException;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class ChatProfile extends Profile {

	public ChatProfile(KingdomFactionsPlayer player) {
		super(player);
	}

	private @Getter @Setter ChatChannel current;
	public @Getter ArrayList<RankHolder> holders = new ArrayList<RankHolder>();

	private @Getter @Setter KingdomFactionsPlayer replyPlayer;

	public ChannelList getChannels() {
		ChannelList channels = new ChannelList();
		for (ChatChannel c : ChatModule.getInstance().getChannels()) {
			if (c.getJoinedPlayers().contains(this.player)) {
				channels.add(c);
			}
		}
		return channels;
	}

	public void addChannel(ChatChannel channel) {
		channel.join(this.player, false);
	}

	public void removeChannel(ChatChannel channel) {
		channel.leave(this.player);
	}

	public void setRank(ChannelRank rank, ChatChannel channel) {
		if (hasRank(channel)) {

		}
		this.holders.add(new RankHolder(this, channel.getName(), rank));

	}

	public void removeRank(ChatChannel channel) {
		RankHolder holder = getRankHolder(channel);
		if (holder != null) {
			getRankHolder(channel).remove();
		}
	}

	public ChannelRank getRank(ChatChannel channel) {
		RankHolder rank = getRankHolder(channel);
		if (rank != null) {
			return rank.getRank();
		}
		return null;
	}

	public RankHolder getRankHolder(ChatChannel c) {
		for (RankHolder h : getHolders()) {
			if (c.getName().equalsIgnoreCase(h.getChannel())) {
				return h;
			}
		}
		return null;
	}

	public boolean hasRank(ChatChannel channel) {
		return getRank(channel) != null;
	}

	public boolean isTypingPassword() {
		return player.getAction() instanceof PasswordAttemptSession;
	}

	public boolean hasJoinedChannel(ChatChannel c) {
		return c.getJoinedPlayers().contains(this.getPlayer());
	}

	public boolean mayJoinChannel(ChatChannel c) {
		return c.getWhitelist().contains(this.getPlayer().getUuid());
	}

	public void wipeChannels() {
		try {
			ChatModule.getInstance().getChannels().forEach(channel -> channel.leave(getPlayer(), false, false));
	        ChatModule.getInstance().getChannels().forEach(channel -> channel.disAllow(player));
			getHolders().removeAll(getHolders());
			ChatChannel radius = ChatModule.getInstance().getChannelByName("Radius");

			KingdomChannel kingdom = (KingdomChannel) ChatModule.getInstance()
					.getChannelByName(player.getKingdom().getName());

			radius.allow(player);
			radius.join(player, false);
			kingdom.allow(player);
			kingdom.join(player, false);
			if (player.isStaff()) {
				player.getChatProfile()
						.setRank(new DDGStaffChannelRank(new KingdomChannelRank(player.getKingdomRank())), kingdom);
				player.getChatProfile().setRank(new DDGStaffChannelRank(new SpeakerChannelRank()), radius);
				if (player.hasFaction()) {
					player.getFaction().getChannel().allow(player);
					player.getFaction().getChannel().join(player, false);
					player.getChatProfile().setRank(
							new DDGStaffChannelRank(new FactionChannelRank(player.getFactionRank())),
							player.getFaction().getChannel());
				}
			} else {
				player.getChatProfile().setRank(new KingdomChannelRank(player.getKingdomRank()), kingdom);
				player.getChatProfile().setRank(new SpeakerChannelRank(), radius);
				if (player.hasFaction()) {
					player.getFaction().getChannel().allow(player);
					player.getFaction().getChannel().join(player, false);
					player.getChatProfile().setRank(new FactionChannelRank(player.getFactionRank()),
							player.getFaction().getChannel());
				}
			}
			player.getChatProfile().setCurrent(kingdom);
		} catch (ChannelException | ChannelNotFoundException e) {
			e.printStackTrace();
		}

	}

	private Queue<String> lastChats = new LinkedList<String>();

	public boolean mayChat(AsyncPlayerChatEvent e) {
		if (this.player.hasCooldown("chatcooldown")) {
			if (this.player.hasPermission("kingdomfactions.chat.nocooldown") || this.player.isStaff()) {
				this.player.removeCooldown("chatcooldown");
			} else {
				this.player.sendMessage(getChatCooldownMessage(this.player.getCooldown("chatcooldown").getCooldown()));
				e.setCancelled(true);
				return false;
			}
		}
		if (this.player.hasPermission("kingdomfactions.chat.nofilter") || this.player.isStaff()) {
			lastChats.clear();
			return true;
		}
		if (lastChats.contains(e.getMessage())) {
			this.player.sendMessage(ChatColor.RED + "Spammen is niet toegestaan.");
			e.setCancelled(true);
			return false;
		} else {
			if (lastChats.size() > 4) {
				lastChats.poll();
			}
			lastChats.add(e.getMessage());
			return true;
		}

	}

	public boolean mayUseMsg(String message) {
		if (this.player.hasCooldown("chatcooldown")) {
			if (this.player.hasPermission("kingdomfactions.chat.nocooldown") || this.player.isStaff()) {
				this.player.removeCooldown("chatcooldown");
			} else {
				this.player.sendMessage(getChatCooldownMessage(this.player.getCooldown("chatcooldown").getCooldown()));
				return false;
			}
		}
		if (this.player.hasPermission("kingdomfactions.chat.nofilter") || this.player.isStaff()) {
			lastChats.clear();
			return true;
		}
		if (lastChats.contains(message)) {
			this.player.sendMessage(ChatColor.RED + "Spammen is niet toegestaan.");

			return false;
		} else {
			if (lastChats.size() > 4) {
				lastChats.poll();
			}
			lastChats.add(message);
			return true;
		}
	}

	private String getChatCooldownMessage(int i) {
		if (i == 1) {
			return ChatColor.RED + "Je moet nog " + ChatColor.GRAY + "1 seconde" + ChatColor.RED + " wachten";
		} else {
			return ChatColor.RED + "Je moet nog " + ChatColor.GRAY + i + " seconden" + ChatColor.RED + " wachten";
		}
	}
}
