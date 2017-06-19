package nl.dusdavidgames.kingdomfactions.modules.kingdom.command;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;

public class SpawnCommand extends KingdomFactionsCommand{

	public SpawnCommand(String name, String permission, String info, String usage) {
		super(name, permission, info, usage, false, false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() throws KingdomFactionsException {
		if(getPlayer().getKingdom() != null) {
			if(getPlayer().getKingdom().getType() != KingdomType.GEEN) {
				
			}
		}
		
	}

}
