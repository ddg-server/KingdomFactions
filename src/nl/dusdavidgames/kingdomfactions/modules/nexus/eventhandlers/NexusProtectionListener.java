package nl.dusdavidgames.kingdomfactions.modules.nexus.eventhandlers;

import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class NexusProtectionListener implements Listener {

	@EventHandler
	public void onPiston(BlockPistonExtendEvent e) {

		if (NexusModule.getInstance().containsNexus((e.getBlocks()))) {
		e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPiston(BlockPistonRetractEvent e) {

		if (NexusModule.getInstance().containsNexus((e.getBlocks()))) {
		e.setCancelled(true);
		}
	}
	@EventHandler
	public void onExplode(BlockExplodeEvent e) {
		if (NexusModule.getInstance().containsNexus((e.blockList()))) {
		e.setCancelled(true);
		}
	}
	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		List<Location> blocks = Utils.getInstance().drawCircle(e.getLocation(), 10, 10, false, true, 0);
		if(NexusModule.getInstance().containsNexusLocation(blocks)) {
			e.setCancelled(true);
		}
        
			
		}
	}

