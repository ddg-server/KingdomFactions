package nl.dusdavidgames.kingdomfactions.modules.kingdom.eventhandlers.select;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class DropEventListener implements Listener{
	
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
		if(p.getKingdom().getType() == KingdomType.GEEN) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
    public void onFood(FoodLevelChangeEvent e) {
		KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(e.getEntity());
		if(player != null) {
			if(player.getKingdom().getType() == KingdomType.GEEN) {
				e.setFoodLevel(20);
			}
			
		}
	}

}
