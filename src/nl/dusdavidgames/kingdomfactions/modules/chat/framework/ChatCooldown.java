package nl.dusdavidgames.kingdomfactions.modules.chat.framework;

import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Cooldown;

public class ChatCooldown extends Cooldown {

	public ChatCooldown(KingdomFactionsPlayer player, int cooldown) {
		super("chatcooldown", player, cooldown);
		// TODO Auto-generated constructor stub
	}

}
