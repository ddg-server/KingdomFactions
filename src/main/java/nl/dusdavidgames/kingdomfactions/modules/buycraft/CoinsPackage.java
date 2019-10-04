package nl.dusdavidgames.kingdomfactions.modules.buycraft;

import java.util.Date;
import java.util.UUID;

import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.PurchaseSource;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

public class CoinsPackage extends PurchasedPackage {

	public CoinsPackage(String purchaseId,int bought, UUID uuid, int price, Date purchaseDate, String type, PurchaseSource source,
			boolean active, boolean requireOnline) {
		super(purchaseId, uuid, price, purchaseDate, type, source, active, requireOnline);
		this.bought = bought;
	}

	public int bought;

	public int getBought() {
		return bought;
	}

	@Override
	public void execute() {
		IPlayerBase player;
		try {
			player = PlayerModule.getInstance().getPlayerBase(this.getUuid());
		} catch (UnkownPlayerException e) {
			setActive(false);
			Logger.INFO.log("Received purchase for unkown player! Setting it to INACTIVE");
			return;
		}
		if(doesRequireOnline()) {
		if(player.isOnline()) {
			Logger.INFO.log("Processing purchase for "+this.getUuid()+": " + player.getCoins() + " + " + bought + " coins!");
			player.addCoins(bought);
			player.save();
			
		}
		} else {
			Logger.INFO.log("Processing purchase for "+this.getUuid()+": " + player.getCoins() + " + " + bought + " coins!");
			player.addCoins(bought);
			player.save();
		}


	}
	
	
}
