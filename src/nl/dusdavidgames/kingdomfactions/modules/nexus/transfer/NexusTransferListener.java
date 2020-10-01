package nl.dusdavidgames.kingdomfactions.modules.nexus.transfer;

import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.CapitalNexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class NexusTransferListener implements Listener{

	
	
	public void onInteract(PlayerInteractEvent e) {
		KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(e.getPlayer());
		if(e.getClickedBlock() != null) {
			if(NexusModule.getInstance().isNexus(e.getClickedBlock()));
			INexus n =NexusModule.getInstance().getNexus(e.getClickedBlock());
			if(n instanceof CapitalNexus) {
				e.getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Dit is de Nexus van een hoofdstad! Die kan je niet overzetten!");
				return;
			}
			Nexus nexus = (Nexus)n;
			if(NexusModule.getInstance().transferManager.getTransfer(player) != null) {
				NexusTransfer t = NexusModule.getInstance().transferManager.getTransfer(player);
				t.setNexus(nexus);
				t.execute(nexus.getOwner());
			}
		}
	}
}
