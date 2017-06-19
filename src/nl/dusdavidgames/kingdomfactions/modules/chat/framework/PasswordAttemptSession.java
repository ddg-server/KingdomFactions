package nl.dusdavidgames.kingdomfactions.modules.chat.framework;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.action.IAction;

public class PasswordAttemptSession implements IAction {

	private KingdomFactionsPlayer player;
	private @Getter ChatChannel channel;
	
	public PasswordAttemptSession(KingdomFactionsPlayer player, ChatChannel channel) {
		this.channel = channel;
		this.player = player;
	}
	@Override
	public KingdomFactionsPlayer getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}


	
	
	private @Getter @Setter String password;
}
