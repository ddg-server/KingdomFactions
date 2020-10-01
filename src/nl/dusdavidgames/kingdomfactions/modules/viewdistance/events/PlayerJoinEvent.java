package nl.dusdavidgames.kingdomfactions.modules.viewdistance.events;

import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoinEvent(org.bukkit.event.player.PlayerJoinEvent event){
		PlayerModule.getInstance().getPlayer(event.getPlayer()).handleViewDistance();
	}
}