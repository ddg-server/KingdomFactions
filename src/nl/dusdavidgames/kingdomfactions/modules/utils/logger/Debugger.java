package nl.dusdavidgames.kingdomfactions.modules.utils.logger;

import nl.dusdavidgames.kingdomfactions.modules.configuration.ConfigModule;

public class Debugger extends Logger {

	public Debugger() {
		super("[DEBUG]");
	}

	@Override
	public void log(String message) {

		boolean shouldDebug = false;
		shouldDebug = ConfigModule.getInstance().getFile(ConfigModule.CONFIG).getConfig().getBoolean("log.debug");
		if (shouldDebug) {
			System.out.println("[KingdomFactions] " + prefix + " " + message);
		}
	}
}
