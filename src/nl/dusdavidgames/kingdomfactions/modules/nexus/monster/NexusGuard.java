package nl.dusdavidgames.kingdomfactions.modules.nexus.monster;

import lombok.Data;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_9_R2.EnumItemSlot;
import nl.dusdavidgames.kingdomfactions.modules.monster.GuardType;
import nl.dusdavidgames.kingdomfactions.modules.monster.IGuard;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Item;
import nl.dusdavidgames.kingdomfactions.modules.utils.nms.KDFEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;

import java.util.HashMap;

public @Data class NexusGuard implements IGuard {

	private boolean alive;
	private Location location;
	private Entity entity;
	private Nexus nexus;
	private GuardType type;

	@Override
	public void spawn() {
		switch (type) {
		case SKELETON:
			Skeleton s = getLocation().getWorld().spawn(this.getLocation(), Skeleton.class);
			s.setCustomName(ChatColor.RED + "Nexus Wachter");
			s.setCustomNameVisible(true);
			this.entity = s;
			KDFEntity ke = new KDFEntity(entity);
			HashMap<Enchantment, Integer> bowE = new HashMap<Enchantment, Integer>();
			bowE.put(Enchantment.ARROW_DAMAGE, 5);
			bowE.put(Enchantment.ARROW_FIRE, 1);
			bowE.put(Enchantment.ARROW_KNOCKBACK, 2);
			ke.setItem(EnumItemSlot.MAINHAND,
					Item.getInstance().getItem(Material.BOW, ChatColor.RED + "Nexus Wachter Boog", 1, bowE, true));
			ke.setItem(EnumItemSlot.OFFHAND,
					Item.getInstance().getItem(Material.SHIELD, ChatColor.RED + "Nexus Wachter Schild", 1));
			break;
		case ZOMBIE:
			Zombie z = getLocation().getWorld().spawn(this.getLocation(), Zombie.class);
			z.setCustomName(ChatColor.RED + "Nexus Wachter");
			z.setCustomNameVisible(true);
			this.entity = z;
			KDFEntity kez = new KDFEntity(entity);
			HashMap<Enchantment, Integer> swordE = new HashMap<Enchantment, Integer>();
			swordE.put(Enchantment.DAMAGE_ALL, 5);
			swordE.put(Enchantment.FIRE_ASPECT, 2);
			swordE.put(Enchantment.KNOCKBACK, 3);
			kez.setItem(EnumItemSlot.MAINHAND,
					Item.getInstance().getItem(Material.BOW, ChatColor.RED + "Nexus Wachter Zwaard", 1, swordE, true));
			kez.setItem(EnumItemSlot.OFFHAND,
					Item.getInstance().getItem(Material.SHIELD, ChatColor.RED + "Nexus Wachter Schild", 1));
			break;
		}
		KDFEntity armor = new KDFEntity(entity);
		HashMap<Enchantment, Integer> armorE = new HashMap<Enchantment, Integer>();
		armorE.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		armorE.put(Enchantment.DURABILITY, 3);
		armorE.put(Enchantment.THORNS, 3);
		armor.setItem(EnumItemSlot.FEET, Item.getInstance().getItem(Material.DIAMOND_BOOTS,
				ChatColor.RED + "Wachter's Schoenen", 1, armorE, true));
		armor.setItem(EnumItemSlot.LEGS, Item.getInstance().getItem(Material.DIAMOND_LEGGINGS,
				ChatColor.RED + "Wachter's Broek", 1, armorE, true));
		armor.setItem(EnumItemSlot.CHEST, Item.getInstance().getItem(Material.DIAMOND_CHESTPLATE,
				ChatColor.RED + "Wachter's Kuras", 1, armorE, true));
		armor.setItem(EnumItemSlot.HEAD,
				Item.getInstance().getItem(Material.DIAMOND_HELMET, ChatColor.RED + "Wachter's Helm", 1, armorE, true));

	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return location;
	}

	@Override
	public Entity getEntity() {
		// TODO Auto-generated method stub
		return entity;
	}

	@Override
	public net.minecraft.server.v1_9_R2.Entity getNMSEntity() {
		// TODO Auto-generated method stub

		return ((CraftEntity) getEntity()).getHandle();
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return alive;
	}

	@Override
	public GuardType getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public INexus getNexus() {
		// TODO Auto-generated method stub
		return nexus;
	}

	public void kill() {
		this.getEntity().remove();
		setAlive(false);
		this.nexus.getGuards().remove(this);

	}

	public void setAlive(boolean alive) {
		// TODO Auto-generated method stub
		this.alive = alive;

	}

	public void setTarget(LivingEntity entity) {
		((Monster) getEntity()).setTarget(entity);

	}

	public void setTarget(KingdomFactionsPlayer player) {
		this.setTarget(player.getPlayer());

	}

	@Override
	public void remove() {
		this.nexus.getGuards().remove(this);
		
	}

}
