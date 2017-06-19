package nl.dusdavidgames.kingdomfactions.modules.nexus.build.menu;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.exception.nexus.NexusException;
import nl.dusdavidgames.kingdomfactions.modules.exception.value.ValueException;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings.BuildAction;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings.Building;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildingType;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoListener;

public class YesNoBuildingBuilder implements YesNoListener{

	
	
	
	public YesNoBuildingBuilder(BuildAction action) {
		this.action = action;
	}

	
	private @Getter BuildAction action;
	
	@Override
	public void onAgree(KingdomFactionsPlayer player) {
	 try {
		player.removeCoins(action.getPrice(), false);
		if(action.getType() == BuildingType.NEXUS) {
			if(action.getLevel() == BuildLevel.LEVEL_1) {
			Nexus nexus = player.getBuildAction().executeNexusAction(action.getLocation(), player);
		    nexus.getOwner().broadcast(Messages.getInstance().getPrefix(), "Er is een Nexus gebouwd door " + player.getName() + "!");
		    nexus.save();
		    player.getBuildAction().cancel();
			} else {
				Nexus n = player.getBuildAction().executeNexusAction();
				n.save();
				player.getBuildAction().cancel();
			}
		} else {
            	Building b = action.executeAction();
                b.save();
			}
            
	} catch (ValueException | NexusException e) {
		if(e instanceof ValueException) {
          player.sendMessage(Messages.getInstance().getPrefix() + "Je hebt onvoldoende coins voor deze actie!");
		} else {
			e.printStackTrace();
		}
	}
		
	}

	@Override
	public void onDeny(KingdomFactionsPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose(KingdomFactionsPlayer player) {
		// TODO Auto-generated method stub
		
	}
}
