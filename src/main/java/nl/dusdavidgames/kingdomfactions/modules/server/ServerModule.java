package nl.dusdavidgames.kingdomfactions.modules.server;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.configuration.ConfigModule;

public class ServerModule {

	
	public ServerModule() {
		this.serverMode = ServerMode.getMode(ConfigModule.getInstance().getFile(ConfigModule.CONFIG).getConfig().getString("Servermode"));
 }
	
	private static @Getter @Setter ServerModule instance;
	
	
	private @Getter @Setter ServerMode serverMode;
}
