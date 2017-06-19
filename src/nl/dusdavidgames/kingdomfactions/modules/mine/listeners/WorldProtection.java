package nl.dusdavidgames.kingdomfactions.modules.mine.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;

public class WorldProtection implements Listener {

	public void onExlode(EntityExplodeEvent event) {
		if(event.getLocation().getWorld() == Utils.getInstance().getMiningWorld()) {
		event.setCancelled(true);
		}
	}
}
