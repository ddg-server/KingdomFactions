package nl.dusdavidgames.kingdomfactions.modules.nexus.protection.protectionlisteners;

import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.ProtectionModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityEventListener implements Listener {

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}

		if (!(event.getEntity() instanceof Ageable)) {
			return;
		}

		Ageable entity = (Ageable) event.getEntity();
		Player player = null;

		if (event.getDamager() instanceof Player) {
			player = (Player) event.getDamager();
		}

	

		if (event.getDamager() instanceof Projectile) {
			Projectile projectile = (Projectile) event.getDamager();

			if (projectile.getShooter() != null && projectile.getShooter() instanceof Player) {
				player = (Player) projectile.getShooter();
			}
		}

		if (player == null) {
			return;
		}
		if (PlayerModule.getInstance().getPlayer(player).getTerritoryId().equalsIgnoreCase("~MINING~")) {
			return;
		}
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(player);
	
		if(!p.canBuild(event.getEntity().getLocation())) {

		if (PlayerModule.getInstance().getPlayer(player).getSettingsProfile().hasAdminMode())
			return;
			if (entity instanceof Villager) {
				event.setCancelled(!ProtectionModule.getInstance().tryInfluence(PlayerModule.getInstance().getPlayer(player), 200));
			} else {
				event.setCancelled(!ProtectionModule.getInstance().tryInfluence(PlayerModule.getInstance().getPlayer(player), 100));
			}

		}
	}
}
