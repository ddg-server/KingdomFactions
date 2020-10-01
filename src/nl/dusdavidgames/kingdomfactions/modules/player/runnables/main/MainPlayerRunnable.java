package nl.dusdavidgames.kingdomfactions.modules.player.runnables.main;

import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.CombatTracker;
import org.bukkit.Bukkit;

public class MainPlayerRunnable {

	public MainPlayerRunnable() {
		this.init();
		new ImportandTasks(this);
		CombatTracker.initCombatNotifier(this);
	}

	private PlayerTaskList list = new PlayerTaskList();

	private void init() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(KingdomFactionsPlugin.getInstance(), new Runnable() {

			@Override
			public void run() {
				for (KingdomFactionsPlayer player : PlayerModule.getInstance().getPlayers()) {
					for (ScheduledPlayerTask t : list) {
						t.run(player);
					}
				}
			}
		}, 0L, 20L);
	}

	public void scheduleTask(ScheduledPlayerTask task) {
		this.list.add(task);
	}
}
