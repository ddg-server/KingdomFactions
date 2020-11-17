package nl.dusdavidgames.kingdomfactions.modules.player.deathban;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class DeathBanModule {

	public DeathBanModule() {
		setInstance(this);
		KingdomFactionsPlugin.getInstance().registerListener(new DeathBanLoginEventListener());
		this.initDeathbanCleaner();
	}

	private static @Getter @Setter DeathBanModule instance;

	private @Getter ArrayList<DeathBan> bans = new ArrayList<DeathBan>();

	public DeathBan getBan(UUID uuid) {
		for (DeathBan ban : bans) {
			if (ban.getUuid().equals(uuid)) {
				return ban;
			}
		}
		return null;

	}

	public void ban(KingdomFactionsPlayer p) {
		DeathBan ban = new DeathBan(p.getName(), p.getUuid(), 5);
		bans.add(ban);
		p.kick(ChatColor.RED + "Je bent dood gegaan! Wacht "+ban.getMinutes()+" minuten.");
	}

	public void unRegister(DeathBan ban) {
		bans.remove(ban);
	}

	public DeathBan getBan(String name) {
		for (DeathBan ban : bans) {
			if (ban.getName().equalsIgnoreCase(name)) {
				return ban;
			}
		}
		return null;

	}
	
	@SuppressWarnings("deprecation")
	private void initDeathbanCleaner() {
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(KingdomFactionsPlugin.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				Iterator<DeathBan> ban = bans.iterator();
				while(ban.hasNext()) {
					DeathBan b = ban.next();
					if(!b.isActive()) {
						b.unban();
					}
				}
			}
		}, 0, 20*60*5);
	}
}
