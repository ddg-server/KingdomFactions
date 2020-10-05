package nl.dusdavidgames.kingdomfactions.modules.empirewand.spells;

import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.Spell;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Leap extends Spell implements Listener {

	public Leap(String name) {
		super(name);
		Bukkit.getServer().getPluginManager().registerEvents(this, KingdomFactionsPlugin.getInstance());
	}

	private static ArrayList<String> noFallDamage = new ArrayList<>();

	@Override
	public void execute(KingdomFactionsPlayer player) {

		noFallDamage.add(player.getName());

		double forwardPowerModifier = 1.5D;
		double upwardPowerModifier = forwardPowerModifier * 2.0D;
		double fwv = 2.0D;
		double uwv = 0.7D;

		Vector dir = player.getLocation().getDirection();

		dir.setY(0).normalize().multiply(fwv * forwardPowerModifier).setY(uwv * upwardPowerModifier);

		player.getPlayer().setVelocity(dir);

		final World w = player.getPlayer().getWorld();
		w.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);

		new BukkitRunnable() {
			int autoStopCount = 0;

			@SuppressWarnings("deprecation")
			public void run() {
				if ((this.autoStopCount <= 30) && (Leap.noFallDamage.contains(player.getName()))
						&& (Bukkit.getPlayer(player.getName()) != null)
						&& (!Bukkit.getPlayer(player.getName()).isOnGround())) {
					Player p = Bukkit.getPlayer((player.getName()));
					w.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1);

					this.autoStopCount += 1;
				} else {
					if ((Leap.noFallDamage.contains(player.getName()))) {
						Leap.noFallDamage.remove(player.getName());
					}
					cancel();
				}
			}
		}.runTaskTimer(KingdomFactionsPlugin.getInstance(), 5L, 3L);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if ((e.getEntity() instanceof Player)) {
			Player p = (Player) e.getEntity();
			if ((e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) && (noFallDamage.contains(p.getName()))) {
				e.setCancelled(true);

				noFallDamage.remove(p.getName());
			}
		}
	}

}
