package nl.dusdavidgames.kingdomfactions.modules.chat.listeners;

import nl.dusdavidgames.kingdomfactions.modules.chat.events.ChannelSwitchEvent;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.DDGStaffChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.FactionChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.KingdomChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels.FactionChannel;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels.KingdomChannel;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels.RadiusChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.ChatProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChannelSwitchEventListener implements Listener {

	@EventHandler
	public void onSwitch(ChannelSwitchEvent e) {

		if(!e.getChannel().hasRank(e.getByWho())) {
		
		 ChatProfile profile  = e.getByWho().getChatProfile();
			if(!e.getByWho().isStaff()) {
				if(e.getChannel() instanceof FactionChannel) {
					  profile.setRank(new FactionChannelRank(profile.getPlayer().getFactionRank()), e.getChannel());
					} else if(e.getChannel() instanceof KingdomChannel) {
						  profile.setRank(new KingdomChannelRank(profile.getPlayer().getKingdomRank()), e.getChannel());
								
					} else if(e.getChannel() instanceof RadiusChannel) {
						  profile.setRank(new KingdomChannelRank(profile.getPlayer().getKingdomRank()), e.getChannel());
							
					} else {
						  profile.setRank(e.getChannel().getDefaultRank(), e.getChannel());
							
					}
			} else {
				if(e.getChannel() instanceof FactionChannel) {
				  profile.setRank(new DDGStaffChannelRank(new FactionChannelRank(profile.getPlayer().getFactionRank())), e.getChannel());
				} else if(e.getChannel() instanceof KingdomChannel) {
					  profile.setRank(new DDGStaffChannelRank(new KingdomChannelRank(profile.getPlayer().getKingdomRank())), e.getChannel());
							
				} else if(e.getChannel() instanceof RadiusChannel) {
					  profile.setRank(new DDGStaffChannelRank(new KingdomChannelRank(profile.getPlayer().getKingdomRank())), e.getChannel());
						
				} else {
					  profile.setRank(new DDGStaffChannelRank(e.getChannel().getDefaultRank()), e.getChannel());
						
				}
			}
		}
	} 
}
