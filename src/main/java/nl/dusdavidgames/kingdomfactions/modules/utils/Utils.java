package nl.dusdavidgames.kingdomfactions.modules.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.configuration.ConfigModule;
import nl.dusdavidgames.kingdomfactions.modules.exception.DataException;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.PlayerException;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings.Building;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildingType;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.MessagePrefix;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.Title;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.TitleDuration;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import nl.dusdavidgames.kingdomfactions.modules.utils.nms.NMSMethods;
import nl.dusdavidgames.kingdomfactions.modules.utils.particles.ParticleEffect;

public class Utils extends NMSMethods {

	private static @Getter @Setter Utils instance;

	public Utils() {
		setInstance(this);
	}

	public void playFirework(Player p, Location loc, Color color1, Color color2, FireworkEffect.Type type) {
		loc.add(0.5, 1, 0.5);
		Firework fw = p.getWorld().spawn(loc, Firework.class);
		FireworkMeta fwmeta = ((org.bukkit.entity.Firework) fw).getFireworkMeta();
		FireworkEffect.Builder builder = FireworkEffect.builder();

		builder.withFlicker();
		builder.withFade(color2);
		builder.withColor(color1);
		builder.with(type);
		fwmeta.clearEffects();
		Field f;
		try {
			f = fwmeta.getClass().getDeclaredField("power");
			f.setAccessible(true);
			f.set(fwmeta, -1);
		} catch (Exception e) {
			return;
		}
		fwmeta.addEffect(builder.build());
		fw.setFireworkMeta(fwmeta);
	}

	public void pasteSchematic(KingdomFactionsPlayer p, String file, Location loc) {
		File schematic = new File("plugins/WorldEdit/schematics/" + file);
		EditSession es = new EditSession(new BukkitWorld(p.getPlayer().getWorld()), 999999999);
		
		try {
			p.sendMessage(Messages.getInstance().getPrefix() + "Bezig met het bouwen...");
			@SuppressWarnings("deprecation")
			CuboidClipboard cc = CuboidClipboard.loadSchematic(schematic);
			cc.paste(es, new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), false);
			
	
		} catch (IOException e) {
			Logger.ERROR.log("------------------------- WORLDEDIT PASTE PROBLEM -------------------------");
			Logger.ERROR.log("Expected Problem: Missing Schematic. /plugins/WorldEdit/schematics/?");
			Logger.ERROR.log("Expected Schematic Name: " + file);
			Logger.ERROR.log("Location: " + getLocation(loc));
			Logger.ERROR.log("By player: " + p.getName() + " / " + p.getUuid());
			e.printStackTrace();
			Logger.ERROR.log("------------------------- WORLDEDIT PASTE PROBLEM -------------------------");
			p.sendMessage(MessagePrefix.KDFERROR, "Er ging iets fout! Meld dit aub bij een staff lid!");
			p.sendMessage(MessagePrefix.KDFERROR, "Vermeld deze informatie: IOException - Verwacht probleem: Missende Schematic");
		} catch (com.sk89q.worldedit.data.DataException e) {
			Logger.ERROR.log("------------------------- WORLDEDIT PASTE PROBLEM -------------------------");
			Logger.ERROR.log("Location: " + getLocation(loc));
			Logger.ERROR.log("By player: " + p.getName() + " / " + p.getUuid());
			e.printStackTrace();
			Logger.ERROR.log("------------------------- WORLDEDIT PASTE PROBLEM -------------------------");
			p.sendMessage(MessagePrefix.KDFERROR, "Er ging iets fout! Meld dit aub bij een staff lid!");
			p.sendMessage(MessagePrefix.KDFERROR,
					"Vermeld deze informatie: DataException - Pasting a building caused exception: " + getDate() + ", "
							+ getLocation(loc));
		} catch (MaxChangedBlocksException e) {
			Logger.ERROR.log("------------------------- WORLDEDIT PASTE PROBLEM -------------------------");
			Logger.ERROR.log("Location: " + getLocation(loc));
			Logger.ERROR.log("By player: " + p.getName() + " / " + p.getUuid());
			e.printStackTrace();
			Logger.ERROR.log("------------------------- WORLDEDIT PASTE PROBLEM -------------------------");
			p.sendMessage(MessagePrefix.KDFERROR, "Er ging iets fout! Meld dit aub bij een staff lid!");
			p.sendMessage(MessagePrefix.KDFERROR,
					"Vermeld deze informatie: MaxChangedBlocksException - Pasting a building caused exception: "
							+ getDate() + ", " + getLocation(loc));
		} catch(Exception e)  {
			
			p.sendMessage(MessagePrefix.KDFERROR, "Er ging iets fout! Meld dit aub direct bij een staff lid!");
			p.sendMessage(MessagePrefix.KDFERROR,
					"Vermeld deze informatie: Exception");
			
			Logger.ERROR.log("------------------------- WORLDEDIT PASTE PROBLEM -------------------------");
			Logger.ERROR.log("Exception: " + e);
			Logger.ERROR.log("Location: " + getLocation(loc));
			Logger.ERROR.log("By player: " + p.getName() + " / " + p.getUuid());
			e.printStackTrace();
			Logger.ERROR.log("------------------------- WORLDEDIT PASTE PROBLEM -------------------------");
		}
	}
	
	public void pasteSchematicSilence(KingdomFactionsPlayer p, String file, Location loc, Building building) {
		File schematic = new File("plugins/WorldEdit/schematics/" + file);
		EditSession es = new EditSession(new BukkitWorld(p.getPlayer().getWorld()), 999999999);
		
		try {
			@SuppressWarnings("deprecation")
			CuboidClipboard cc = CuboidClipboard.loadSchematic(schematic);
			cc.paste(es, new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), false);
			
			
			
		} catch (IOException e) {
		} catch (com.sk89q.worldedit.data.DataException e) {
		} catch (MaxChangedBlocksException e) {
		}
	}
	
	public String getDate() {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy:MM:dd:hh:mm:ss");
		return ft.format(date);
	}

	public String getLocation(Location loc) {
		return loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ();
	}

	public String getSchematic(Kingdom k, BuildLevel l, BuildingType b) {
		return ConfigModule.getInstance().getFile(ConfigModule.SCHEMATICS).getConfig()
				.getString(k.getType().toString() + "." + b.toString() + "_" + l.getLevel());
	}

	public Location getNewLocation(Location oldLocation) {
		return new Location(oldLocation.getWorld(), oldLocation.getBlockX(), oldLocation.getBlockY(),
				oldLocation.getBlockZ());
	}

	public Entity[] getNearbyEntities(Location l, int radius) {
		int chunkRadius = radius < 16 ? 1 : (radius - radius % 16) / 16;
		HashSet<Entity> radiusEntities = new HashSet<>();
		for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
			for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
				int x = (int) l.getX();
				int y = (int) l.getY();
				int z = (int) l.getZ();
				for (Entity e : new Location(l.getWorld(), x + chX * 16, y, z + chZ * 16).getChunk().getEntities()) {
					if ((e.getLocation().distance(l) <= radius) && (e.getLocation().getBlock() != l.getBlock())) {
						radiusEntities.add(e);
					}
				}
			}
		}
		return (Entity[]) radiusEntities.toArray(new Entity[radiusEntities.size()]);
	}

	public void sendTitle(KingdomFactionsPlayer player, Title t, TitleDuration d, String message) {
		switch (t) {
		case SUBTITLE:
			switch (d) {
			case LONG:
				Utils.getInstance().sendSubTitle(player, message, 60, 160, 10);
				break;
			case MEDIUM:
				Utils.getInstance().sendSubTitle(player, message, 40, 80, 40);
				break;
			case SHORT:
				Utils.getInstance().sendSubTitle(player, message, 20, 40, 20);
				break;
			default:
				break;

			}
			break;
		case TITLE:
			switch (d) {
			case LONG:
				Utils.getInstance().sendTitle(player, message, 60, 160, 10);
				break;
			case MEDIUM:
				Utils.getInstance().sendTitle(player, message, 40, 80, 40);
				break;
			case SHORT:
				Utils.getInstance().sendTitle(player, message, 20, 40, 20);
				break;
			default:
				break;

			}

		}
	}

	public void sendMessage(MessagePrefix p, KingdomFactionsPlayer player, String message) {
		switch (p) {
		case DEBUG:
			player.sendMessage(ChatColor.YELLOW + "[DEBUG] " + ChatColor.GRAY + message);
			break;
		case KDFERROR:
			player.sendMessage(Messages.getInstance().getWarning() + message);
			break;
		case KINGDOMFACTIONS:
			player.sendMessage(Messages.getInstance().getPrefix() + message);
			break;
		}

	}

	private final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private SecureRandom rnd = new SecureRandom();

	public String generateRandomString(int size) {
		int l = size;
		StringBuilder sb = new StringBuilder(l);
		for (int i = 0; i < l; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public static final int FACTION = 30;
	public static final int NEXUS = 31;
	public static final int BUILDING = 29;
	public static final int SWITCH = 5;

	public boolean equalsLocation(Location loc1, Location loc2) {
		return (loc1.getBlockX() == loc2.getBlockX() && loc1.getBlockY() == loc2.getBlockY()
				&& loc1.getBlockZ() == loc2.getBlockZ());
	}

	public List<Location> drawCircle(Location loc, Integer radius, Integer height, Boolean hollow, Boolean sphere,
			int plus_y) {
		List<Location> circleblocks = new ArrayList<Location>();
		Integer r = radius;
		Integer h = height;
		int cx = loc.getBlockX();
		int cy = loc.getBlockY();
		int cz = loc.getBlockZ();
		for (int x = cx - r.intValue(); x <= cx + r.intValue(); x++) {
			for (int z = cz - r.intValue(); z <= cz + r.intValue(); z++) {
				for (int y = sphere.booleanValue() ? cy - r.intValue() : cy; y < (sphere.booleanValue()
						? cy + r.intValue() : cy + h.intValue()); y++) {
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z)
							+ (sphere.booleanValue() ? (cy - y) * (cy - y) : 0);
					if ((dist < r.intValue() * r.intValue())
							&& ((!hollow.booleanValue()) || (dist >= (r.intValue() - 1) * (r.intValue() - 1)))) {
						Location l = new Location(loc.getWorld(), x, y + plus_y, z);
						circleblocks.add(l);
					}
				}
			}
		}
		return circleblocks;
	}

	public ArrayList<String> getLore(String lore) {
		ArrayList<String> newlore = new ArrayList<String>();
		newlore.add(lore);
		return newlore;
	}

	public void playParticle(Location location, ParticleEffect effect) {
		for (Entity e : this.getNearbyEntities(location, 100)) {
			try {
				effect.sendToPlayer(PlayerModule.getInstance().getPlayer(e).getPlayer(), location, 0.4F, 0.4F, 0.4F,
						1.0F, 20);
			} catch (PlayerException e1) {
			} catch (Exception e1) {
			}
		}
	}

	public ArrayList<String> convert(ArrayList<Object> list) {
		ArrayList<String> temp = new ArrayList<String>();
		for (Object o : list) {
			if (o instanceof String) {
				String s = (String) o;
				temp.add(s);
			}

		}
		return temp;
	}

	public String locationToDbString(Location loc) {
		return loc.getBlockX() + "-" + loc.getBlockY() + "-" + loc.getBlockZ() + "-" + loc.getPitch() + "-"
				+ loc.getYaw() + "-" + loc.getWorld().getName();
	}

	public Location DbStringToLocation(String s) {
		String[] strings = s.split("-");
		int x, y, z;
		try {
		x = Integer.parseInt(strings[0]);
		y = Integer.parseInt(strings[1]);
		z = Integer.parseInt(strings[2]);
		float pitch, yaw;
		pitch = Float.parseFloat(strings[3]);
		yaw = Float.parseFloat(strings[4]);
		World w = Bukkit.getWorld(strings[5]);
		return new Location(w, x, y, z, yaw, pitch);
		} catch(NumberFormatException e) {
		}
		return null;
		
	}

	public World getOverWorld() {
		try {
			return Bukkit.getWorld(KingdomFactionsPlugin.getInstance().getDataManager().getString("worlds.overworld"));
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public World getMiningWorld() {
		try {
			return Bukkit
					.getWorld(KingdomFactionsPlugin.getInstance().getDataManager().getString("worlds.miningworld"));
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getLocationString(Location loc) {
		return loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ();
	}

	public void strikeLighting(Location location, boolean damage) {
		if (damage) {
			location.getWorld().strikeLightning(location);
			return;
		}
		location.getWorld().strikeLightningEffect(location);
	}
	
	
	public void strikeRandomLightning(Location loc, int radius, boolean damage) {
          Location loca = new Randomizer<Location>(this.drawCircle(loc, radius, 1, false, false, 0)).result();
			this.strikeLighting(loca, damage);

	}
}