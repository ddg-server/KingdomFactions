package nl.dusdavidgames.kingdomfactions.modules.utils.logger;

import nl.dusdavidgames.kingdomfactions.modules.configuration.ConfigModule;

public class MemLeakHunter extends Logger {

	public MemLeakHunter() {
		super("Memory Leak Hunter ");
}

	
	@Override
	public void log(String message) {
		if(ConfigModule.getInstance().getFile(ConfigModule.CONFIG).getConfig().getBoolean("log.memleak_hunter")) {
			System.out.println("[KingdomFactions] " + prefix + message);
		} else {
			return;
		}
	}
}
