package nl.dusdavidgames.kingdomfactions.modules.war;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.war.command.OorlogCommand;
import nl.dusdavidgames.kingdomfactions.modules.war.command.WarCommand;
import nl.dusdavidgames.kingdomfactions.modules.war.runnable.WarRunnable;
import org.bukkit.Bukkit;

public class WarModule {

	private static @Getter @Setter WarModule instance;
	public WarModule() {
		setInstance(this);
		new WarCommand("war", "kingdomfactions.command.war", "Oorlog commando", "war", true, true).registerCommand();
		new OorlogCommand("oorlog", "kingdomfactions.command.oorlog", "Oorlog commando", "oorlog", true, true);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(KingdomFactionsPlugin.getInstance(), new WarRunnable(), 0, 20);//Every second

	}
	public @Getter @Setter War war;
	
	
	public boolean isWarActive() {
		if(war != null) {
			return true;
		}
		return false;
	}
	
	public void start(int time) {
		war = new War(time);
		war.start();
	}
}
