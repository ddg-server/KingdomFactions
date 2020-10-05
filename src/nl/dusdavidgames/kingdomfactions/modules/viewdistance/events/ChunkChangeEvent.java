package nl.dusdavidgames.kingdomfactions.modules.viewdistance.events;

import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ChunkChangeEvent implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onChunkChange(PlayerMoveEvent event) {
        Location to = event.getTo();
        Location from = event.getFrom();
        if (to.getBlockX() == from.getBlockX() && to.getBlockZ() == from.getBlockZ()) {
            return;
        }
        Chunk toChunk = to.getChunk();
        Chunk fromChunk = from.getChunk();
        if (toChunk.getX() == fromChunk.getX() && toChunk.getZ() == fromChunk.getZ()) {
            return;
        }

        PlayerModule.getInstance().getPlayer(event.getPlayer()).handleViewDistance();
    }
}