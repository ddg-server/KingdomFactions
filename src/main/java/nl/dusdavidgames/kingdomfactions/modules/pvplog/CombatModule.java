package nl.dusdavidgames.kingdomfactions.modules.pvplog;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.player.runnables.main.ScheduledPlayerTask;

public class CombatModule {

	public CombatModule() {
		setInstance(this);
		this.initRunnable();
		KingdomFactionsPlugin.getInstance().registerListener(new VanishListener());
		new CombatCommand("combat", "kingdomfactions.command.combat", "Combat gerelateerde commando's", "combat <sub>",
				true, false).registerCommand();
        new LogoutCommand("logout", "kingdomfactions.command.logout", "Log uit via dit commando als je in gevecht bent!", "logout", false, false).registerCommand();
	}

	private static @Getter @Setter CombatModule instance;

	private void initRunnable() {
		KingdomFactionsPlugin.getInstance().getTaskManager().scheduleTask(player -> {
			if (player.getCombatTracker() == null)
				return;
			if (player.getCombatTracker().isInCombat()) {
				if (player.getCombatTracker().getCombatSeconds() <= 0) {
					player.getCombatTracker().setInCombat(false);
				} else {
					int i = player.getCombatTracker().getCombatSeconds();
					i--;
					player.getCombatTracker().setCombatSeconds(i);
				}
			}
			if (player.hasAction()) {

				if (player.getAction() instanceof LogoutAction) {
					LogoutAction a = (LogoutAction) player.getAction();

					if (a.logoutSeconds <= 1) {
						try {
							a.execute();

						} catch (KingdomFactionsException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						a.logoutSeconds--;
					}
				}
			}

		});
	}
}
