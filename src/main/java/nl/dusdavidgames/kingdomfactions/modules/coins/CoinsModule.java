package nl.dusdavidgames.kingdomfactions.modules.coins;

import org.bukkit.Bukkit;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.coins.command.CoinsCommand;
import nl.dusdavidgames.kingdomfactions.modules.coins.command.PayCommand;
import nl.dusdavidgames.kingdomfactions.modules.coins.event.CoinEditEvent;
import nl.dusdavidgames.kingdomfactions.modules.exception.value.ValueException;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.StatisticsProfile;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ValueEditAction;

public class CoinsModule {

	private static @Getter @Setter CoinsModule instance;

	public CoinsModule() {
		setInstance(this);
		
		new CoinsCommand("coins", "kingdomfactions.command.coins", "Main commando voor Coins!", "coins sub", true, true).registerCommand();
		new PayCommand("pay", "kingdomfactions.command.pay", "Betaal een gebruiker!", "pay [user]", false, false).registerCommand();
	
	}

	public boolean canAfford(StatisticsProfile profile, int coins) {
		if (profile.getCoins() >= coins) {
			return true;
		}
		return false;
	}

	public void addCoins(StatisticsProfile profile, int coins) {
		profile.setCoins(profile.getCoins() + coins);

		Bukkit.getPluginManager().callEvent(new CoinEditEvent(profile, ValueEditAction.ADD));
	}

	public void removeCoins(StatisticsProfile profile, int coins, boolean mayBeNegative) throws ValueException {

		if (mayBeNegative) {
			profile.setCoins(profile.getCoins() - coins);
			Bukkit.getPluginManager().callEvent(new CoinEditEvent(profile, ValueEditAction.REMOVE));

		} else {
			if (canAfford(profile, coins)) {
				profile.setCoins(profile.getCoins() - coins);
				Bukkit.getPluginManager().callEvent(new CoinEditEvent(profile, ValueEditAction.REMOVE));
			} else {
				throw new ValueException("Player could not afford this price.");
			}
		}
	}

}
