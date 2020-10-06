package nl.dusdavidgames.kingdomfactions.modules.nexus.transfer;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

import java.util.ArrayList;

public class TransferManager {

	
	public TransferManager() {
	}
	
	private @Getter ArrayList<NexusTransfer> transfer = new ArrayList<NexusTransfer>();
	
	
	public NexusTransfer getTransfer(KingdomFactionsPlayer player) {
		for(NexusTransfer t : transfer) {
			if(t.getPlayer().equals(player)) {
				return t;
			}
		}
		return null;
	}
	public NexusTransfer getTrasfer(Nexus nexus) {
		for(NexusTransfer t : transfer) {
			if(t.getNexus().equals(t)) {
				return t;
			}
		}
		return null;
	}
	
	public NexusTransfer createTransfer(KingdomFactionsPlayer player, Faction faction) {
		NexusTransfer t = new NexusTransfer(faction, player);
		this.transfer.add(t);
		return t;
	}
}
