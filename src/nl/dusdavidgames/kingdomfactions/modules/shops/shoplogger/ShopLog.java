package nl.dusdavidgames.kingdomfactions.modules.shops.shoplogger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

import lombok.Data;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.shops.shop.ShopItem;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ShopAction;

@Data
public class ShopLog {

	private ShopItem shopItem;
	private UUID offlineUUID;
	private String playerName;
	private String date;
	private ShopAction shopAction;
	private int coins = -1;

	public ShopLog(KingdomFactionsPlayer kdfPlayer, ShopItem shopItem, ShopAction shopAction){
		this.shopItem = shopItem;
		this.shopAction = shopAction;
		
		if(shopAction == ShopAction.PURCHASE){
			this.coins = shopItem.getBuyPrice();
		}else{
			this.coins = shopItem.getSellPrice();
		}

		OfflinePlayer player = kdfPlayer.getPlayer();

		this.offlineUUID = player.getUniqueId();
		this.playerName = player.getName();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
		Date resultdate = new Date(System.currentTimeMillis());
		this.date = sdf.format(resultdate) + "";
		
		ShopLogger.getInstance().getShopLogs().add(this);
	}
}