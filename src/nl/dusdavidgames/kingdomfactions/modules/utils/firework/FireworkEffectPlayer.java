package nl.dusdavidgames.kingdomfactions.modules.utils.firework;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import net.minecraft.server.v1_9_R2.EntityFireworks;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_9_R2.World;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;

public class FireworkEffectPlayer {

	public void playFirework(org.bukkit.World world, Location location, FireworkEffect fireworkEffect) {
		CustomFirework.spawn(location, fireworkEffect, world.getPlayers().toArray(new Player[world.getPlayers().size()]));
	}

	public static class CustomFirework extends EntityFireworks {
		Player[] players = null;

		public CustomFirework(World world, Player[] p) {
			super(world);
			players = p;
			this.a(0.25F, 0.25F);
		}

		boolean gone = false;

		public void m() {
			if (gone) {
				die();
				return;
			}	 
			if (!this.world.isClientSide) {
				gone = true;

				if (players != null)
					if (players.length > 0)
						for (Player player : players)
							PlayerModule.getInstance().getPlayer(player).getEntityPlayer().playerConnection.sendPacket(new PacketPlayOutEntityStatus(this, (byte) 17));
					else
						world.broadcastEntityEffect(this, (byte) 17);
				this.die();
			}
		}

		public static void spawn(Location location, FireworkEffect effect, Player[] players) {
			try {
				CustomFirework firework = new CustomFirework(((CraftWorld) location.getWorld()).getHandle(), players);
				FireworkMeta meta = ((Firework) firework.getBukkitEntity()).getFireworkMeta();
				meta.addEffect(effect);
				((Firework) firework.getBukkitEntity()).setFireworkMeta(meta);
				firework.setPosition(location.getX(), location.getY(), location.getZ());

				if ((((CraftWorld) location.getWorld()).getHandle()).addEntity(firework)) {
					firework.setInvisible(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}