package nl.dusdavidgames.kingdomfactions.modules.kingdom;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.KingdomDatabase;
import nl.dusdavidgames.kingdomfactions.modules.exception.kingdom.UnkownKingdomException;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.command.KingdomCommand;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.eventhandlers.KingdomSwitchListener;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.eventhandlers.capital.InteractEventListener;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.menu.KingdomMenuModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.SetCapitalAction;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

public class KingdomModule {

	private static @Getter @Setter KingdomModule instance;

	public KingdomModule() {

		setInstance(this);
		loadKingdoms();
		new KingdomCommand("kingdom", "kingdomfactions.command.kingdom", "Main Kingdom Command", "", true, false)
				.registerCommand();
		new KingdomMenuModule();
		KingdomFactionsPlugin.getInstance().registerListener(new KingdomSwitchListener());
		KingdomFactionsPlugin.getInstance().registerListener(new KingdomSwitchListener());
		KingdomFactionsPlugin.getInstance().registerListener(new InteractEventListener());
	}

	private @Getter ArrayList<Kingdom> kingdoms = new ArrayList<Kingdom>();

	public Kingdom getSmallestKingdom() {
		HashMap<Kingdom, Integer> size = new HashMap<Kingdom, Integer>();
		for (Kingdom k : KingdomModule.getInstance().getKingdoms()) {
			size.put(k, k.getMembers());
		}
		int minValueInMap = (Collections.min(size.values()));
		if (size.isEmpty()) {
			return null;
		}
		for (Entry<Kingdom, Integer> entry : size.entrySet()) {
			if (entry.getValue() == minValueInMap) {
				entry.getKey(); // nexus
				entry.getValue();// value
				return entry.getKey();

			}
		}
		return null;
	}

	public void loadKingdoms() {
		for (KingdomType t : KingdomType.values()) {
			if(t == KingdomType.ERROR) continue;
		      try {
				kingdoms.add(KingdomDatabase.getInstance().loadKingdom(t));
			} catch(UnkownKingdomException e) {
				KingdomDatabase.getInstance().prepareKingdom(t.toString());
				Kingdom k = new Kingdom(t, Utils.getInstance().getMiningWorld().getSpawnLocation(), 
						new Location(Utils.getInstance().getOverWorld(), 0, 0, 0),
						new Location(Utils.getInstance().getOverWorld(), 0, 0, 0));
				kingdoms.add(k);

			}
		}
	}

	public Kingdom getKingdom(KingdomType kt) {
		for (Kingdom k : kingdoms) {
			if (k.getType().equals(kt)) {
				return k;
			}
		}
		return null;
	}

	public Kingdom getKingdom(String kingdom) {
		for (Kingdom k : kingdoms) {
			if (k.getType().toString().equalsIgnoreCase(kingdom)) {
				return k;
			}
		}
		return null;
	}


	public void createAction(Kingdom k, KingdomFactionsPlayer player) {
		SetCapitalAction action = new SetCapitalAction(k, player);
		player.setAction(action);
	}
}
