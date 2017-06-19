package nl.dusdavidgames.kingdomfactions.modules.empirewand.spells;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.Spell;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.event.SpellExecuteEvent;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.particles.ParticleEffect;

public class Spark extends Spell {

	public Spark(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	private @Getter Location executeLocation;

	@Override
	public void execute(KingdomFactionsPlayer player) {

		setLocation(player.getPlayer().getTargetBlock((Set<Material>) null, 80).getLocation());

		SpellExecuteEvent event = new SpellExecuteEvent(executeLocation, this, player);
		if (event.isCancelled())
			return;
		playSmoke(executeLocation);
		for (Entity e : Utils.getInstance().getNearbyEntities(executeLocation, 3)) {
			if (e instanceof LivingEntity) {
				LivingEntity en = (LivingEntity) e;

				if (e instanceof Player) {
					Player p = (Player) e;
					if (PlayerModule.getInstance().getPlayer(p).isVanished())
						return;
					if (en.getHealth() > 5) {
						en.damage(5D);
					} else {
						en.damage(en.getHealth());
					}
				}
			}
		}

	}

	public void setLocation(Location loc) {
		this.executeLocation = Utils.getInstance().getNewLocation(loc);
	}

	public void playSmoke(Location l) {
		Utils.getInstance().playParticle(l, ParticleEffect.LARGE_SMOKE);

		new BukkitRunnable() {
			int count = 0;

			public void run() {
				this.count += 1;
				if (this.count <= 5) {
					Utils.getInstance().playParticle(l, ParticleEffect.LARGE_SMOKE);
				} else {
					cancel();
				}
			}
		}.runTaskTimer(KingdomFactionsPlugin.getInstance(), 1L, 2L);
		Utils.getInstance().playParticle(l, ParticleEffect.LARGE_SMOKE);
		for(int i = 0; i < 5; i++) {
 		Utils.getInstance().playParticle(l, ParticleEffect.HAPPY_VILLAGER);
		}
	}
}
