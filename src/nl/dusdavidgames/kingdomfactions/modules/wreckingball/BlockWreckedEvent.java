package nl.dusdavidgames.kingdomfactions.modules.wreckingball;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Jan on 21-6-2017.
 */
public class BlockWreckedEvent extends BlockBreakEvent {

    public BlockWreckedEvent(Block block, Player player) {
        super(block, player);
    }

    //breaking blocks with a wrecking ball cannot be cancelled whatsoever
    @Override
    public boolean isCancelled() {
        return false;
    }

}
