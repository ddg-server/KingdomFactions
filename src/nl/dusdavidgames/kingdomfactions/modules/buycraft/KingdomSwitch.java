package nl.dusdavidgames.kingdomfactions.modules.buycraft;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.StatisticsProfile;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.PurchaseSource;

import java.util.Date;
import java.util.UUID;

public class KingdomSwitch extends PurchasedPackage {

	public KingdomSwitch(String purchaseId, KingdomType kingdom, UUID uuid, int price, Date purchaseDate, String type, PurchaseSource source, boolean active) {
		super(purchaseId, uuid, price, purchaseDate, type, source, active, true);

        this.targetKingdom = kingdom;
	}

	private @Getter KingdomType targetKingdom;
	@Override
	public void execute() {
		KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(getUuid());
		if(player == null) {
			return;
		}
		player.heal();
		player.feed();
		player.setExp(0);
		player.clearInventory();
		player.setStatisticsProfile(new StatisticsProfile(player));
		if(player.hasFaction()) {
			if(player.getMembershipProfile().isFactionLeader()) {
				player.getFaction().remove();
			} else {
			
			}
		}
		player.save();

		player.setKingdom(KingdomModule.getInstance().getKingdom(targetKingdom));
	
		
	}

	

}
