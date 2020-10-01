package nl.dusdavidgames.kingdomfactions.modules.memleak;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.chat.ChatModule;
import nl.dusdavidgames.kingdomfactions.modules.command.CommandModule;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.SpellModule;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.deathban.DeathBanModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.shops.ShopsModule;
import nl.dusdavidgames.kingdomfactions.modules.shops.shop.Shop;
import nl.dusdavidgames.kingdomfactions.modules.shops.shoplogger.ShopLogger;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Queue;

public class MemLeakMessage {

	private static @Getter @Setter MemLeakMessage instance;

	public MemLeakMessage() {
		setInstance(this);
	}

	public void sendMessage(CommandSender sender) {
		sender.sendMessage("--------");
		send("Channels", ChatModule.getInstance().getChannels(), sender);
		sender.sendMessage("--------");
		send("Commands", CommandModule.getInstance().getCommand(), sender);
		sender.sendMessage("--------");
		send("Data list", KingdomFactionsPlugin.getInstance().getDataManager().getDataList(), sender);
		sender.sendMessage("--------");
		send("Spells", SpellModule.getInstance().getSpells(), sender);
		sender.sendMessage("--------");
		send("Factions object", FactionModule.getInstance().getFactions(), sender);
		sender.sendMessage("--------");
		send("Kingdoms", KingdomModule.getInstance().getKingdoms(), sender);
	//	send("Kingdom menu", KingdomMenuModule.getInstance().getItems(), sender);
		send("Kingdom guards", getKingdomGuards(), sender);
		sender.sendMessage("--------");
		send("Nexuses", NexusModule.getInstance().getNexuses(), sender);
    //  send("Nexus update owner", NexusModule.getInstance().getUpdateOwner(), sender);
	//	send("Nexus edit health", NexusModule.getInstance().getEditHealth(), sender);
	//	send("Nexus info", NexusModule.getInstance().getNexusInfo(), sender);
		sender.sendMessage("--------");
		send("Deathbans", DeathBanModule.getInstance().getBans(), sender);
		sender.sendMessage("--------");
		send("Player list", PlayerModule.getInstance().getPlayers(), sender);
		send("Player queue", PlayerModule.getInstance().getQueue(), sender);
		send("Player cooldowns", getCooldowns(), sender);
		send("Player chatprofile holders", getChatHolders(), sender);
		send("Player meta data", getMetaData(), sender);
		sender.sendMessage("--------");
		send("Shops", ShopsModule.getInstance().getShops(), sender);
		send("Shop items", getShopItems(), sender);
		send("Shop logs", ShopLogger.getInstance().getShopLogs(), sender);
	}
	
	private int getShopItems(){
		int i = 0;
		
		for(Shop shop : ShopsModule.getInstance().getShops()){
			i += shop.getShopItemAmount();
		}
		
		return i;
	}

	private int getKingdomGuards() {
		int i = 0;
		for (Kingdom kingdom : KingdomModule.getInstance().getKingdoms()) {
			i += kingdom.getNexus().getGuards().size();
		}
		return i;
	}
	
	private int getCooldowns(){
		int i = 0;
		
		for(KingdomFactionsPlayer player : PlayerModule.getInstance().getPlayers()){
			i += player.getCooldowns().size();
		}
		
		return i;
	}
	
	private int getChatHolders(){
		int i = 0;
		
		for(KingdomFactionsPlayer player : PlayerModule.getInstance().getPlayers()){
			i += player.getChatProfile().getHolders().size();
		}
		
		return i;
	}
	
	private int getMetaData(){
		int i = 0;
		
		for(KingdomFactionsPlayer player : PlayerModule.getInstance().getPlayers()){
			i += player.getMetaData().size();
		}
		
		return i;
	}

	private void send(String name, ArrayList<?> list, CommandSender sender) {
		sender.sendMessage(name + " : " + list.size());
	}

	private void send(String name, int i, CommandSender sender) {
		sender.sendMessage(name + " : " + i);
	}

	//private void send(String name, HashMap<?, ?> list, CommandSender sender) {
	//	sender.sendMessage(name + ": " + list.size());
	//}
	
	private void send(String name, Queue<?> list, CommandSender sender) {
		sender.sendMessage(name + " : " + list.size());
	}
}