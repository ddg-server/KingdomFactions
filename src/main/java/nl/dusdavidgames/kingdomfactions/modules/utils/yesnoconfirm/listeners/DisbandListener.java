package nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.listeners;

import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoListener;

public class DisbandListener implements YesNoListener{

	@Override
	public void onAgree(KingdomFactionsPlayer player) {
		player.getFaction().broadcast(Messages.getInstance().getPrefix(), player.getName() + " heeft de faction verwijderd!");
		player.getFaction().remove();
		
	}

	@Override
	public void onDeny(KingdomFactionsPlayer player) {
		player.getPlayer().closeInventory();
		
	}

	@Override
	public void onClose(KingdomFactionsPlayer player) {
		// TODO Auto-generated method stub
		
	}
	

}
