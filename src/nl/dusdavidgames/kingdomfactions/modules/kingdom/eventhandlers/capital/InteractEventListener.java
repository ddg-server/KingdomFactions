package nl.dusdavidgames.kingdomfactions.modules.kingdom.eventhandlers.capital;

import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.SetCapitalAction;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractEventListener implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null)
			return;
		if (e.getClickedBlock().getType() != Material.COAL_BLOCK)
			return;
		KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(e.getPlayer());
		if (player.hasAction()) {
			if (player.getAction() instanceof SetCapitalAction) {
				e.setCancelled(true);
				SetCapitalAction action = (SetCapitalAction) player.getAction();
				action.setLocation(e.getClickedBlock().getLocation());
				action.execute();
				player.sendMessage(Messages.getInstance().getPrefix() + "Je hebt de HoofdStad Nexus gezet!");
			}
		}
	}
}
