package nl.dusdavidgames.kingdomfactions.modules.player;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.projectiles.ProjectileSource;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.PlayerDatabase;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.PlayerException;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.player.deathban.DeathBanModule;
import nl.dusdavidgames.kingdomfactions.modules.player.eventhandlers.GodModeListener;
import nl.dusdavidgames.kingdomfactions.modules.player.eventhandlers.PlayerDeathEventListener;
import nl.dusdavidgames.kingdomfactions.modules.player.eventhandlers.PlayerJoinEventListener;
import nl.dusdavidgames.kingdomfactions.modules.player.eventhandlers.PlayerQuitEventListener;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.player.player.offline.OfflineKingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.StatisticsProfile;
import nl.dusdavidgames.kingdomfactions.modules.player.pvp.PvPManager;
import nl.dusdavidgames.kingdomfactions.modules.player.runnables.GodModeRunnable;
import nl.dusdavidgames.kingdomfactions.modules.player.runnables.SaveRunnable;
import nl.dusdavidgames.kingdomfactions.modules.player.runnables.ScheduledSaveRunnable;

public class PlayerModule {

	private @Getter PlayerList players = new PlayerList();
	private static @Getter @Setter PlayerModule instance;
	private @Getter Queue<KingdomFactionsPlayer> queue = new LinkedList<KingdomFactionsPlayer>();

	
	public boolean saving = false; 
	
	@SuppressWarnings("deprecation")
	public PlayerModule() {
		setInstance(this);
		
		new DeathBanModule();
		
		new GodModeRunnable();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(KingdomFactionsPlugin.getInstance(),
				new ScheduledSaveRunnable(), 0, 300L * 20);
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(KingdomFactionsPlugin.getInstance(), new SaveRunnable(), 0, 20 * 2);

		KingdomFactionsPlugin.getInstance().registerListener(new PlayerJoinEventListener());
		KingdomFactionsPlugin.getInstance().registerListener(new PlayerQuitEventListener());
		KingdomFactionsPlugin.getInstance().registerListener(new PlayerDeathEventListener());
		KingdomFactionsPlugin.getInstance().registerListener(new GodModeListener());
		KingdomFactionsPlugin.getInstance().registerListener(new PvPManager());
	}

	/**
	 * 
	 * @param player
	 * @return KingdomFactionsPlayer
	 * 
	 *         get a KingdomFactionsPlayer by the Bukkit Player
	 */
	public KingdomFactionsPlayer getPlayer(Player player) {
		KingdomFactionsPlayer p = null;
		for (KingdomFactionsPlayer pl : players) {
			if (pl.getUuid().equals(player.getUniqueId())) {
				return pl;
			}
		}
		try {
			p = PlayerDatabase.getInstance().loadPlayer(player.getUniqueId());
		} catch (UnkownPlayerException e) {
			PlayerDatabase.getInstance().createPlayer(player.getUniqueId() + "", player.getName(),
					player.getAddress().getHostString());
			p = new KingdomFactionsPlayer(FactionRank.SPELER, KingdomRank.SPELER,
					KingdomModule.getInstance().getKingdom(KingdomType.GEEN), null, player.getName());
			p.setStatisticsProfile(new StatisticsProfile(p, 0, 0, 0, 0, 0, System.currentTimeMillis()));
		}
		this.players.add(p);
		return p;
	}

	/**
	 * 
	 * @param name
	 * @return KingdomFactionsPlayer
	 * @throws UnkownPlayerException
	 */
	public IPlayerBase getPlayerBase(String name) throws UnkownPlayerException {
		for (KingdomFactionsPlayer player : players) {
			if (player.getName().equalsIgnoreCase(name)) {
				return player;
			}
		}
		return PlayerDatabase.getInstance().loadOfflinePlayer(name);
	}

	/**
	 * 
	 * @param uuid
	 * @return KingdomFactionsPlayer
	 * @throws UnkownPlayerException
	 */
	public IPlayerBase getPlayerBase(UUID uuid) throws UnkownPlayerException {
		for (KingdomFactionsPlayer player : players) {
			if (player.getUuid().equals(uuid)) {
				return player;
			}
		}
		return PlayerDatabase.getInstance().loadOfflinePlayer(uuid);
	}

	/**
	 * 
	 * @param player
	 * @return KingdomFactionsPlayer
	 */
	public KingdomFactionsPlayer getOnlinePlayer(IPlayerBase player) {
		if (player instanceof KingdomFactionsPlayer) {
			return (KingdomFactionsPlayer) player;
		}
		return null;
	}

	/**
	 * 
	 * @param player
	 * @return KingdomFactionsPlayer
	 */
	public OfflineKingdomFactionsPlayer getOfflinePlayer(IPlayerBase player) {
		if (player instanceof OfflineKingdomFactionsPlayer) {
			return (OfflineKingdomFactionsPlayer) player;
		}
		return null;
	}

	/**
	 * 
	 * @param name
	 * @return KingdomFactionsPlayer
	 * @throws UnkownPlayerException 
	 */
	public KingdomFactionsPlayer getPlayer(String name) throws UnkownPlayerException {
		for (KingdomFactionsPlayer player : players) {
			if (player.getName().equalsIgnoreCase(name)) {
				return player;
			}
		}
		throw new UnkownPlayerException("Player is not online.");
	}

	/**
	 * 
	 * @param sender
	 * @return KingdomFactionsPlayer
	 */
	public KingdomFactionsPlayer getPlayer(CommandSender sender) {
		for (KingdomFactionsPlayer player : players) {
			if (player.getName().equalsIgnoreCase(sender.getName())) {
				return player;
			}
		}
		return null;
	}

	public KingdomFactionsPlayer getPlayer(ProjectileSource projSource) throws PlayerException {
		if(projSource instanceof Player) {
		Player p = (Player) projSource;
		return getPlayer(p.getName());
		} else {
			throw new PlayerException("ProjectileSource is not a valid Player!");
		}
	}
	/**
	 * 
	 * @param uuid
	 * @return KingdomFactionsPlayer
	 */
	public KingdomFactionsPlayer getPlayer(UUID uuid) {
		for (KingdomFactionsPlayer pl : players) {
			if (pl.getUuid().equals(uuid)) {
				return pl;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param entity
	 * @return KingdomFactionsPlayer
	 * @throws PlayerException
	 */
	public KingdomFactionsPlayer getPlayer(Entity e) throws PlayerException {
		if (e instanceof Player) {
			return getPlayer(((Player) e).getName());
		} else {
			throw new PlayerException("Could not convert this entity to KingdomFactionsPlayer");
		}
	}

	public KingdomFactionsPlayer getPlayer(HumanEntity e) {
		try {
			return getPlayer(e.getName());
		} catch (UnkownPlayerException e1) {
			return null; //Can't happen
		}
	}

	public void saveAsync() {
		for (KingdomFactionsPlayer player : players) {
			this.queue.offer(player);
		}
	}

	public KingdomFactionsPlayer convert(OfflineKingdomFactionsPlayer player) {
		try {
			return getPlayer(player.getName());
		} catch (UnkownPlayerException e) {
			return null; //Can't happen
		}
	}

	public OfflineKingdomFactionsPlayer convert(KingdomFactionsPlayer player) {
		return new OfflineKingdomFactionsPlayer(player.getName(), 
				player.getUuid(), 
				player.getIpAdres(), 
				player.getKingdom(), 
				player.getFaction(), 
				player.getFactionRank(), 
				player.getKingdomRank(), 
				player.getCoins(), 
				player.getStatisticsProfile().getDeaths(), 
				player.getStatisticsProfile().getKills(), 
				player.getStatisticsProfile().getFirstjoin(), 
				player.getStatisticsProfile().getSecondsConnected(), 
				player.getInfluence(), 
				player.getLocation());
		
	}
	
}
