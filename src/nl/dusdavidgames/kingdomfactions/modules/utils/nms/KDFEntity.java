package nl.dusdavidgames.kingdomfactions.modules.utils.nms;

import lombok.Getter;
import net.minecraft.server.v1_9_R2.EnumItemSlot;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class KDFEntity {

	
	public KDFEntity(Entity entity) {
		this.entity = entity;
	}
	
	private @Getter Entity entity;
	
	
	public void setItem(EnumItemSlot slot, ItemStack item) {
		this.getNMSEntity().setEquipment(slot, CraftItemStack.asNMSCopy(item));
	}
	private net.minecraft.server.v1_9_R2.Entity getNMSEntity() {
		return ((CraftEntity)entity).getHandle();
	}
}
