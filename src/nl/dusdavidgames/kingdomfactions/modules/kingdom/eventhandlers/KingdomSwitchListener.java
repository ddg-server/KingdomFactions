package nl.dusdavidgames.kingdomfactions.modules.kingdom.eventhandlers;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.event.KingdomSwitchEvent;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class KingdomSwitchListener implements Listener {

	@EventHandler
	public void onSwitch(KingdomSwitchEvent e) {
	   e.getPlayer().getChatProfile().wipeChannels();
		KingdomFactionsPlayer p = e.getPlayer();
		Faction f = e.getPlayer().getFaction();
		if (f != null) {
			p.getScoreboard().editLine(8, ChatColor.GRAY + "[" +p.getKingdom().getType().getColor()+ f.getName() + ChatColor.GRAY + "]");
		} else {
			p.getScoreboard().editLine(8, p.getKingdom().getType().getColor() + "Geen Faction!");
		}
		for (KingdomFactionsPlayer t : PlayerModule.getInstance().getPlayers()) {
			t.getScoreboard().refreshTags();
		}
	}
}
