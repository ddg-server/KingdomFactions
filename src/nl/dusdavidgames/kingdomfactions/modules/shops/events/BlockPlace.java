package nl.dusdavidgames.kingdomfactions.modules.shops.events;

import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockPlace implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {

		if (event.getItemInHand().getType() != Material.MOB_SPAWNER)
			return;

		ItemStack is = event.getItemInHand();
		if (!is.hasItemMeta())
			return;

		ItemMeta im = is.getItemMeta();

		if (!im.hasLore())
			return;

		String lore = im.getLore().toString();

		if (!lore.contains("Spawner:"))
			return;

		EntityType entity = getEntity(lore);

		if (entity == EntityType.AREA_EFFECT_CLOUD) {
			event.setCancelled(true);
			return;
		}

		setSpawner(event.getBlock(), entity);
	}

	private EntityType getEntity(String lore) {
		String lores = lore.toUpperCase();
		if (lores.contains("[SPAWNER: "))
			lores = lores.replace("[SPAWNER: ", "");
		if (lores.contains("]"))
			lores = lores.replace("]", "");

		Logger.DEBUG.log("LORE: " + lores);

		for (EntityType type : EntityType.values()) {
			String name = type + "";
			if (lores.equalsIgnoreCase(name)) {
				return type;
			}
		}
		return EntityType.AREA_EFFECT_CLOUD;
	}

	public void setSpawner(Block block, EntityType ent) {
		BlockState blockState = block.getState();
		CreatureSpawner spawner = ((CreatureSpawner) blockState);
		spawner.setSpawnedType(ent);
		blockState.update();
	}
}