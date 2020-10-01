package nl.dusdavidgames.kingdomfactions.modules.mine.listeners;

import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class WorldProtection implements Listener {

	public void onExlode(EntityExplodeEvent event) {
		if(event.getLocation().getWorld() == Utils.getInstance().getMiningWorld()) {
		event.setCancelled(true);
		}
	}
}
