package nl.dusdavidgames.kingdomfactions.modules.nexus.transfer;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.action.IAction;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoConfirmation;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoListener;

public class NexusTransfer implements IAction {

	public NexusTransfer(Faction newf, KingdomFactionsPlayer player) {
		this.newFaction = newf;
		this.player = player;
	}

	private @Getter Faction newFaction;
	private @Getter KingdomFactionsPlayer player;
	private @Getter @Setter Nexus nexus;
	
	
	
	
	
	public void cancel() {
	   NexusModule.getInstance().transferManager.getTransfer().remove(this);	
	}
	public void execute(Faction oldOwner) {
		new YesNoConfirmation(player, ChatColor.RED + "Weet je zeker dat je deze nexus over wil zetten?", ChatColor.GREEN + "Er kan hierdoor data verloren gaan! (Denk aan homes etc)", ChatColor.RED + "Annuleer de actie!", new YesNoListener() {
			private Nexus n = getNexus();
			
			@Override
			public void onDeny(KingdomFactionsPlayer player) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClose(KingdomFactionsPlayer player) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAgree(KingdomFactionsPlayer player) {
				this.n.setOwner(newFaction);
				for(KingdomFactionsPlayer staff : PlayerModule.getInstance().getPlayers()) {
					if(staff.isStaff()) {
						staff.sendMessage(player.getFormattedName() + ChatColor.YELLOW + " heeft de Nexus van " +  oldOwner.getName() + " overgezet naar " + newFaction.getName() + "!");
					}
				}
				this.n.save();
			}
		});
		this.cancel();
	}
	public Nexus executeAction(Faction oldOwner) {
		this.execute(oldOwner);
		return nexus;

	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
