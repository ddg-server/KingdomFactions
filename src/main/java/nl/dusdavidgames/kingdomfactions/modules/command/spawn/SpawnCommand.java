package nl.dusdavidgames.kingdomfactions.modules.command.spawn;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.settings.Setting;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.TeleportationAction;

/**
 * Created by Jan on 21-6-2017.
 */
public class SpawnCommand extends KingdomFactionsCommand {
    public SpawnCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {
    }

    @Override
    public void execute() throws KingdomFactionsException {
        if (Setting.ALLOW_SPAWN.isEnabled()) {
            KingdomFactionsPlayer player = getPlayer();
            player.sendMessage(Messages.getInstance().getPrefix() + "We teleporteren je naar de spawn in " + 20 + " seconden.");
            player.setAction(new TeleportationAction(player, player.getKingdom().getSpawn(), true, 20));
        } else {
            getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Het is momenteel niet mogelijk om het Spawn Commando te gebruiken!");
        }
    }

}
