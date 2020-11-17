package nl.dusdavidgames.kingdomfactions.modules.monster;

import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public interface IGuard {

	

	Location getLocation();
    
	void spawn();
	
	Entity getEntity();

	net.minecraft.server.v1_9_R2.Entity getNMSEntity();
	
	boolean isAlive();
	
	GuardType getType();
	
	INexus getNexus();
	
	void kill();
	
	void setAlive(boolean alive);
	
	void setTarget(LivingEntity entity);
	
	void setTarget(KingdomFactionsPlayer player);
	
	void remove();
	
}
