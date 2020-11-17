package nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.guardian;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.CapitalNexus;
import nl.dusdavidgames.kingdomfactions.modules.monster.GuardType;
import nl.dusdavidgames.kingdomfactions.modules.monster.IGuard;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Item;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;

import java.util.HashMap;

public class CapitalGuard implements IGuard {

	private Location location;

	private Entity entity;
	private boolean alive;
	private GuardType type;
	private CapitalNexus nexus;

	
	public CapitalGuard(CapitalNexus nexus, GuardType type, Location location) {
		this.nexus = nexus;
		this.type = type;
		this.location = location;
		this.alive = true;
		((CapitalNexus)getNexus()).getGuards().add(this);
	}
	public Location getLocation() {
		return location;
	}

	public void spawn() {
		switch (type) {
		case SKELETON:
			Skeleton s = getLocation().getWorld().spawn(this.getLocation(), Skeleton.class);
			s.setCustomName(ChatColor.RED + "Nexus Wachter");
			s.setCustomNameVisible(true);
			this.entity = s;
			
			HashMap<Enchantment, Integer> bowE = new HashMap<Enchantment, Integer>();
			bowE.put(Enchantment.ARROW_DAMAGE, 5);
			bowE.put(Enchantment.ARROW_FIRE, 1);
			bowE.put(Enchantment.ARROW_KNOCKBACK, 2);
			
			/**
			KDFEntity ke = new KDFEntity(entity);
			ke.setItem(EnumItemSlot.MAINHAND,
					Item.getInstance().getItem(Material.BOW, ChatColor.RED + "Nexus Wachter Boog", 1, bowE, true));
			ke.setItem(EnumItemSlot.OFFHAND,
					Item.getInstance().getItem(Material.SHIELD, ChatColor.RED + "Nexus Wachter Schild", 1));
					*/
			
			EntityEquipment skeletonEquipments = ((LivingEntity) this.entity).getEquipment();
			skeletonEquipments.setItemInMainHand(Item.getInstance().getItem(Material.BOW, ChatColor.RED + "Nexus Wachter Boog", 1, bowE, true));
			//skeletonEquipments.setItemInOffHand(Item.getInstance().getItem(Material.SHIELD, ChatColor.RED + "Nexus Wachter Schild", 1));
			
			break;
		case ZOMBIE:
			Zombie z = getLocation().getWorld().spawn(this.getLocation(), Zombie.class);
			z.setCustomName(ChatColor.RED + "Nexus Wachter");
			z.setCustomNameVisible(true);
			this.entity = z;
			
			/**
			KDFEntity kez = new KDFEntity(entity);
			HashMap<Enchantment, Integer> swordE = new HashMap<Enchantment, Integer>();
			swordE.put(Enchantment.DAMAGE_ALL, 5);
			swordE.put(Enchantment.FIRE_ASPECT, 2);
			swordE.put(Enchantment.KNOCKBACK, 3);
			kez.setItem(EnumItemSlot.MAINHAND,
					Item.getInstance().getItem(Material.BOW, ChatColor.RED + "Nexus Wachter Zwaard", 1, swordE, true));
			kez.setItem(EnumItemSlot.OFFHAND,
					Item.getInstance().getItem(Material.SHIELD, ChatColor.RED + "Nexus Wachter Schild", 1));
			*/
			
			HashMap<Enchantment, Integer> swordE = new HashMap<Enchantment, Integer>();
			swordE.put(Enchantment.DAMAGE_ALL, 5);
			swordE.put(Enchantment.FIRE_ASPECT, 2);
			swordE.put(Enchantment.KNOCKBACK, 3);
			
			EntityEquipment zombieEquipments = ((LivingEntity) this.entity).getEquipment();
			zombieEquipments.setItemInMainHand(Item.getInstance().getItem(Material.DIAMOND_SWORD, ChatColor.RED + "Nexus Wachter Zwaard", 1, swordE, true));
		//	zombieEquipments.setItemInOffHand(Item.getInstance().getItem(Material.SHIELD, ChatColor.RED + "Nexus Wachter Schild", 1));
			//Against sun light
			break;
		}
		
		HashMap<Enchantment, Integer> armorE = new HashMap<Enchantment, Integer>();
		armorE.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		armorE.put(Enchantment.DURABILITY, 3);
		armorE.put(Enchantment.THORNS, 3);
		
		/**
		KDFEntity armor = new KDFEntity(entity);
		
		armor.setItem(EnumItemSlot.FEET, Item.getInstance().getItem(Material.DIAMOND_BOOTS,
				ChatColor.RED + "Wachter's Schoenen", 1, armorE, true));
		armor.setItem(EnumItemSlot.LEGS, Item.getInstance().getItem(Material.DIAMOND_LEGGINGS,
				ChatColor.RED + "Wachter's Broek", 1, armorE, true));
		armor.setItem(EnumItemSlot.CHEST, Item.getInstance().getItem(Material.DIAMOND_CHESTPLATE,
				ChatColor.RED + "Wachter's Kuras", 1, armorE, true));
		armor.setItem(EnumItemSlot.HEAD,
				Item.getInstance().getItem(Material.DIAMOND_HELMET, ChatColor.RED + "Wachter's Helm", 1, armorE, true));
				*/
		EntityEquipment equipments = ((LivingEntity) this.entity).getEquipment();
		equipments.setBoots(Item.getInstance().getItem(Material.DIAMOND_BOOTS,
				ChatColor.RED + "Wachter's Schoenen", 1, armorE, true));
		equipments.setLeggings(Item.getInstance().getItem(Material.DIAMOND_LEGGINGS,
				ChatColor.RED + "Wachter's Broek", 1, armorE, true));
		equipments.setChestplate(Item.getInstance().getItem(Material.DIAMOND_CHESTPLATE,
				ChatColor.RED + "Wachter's Kuras", 1, armorE, true));
		equipments.setHelmet(Item.getInstance().getItem(Material.DIAMOND_HELMET, ChatColor.RED + "Wachter's Helm", 1, armorE, true));
		
	}

	public Entity getEntity() {
		return entity;
	}

	public net.minecraft.server.v1_9_R2.Entity getNMSEntity() {
		return ((CraftEntity) entity).getHandle();
	}

	public boolean isAlive() {
		return alive;
	}

	public GuardType getType() {
		return type;
	}

	@Override
	public INexus getNexus() {
		return nexus;
	}
	public void kill() {
		setAlive(false);
		getEntity().remove();
		this.nexus.getGuards().remove(this);
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
		
	}
	public void setTarget(KingdomFactionsPlayer player) {
		this.setTarget(player.getPlayer().getPlayer());
		
	}
	public void setTarget(LivingEntity entity) {
	((Monster)getEntity()).setTarget(entity);
	}
	@Override
	public void remove() {
		this.nexus.getGuards().remove(this);
	}

}
