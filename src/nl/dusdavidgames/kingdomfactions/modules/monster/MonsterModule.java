package nl.dusdavidgames.kingdomfactions.modules.monster;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import org.bukkit.entity.LivingEntity;

public class MonsterModule {

	public MonsterModule() {
		setInstance(this);
	}

	private static @Getter @Setter MonsterModule instance;




	public IGuard getGuard(LivingEntity e) {
		for(INexus n : NexusModule.getInstance().getNexuses()) {
			for(IGuard guard : n.getGuards()) {
				if(guard.getEntity().equals(e)) {
					return guard;
				}
			}
		}
		return null;
	}
	public boolean isGuard(LivingEntity e) {
		return getGuard(e) != null;
	}
	
}


