package nl.dusdavidgames.kingdomfactions;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.chat.ChatModule;
import nl.dusdavidgames.kingdomfactions.modules.coins.CoinsModule;
import nl.dusdavidgames.kingdomfactions.modules.command.CommandModule;
import nl.dusdavidgames.kingdomfactions.modules.configuration.ConfigModule;
import nl.dusdavidgames.kingdomfactions.modules.data.DataList;
import nl.dusdavidgames.kingdomfactions.modules.data.DataManager;
import nl.dusdavidgames.kingdomfactions.modules.database.DatabaseModule;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.EmpireWandModule;
import nl.dusdavidgames.kingdomfactions.modules.exception.DataException;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionModule;
import nl.dusdavidgames.kingdomfactions.modules.influence.InfluenceModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.memleak.MemLeakModule;
import nl.dusdavidgames.kingdomfactions.modules.mine.MineModule;
import nl.dusdavidgames.kingdomfactions.modules.monster.MonsterModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.permission.PermissionModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.runnables.main.MainPlayerRunnable;
import nl.dusdavidgames.kingdomfactions.modules.pvplog.CombatModule;
import nl.dusdavidgames.kingdomfactions.modules.scoreboard.ScoreboardModule;
import nl.dusdavidgames.kingdomfactions.modules.settings.SettingsModule;
import nl.dusdavidgames.kingdomfactions.modules.shops.ShopsModule;
import nl.dusdavidgames.kingdomfactions.modules.shops.shoplogger.ShopLogger;
import nl.dusdavidgames.kingdomfactions.modules.time.TimeModule;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.UtilsModule;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import nl.dusdavidgames.kingdomfactions.modules.war.WarModule;
import nl.dusdavidgames.kingdomfactions.modules.wreckingball.WreckingBallModule;

public class KingdomFactionsPlugin extends JavaPlugin {

	private static @Getter @Setter KingdomFactionsPlugin instance;

	public void onEnable() {
		this.loadMS = System.currentTimeMillis();
		setInstance(this);

		this.initTps();
		this.dataManager = new DataManager(new DataList());
		new UtilsModule();
		new ConfigModule();
		this.initWorlds();
		Logger.INFO.log("Starting The Kingdom Factions v" + this.getDescription().getVersion() + " by "
				+ this.getDescription().getAuthors().toString().replace("[", "").replace("]", ""));
		new DatabaseModule();
		new CommandModule();
		new KingdomModule();
		new PlayerModule();
		new NexusModule();
		new FactionModule();
		new MineModule();
		new TimeModule();
		new ChatModule();
		new CoinsModule();
		new InfluenceModule();
		new ScoreboardModule();
		new EmpireWandModule();
		new WarModule();
		new PermissionModule();
        new MemLeakModule();
		// new DynmapModule();
		// new ViewDistanceModule();
        this.taskManager = new MainPlayerRunnable();
		new ShopsModule();
		new WreckingBallModule();
		new CombatModule();
		new SettingsModule();

		Logger.INFO.log("Started The Kingdom Factions! Took " + (System.currentTimeMillis() - loadMS) + " ms!");
	}

	private long loadMS;
	private @Getter DataManager dataManager;

	
	private @Getter MainPlayerRunnable taskManager;
	
	
	@Override
	public void onDisable() {

		ShopLogger.getInstance().disable();

		for (Entity e : Utils.getInstance().getOverWorld().getEntities()) {
			if (e instanceof LivingEntity) {
				if (MonsterModule.getInstance().getGuard((LivingEntity) e) != null) {
					MonsterModule.getInstance().getGuard((LivingEntity) e).kill();
				}
			}
		}
		MySQLModule.getInstance().closeConnection();
	}

	public void registerListener(Listener l) {
		Bukkit.getPluginManager().registerEvents(l, this);

	}
	

	public void registerCommand(String command, CommandExecutor c) {
		this.getCommand(command).setExecutor(c);

	}

	private void initWorlds() {
		try {
			String overworld = KingdomFactionsPlugin.getInstance().getDataManager().getString("worlds.overworld");
			String miningworld = KingdomFactionsPlugin.getInstance().getDataManager().getString("worlds.miningworld");
			if (Utils.getInstance().getOverWorld() == null) {
				WorldCreator creator = new WorldCreator(overworld);
				creator.environment(World.Environment.NORMAL);
				creator.type(WorldType.NORMAL);
				creator.seed(new Random().nextLong());
				creator.generateStructures(true);
				Bukkit.createWorld(creator);
			}
			if (Utils.getInstance().getMiningWorld() == null) {
				WorldCreator creator = new WorldCreator(miningworld);
				creator.environment(World.Environment.NORMAL);
				creator.type(WorldType.NORMAL);
				creator.seed(new Random().nextLong());
				creator.generateStructures(true);
				Bukkit.createWorld(creator);
			}
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getTPS() {
		return tps;
	}

	private int tps = 0;
	private long second = 0;

	private void initTps() {
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			long sec;
			int ticks;

			@Override
			public void run() {
				sec = (System.currentTimeMillis() / 1000);

				if (second == sec) {
					ticks++;
				} else {
					second = sec;
					tps = (tps == 0 ? ticks : ((tps + ticks) / 2));
					ticks = 0;
				}
			}
		}, 20, 1);
	}
	
	
	
	
	
}
