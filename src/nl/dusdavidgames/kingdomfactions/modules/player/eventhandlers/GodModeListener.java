package nl.dusdavidgames.kingdomfactions.modules.player.eventhandlers;

import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodModeListener implements Listener{

	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(player);
			switch (p.getSettingsProfile().getGodMode()) {
			case FAKEDAMAGE:
				e.setDamage(0);
				p.heal();
				break;
			case NODAMAGE:
				e.setCancelled(true);
				break;
			case NOGOD:
				return;
			default:
				return;
			
			}
		}
	}
}
