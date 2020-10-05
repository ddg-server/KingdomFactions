package nl.dusdavidgames.kingdomfactions.modules.configuration;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildingType;

import java.io.File;
import java.util.ArrayList;

public class ConfigModule {

	private static @Getter @Setter ConfigModule instance;

	public ConfigModule() {
		setInstance(this);
        initConfig();
	}
	
	public static final String CONFIG = "config.yml";
	public static final String SCHEMATICS = "schematics.yml";
	public static final String PRICES = "prices.yml";
	public void initConfig() {
		File f = new File(KingdomFactionsPlugin.getInstance().getDataFolder(), "schematics");
		if(!f.exists()) {
			f.mkdir();
		}
		KingdomFactionsFile config = new KingdomFactionsFile(CONFIG);
		KingdomFactionsFile schematics = new KingdomFactionsFile(SCHEMATICS);
		KingdomFactionsFile prices = new KingdomFactionsFile(PRICES);
		config.createConfigPath("Test.enabled", false);
		config.createConfigPath("Database.Logging.url", "jdbc:mysql://localhost:3306/{NAME}");
		config.createConfigPath("Database.MySQL.use_bukkitYaml", true);
		config.createConfigPath("Database.MySQL.url", "localhost");
		config.createConfigPath("Database.MySQL.password", "Walrus");
		config.createConfigPath("Database.MySQL.user","root");
		config.createConfigPath("Database.MySQL.name", "KingdomFactions");
		config.createConfigPath("Database.Redis.host", "localhost");
		config.createConfigPath("Database.Redis.port", 6379);
		config.createConfigPath("Database.Redis.password", "Walrus");
		config.createConfigPath("Database.Redis.db", 0);
		config.createConfigPath("Database.Redis.timeout", 60000);
		config.createConfigPath("Database.Redis.Connections", 4);
		config.createConfigPath("moderation.broadcast_commands", true);
		config.createConfigPath("log.debug", false);
		config.createConfigPath("log.ingame_debug", false);
		config.createConfigPath("log.ingame_warning", true);
		config.createConfigPath("log.memleak_hunter", true);
		config.createConfigPath("nexus.protected_region", 100);
		config.createConfigPath("faction.members.max", 30);
		config.createConfigPath("faction.members.min", 5);
		config.createConfigPath("kingdom.members.max", 10000);
		config.createConfigPath("kingdom.capital.protected_region", 200);
		config.createConfigPath("kingdom.total.protected_region", 1500);
		config.createConfigPath("worlds.overworld", "kdfworld");
		config.createConfigPath("kingdomfactions.uitleg", "http://forum.dusdavidgames.nl/threads/the-kingdom-factions-uitleg.28971");
		config.createConfigPath("worlds.miningworld", "kdfmining");
		config.createConfigPath("schematics.path", f.getPath());
		ArrayList<String> lores = new ArrayList<String>();
		lores.add("&cWow. Een EmpireWand!");
		lores.add("&cDit is een zeer krachtig wapen! Misbruik het niet!");
		lores.add("&4hihi. Zullen we eens een Kingdom opblazen?");
		lores.add("&bLet it goo! Let it gooooo!");
		lores.add("&aKaboom.");
		lores.add("&9Dit is beter dan /kingdom oerknal ^^");
		lores.add("&3Hearaq's EmpireWand. Afblijven!!!");
		config.createConfigPath("Empirewand.lores", lores);
     
      
		
		
		for (KingdomType k : KingdomType.values()) {
			for (BuildingType b : BuildingType.values()) {
				for (BuildLevel l : BuildLevel.values()) {
					if(k == KingdomType.GEEN || k == KingdomType.ERROR) continue;
					if(l == BuildLevel.LEVEL_0) continue;
					schematics.createConfigPath("schematic." + k.toString() + "." + b.toString() + "_" + l.getLevel(),
							k.toString() + "_" + b.toString() + "_" + l.getLevel() + ".schematic");
				}
			}
		
		}
		
		
		for (KingdomType k : KingdomType.values()) {
			for (BuildingType b : BuildingType.values()) {
				for (BuildLevel l : BuildLevel.values()) {
					if(k == KingdomType.GEEN || k == KingdomType.ERROR) continue;
					if(l == BuildLevel.LEVEL_0) continue;
					prices.createConfigPath("price." + k.toString() + "." + b.toString() + "_" + l.getLevel(), 100000);
				}
			}
		}
		
		
		
		schematics.save();
		config.save();
		prices.save();
	}
	
	
	private @Getter ArrayList<KingdomFactionsFile> files = new ArrayList<KingdomFactionsFile>();
	
	public KingdomFactionsFile getFile(String name) {
		for(KingdomFactionsFile file : files) {
			if(file.getName().equalsIgnoreCase(name)) {
				return file;
			}
		}
		return null;
	}
	public boolean isFile(String name) {
		return getFile(name) != null;
	}
	
	
}
