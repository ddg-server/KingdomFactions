package nl.dusdavidgames.kingdomfactions.modules.viewdistance.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;

public class PlayerJoinEvent implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoinEvent(org.bukkit.event.player.PlayerJoinEvent event){
		PlayerModule.getInstance().getPlayer(event.getPlayer()).handleViewDistance();
	}
}