package nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm;

import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public interface YesNoListener {

	public abstract void onAgree(KingdomFactionsPlayer player);
	public abstract void onDeny(KingdomFactionsPlayer player);
	public abstract void onClose(KingdomFactionsPlayer player);
	
}
