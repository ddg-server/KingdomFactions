package nl.dusdavidgames.kingdomfactions.modules.faction.invite;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class Invite {

	private @Getter KingdomFactionsPlayer player;
	private @Getter KingdomFactionsPlayer inviter;
	private @Getter Faction targetFaction;

	public Invite(KingdomFactionsPlayer p, KingdomFactionsPlayer inviter, Faction targetFaction) {
		this.player = p;
		this.inviter = inviter;
		this.targetFaction = targetFaction;
	}


	public void unregister() {
		targetFaction.getInvites().remove(this);
	}

	public void sendClickableAcceptMessage() {
	 
		TextComponent c = new TextComponent();
		c.setText(Messages.getInstance().getPrefix() + "Klik hier om de uitnodiging te accepteren!");
		c.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
				new ComponentBuilder("Klik hier om de Faction " + ChatColor.RED + targetFaction.getName() + ChatColor.WHITE + " te joinen!").create()));
		c.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/faction join " + targetFaction.getName()));
		 PlayerModule.getInstance().getOnlinePlayer(player).getPlayer().spigot().sendMessage(c);
	}
}
