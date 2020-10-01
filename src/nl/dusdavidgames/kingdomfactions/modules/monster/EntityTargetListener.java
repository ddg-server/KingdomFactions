package nl.dusdavidgames.kingdomfactions.modules.monster;

import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.CapitalNexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityTargetListener implements Listener {

	
	
	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		if(!(e.getTarget() instanceof Player)) return;
			KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer((Player) e.getTarget());
	        if(MonsterModule.getInstance().isGuard((LivingEntity) e.getEntity())) return;
	        if(MonsterModule.getInstance().isGuard((LivingEntity)e.getTarget())) e.setCancelled(true);
	        INexus guard = MonsterModule.getInstance().getGuard((LivingEntity) e.getEntity()).getNexus();
	        switch(guard.getType()) {
			case CAPITAL:
				if(player.getKingdom().getType() == ((CapitalNexus) guard).getKingdom()) {
					e.setCancelled(true);
				}
				break;
			case FACTION:
				if(player.getFaction().equals(((Nexus)guard).getOwner())) {
					e.setCancelled(true);
				}
				break;
	        
	        }
		}
	}
