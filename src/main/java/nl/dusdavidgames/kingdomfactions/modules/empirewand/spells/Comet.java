package nl.dusdavidgames.kingdomfactions.modules.empirewand.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.Spell;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.firework.FireworkEffectPlayer;

public class Comet extends Spell implements Listener {

	public Comet(String name) {
		super(name);
		KingdomFactionsPlugin.getInstance().registerListener(this);
	}

	private static List<UUID> fireballList = new ArrayList<>();

	@Override
	public void execute(KingdomFactionsPlayer player) {
		fireComet(player.getPlayer());

	}

	public void fireComet(Player p) {

		final SmallFireball fireball = (SmallFireball) p.getWorld().spawn(p.getEyeLocation(), SmallFireball.class);
		fireball.setShooter(p);
		fireball.setVelocity(p.getLocation().getDirection().multiply(3)
				.add(new Vector(Math.random() / 3.0D, 0.0D, Math.random() / 3.0D)));
		fireball.setYield(1.0F);

		Comet.fireballList.add(fireball.getUniqueId());

		FireworkEffect.Type type = FireworkEffect.Type.BURST;
		Color c1 = Color.ORANGE;
		Color c2 = Color.RED;

		final FireworkEffect fireworkEffect = FireworkEffect.builder().flicker(true).withColor(c1).withFade(c2)
				.with(type).trail(false).build();

		final FireworkEffectPlayer fireworkPlayer = new FireworkEffectPlayer();

		new BukkitRunnable() {
			int count = 0;

			public void run() {
				this.count += 1;
				if ((!fireball.isDead()) && (Comet.fireballList.contains(fireball.getUniqueId()))
						&& (this.count < 501)) {
					Location fireballLocation = fireball.getLocation();
					try {
						fireworkPlayer.playFirework(fireballLocation.getWorld(), fireballLocation, fireworkEffect);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					if (fireball != null) {
						FireworkEffect.Type typeh = FireworkEffect.Type.BALL_LARGE;
						Color c1h = Color.ORANGE;
						Color c2h = Color.RED;
						FireworkEffect effecth = FireworkEffect.builder().flicker(true).withColor(c1h).withFade(c2h)
								.with(typeh).trail(true).build();
						try {
							fireworkPlayer.playFirework(fireball.getWorld(), fireball.getLocation(), effecth);
						} catch (Exception e) {
							e.printStackTrace();
						}
						for (Entity ent : Utils.getInstance().getNearbyEntities(fireball.getLocation(), 5)) {
							if ((ent instanceof LivingEntity)) {
								ent.setFireTicks(ent.getFireTicks() + 60);
							}
						}
						fireball.remove();
					}
					if (Comet.fireballList.contains(fireball.getUniqueId())) {
						Comet.fireballList.remove(fireball.getUniqueId());
					}
					cancel();
				}
			}
		}.runTaskTimer(KingdomFactionsPlugin.getInstance(), 0L, 3L);
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) throws Exception {
		if (fireballList.contains(e.getEntity().getUniqueId())) {
			Location fireballHitLocation = e.getEntity().getLocation();
			if(!KingdomFactionsPlugin.getInstance().getDataManager().getBoolean("Test.enabled")) {
			fireballHitLocation.getWorld().createExplosion(fireballHitLocation.getX(), fireballHitLocation.getY(),
					fireballHitLocation.getZ(), 4, true, true);
			} else {
				fireballHitLocation.getWorld().createExplosion(fireballHitLocation.getX(), fireballHitLocation.getY(),
						fireballHitLocation.getZ(), 4, true, false);
			}

			FireworkEffect.Type type = FireworkEffect.Type.BALL_LARGE;
			Color c1 = Color.ORANGE;
			Color c2 = Color.RED;

			FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(c1).withFade(c2).with(type)
					.trail(true).build();

			FireworkEffectPlayer fireworkPlayer = new FireworkEffectPlayer();

			fireworkPlayer.playFirework(fireballHitLocation.getWorld(), fireballHitLocation, effect);
			for (Entity ent : Utils.getInstance().getNearbyEntities(fireballHitLocation, 5)) {
				if ((ent instanceof LivingEntity)) {
					ent.setFireTicks(ent.getFireTicks() + 60);
				}
			}
			fireballList.remove(e.getEntity().getUniqueId());
		}
	}

}
