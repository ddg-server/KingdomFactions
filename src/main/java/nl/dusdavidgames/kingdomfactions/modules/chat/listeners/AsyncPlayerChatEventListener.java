package nl.dusdavidgames.kingdomfactions.modules.chat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.chat.ChatModule;
import nl.dusdavidgames.kingdomfactions.modules.chat.events.ChannelPasswordTryEvent;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.PasswordAttemptSession;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class AsyncPlayerChatEventListener implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if(event.getFormat().contains("<" + event.getPlayer().getName() + ">")) {
			event.getPlayer().sendMessage(ChatColor.RED + "Er ging iets fout!");
			event.setCancelled(true);
			return;
		}
          KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(event.getPlayer());	
		//Players without a kingdom are not allowed to chat on Kingdom2 whatsoever!
		if (player.getKingdom().getType() == KingdomType.GEEN) {
			player.sendMessage(Messages.getInstance().getPrefix() + "Je moet eerst je Kingdom kiezen!");
			event.setCancelled(true);
			return;
		}

		//first, do a check whether a player wants to join a channel with a password.
		if (player.getChatProfile().isTypingPassword()) {
			PasswordAttemptSession session = (PasswordAttemptSession) player.getAction();
				session.setPassword(event.getMessage());
				ChannelPasswordTryEvent chanPassTryEvent = 
						new ChannelPasswordTryEvent(session, player);
				Bukkit.getPluginManager().callEvent(chanPassTryEvent);
				event.setCancelled(true);
		
			return;
		}
		
		if(!player.getChatProfile().mayChat(event)) {
			return;
		}

	if (event.isCancelled()) {
			return;
	}

		//percent signs are used for chat formatting, so they must be replaced.
		event.setMessage(event.getMessage().replaceAll("%", "\uFF05"));

		//check whether the message was a 'special' mesasge.
		//handle !roleplay, @private and $trade messages.
	
			if (handleSpecialMessage(event)) {
				return;
			}

		//if not, get the current channel the player is chatting in.
		ChatChannel channel = ChatChannel.getCurrent(player);
		
       	if(channel == ChatModule.getInstance().getRadiusChannel()) {
           ChatModule.getInstance().getRadiusChannel().message(event);   
       	}

		if (channel == null) {
	
			if(player.getKingdom().getType() == KingdomType.GEEN) {
				player.sendMessage(Messages.getInstance().getPrefix() + "Je zit momenteel niet in een Kingdom! Kies eerst een Kingdom!");
				event.setCancelled(true);
				return;
			}
			event.getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je zit momenteel niet in een chatkanaal.");
			event.getPlayer().sendMessage(ChatColor.WHITE + "Typ "  + "/channel join <KanaalNaam>" + " om te een kanaal te joinen");
			event.getPlayer().sendMessage(ChatColor.WHITE + "Typ "  + "/channel list"  + " om de lijst van kanalen te zien.");
			event.setCancelled(true);
		} else {
			channel.message(event);

		}
	}

	public boolean handleSpecialMessage(AsyncPlayerChatEvent event) {
		String message = event.getMessage();
		if (!(message.startsWith("!") || message.startsWith("@"))) {
			return false;
		}
		KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(event.getPlayer());

		String firstChar = message.substring(0, 1);
		message = message.substring(1);
		event.setMessage(message);

		switch (firstChar) {
		case "!":
			if (message.equals("")) { //if the player sent just the '!'-symbol.
				event.setCancelled(true);
				return false;
			}
            ChatModule.getInstance().getRadiusChannel().message(event);
			return true;
		case "@":
			if (message.equals("")) { //if the player sent just the '@'-symbol.
				event.setCancelled(true);
				return false;
			}
			String[] words = message.split("\\s");
			KingdomFactionsPlayer receiver = null;
			int i;
			if (words[0].equals("")) {
				try {
					receiver = PlayerModule.getInstance().getPlayer(words[1]);
				} catch (UnkownPlayerException e) {
					
					event.getPlayer().sendMessage(Messages.getInstance().getPrefix() + words[1] + " is niet online.");
					event.setCancelled(true);
				}
				i = 1;
			} else {
				try {
					receiver = PlayerModule.getInstance().getPlayer(words[0]);
				} catch (UnkownPlayerException e) {
					event.getPlayer().sendMessage(Messages.getInstance().getPrefix() + words[0] + " is niet online.");
					event.setCancelled(true);
				}
				i = 0;
			}
			if (receiver != null) {
				
				if(player.getKingdom() != receiver.getKingdom() && !player.isStaff()) {
					player.sendMessage(Messages.getInstance().getPrefix() + "Je kan alleen privé berichten sturen naar spelers in jouw kingdom!");
					 event.setCancelled(true);
					return false;
				}
				
				message = message.replaceFirst("\\s*" + words[i] + "\\s*", "");
			    player.sendPrivateMessage(receiver, message);
			    event.setCancelled(true);
			} else {
				event.getPlayer().sendMessage(Messages.getInstance().getPrefix() + words[i] + " is niet online.");
				event.setCancelled(true);
			}
			return true;
		}
		return false;
	}
	
	


}