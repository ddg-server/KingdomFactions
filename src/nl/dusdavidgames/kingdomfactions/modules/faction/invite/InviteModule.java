package nl.dusdavidgames.kingdomfactions.modules.faction.invite;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class InviteModule {

	public InviteModule() {
		setInstance(this);
	}

	private static @Getter @Setter InviteModule instance;


	public Invite invite(Faction f, KingdomFactionsPlayer player, KingdomFactionsPlayer inviter) {
		Invite i = new Invite(player, inviter, f);
		f.getInvites().add(i);
		return i;
	}
	public Invite getInvite(Faction f, KingdomFactionsPlayer player) {
		for(Invite i : f.getInvites()) {
			if(i.getPlayer().equals(player)) {
				return i;
			}
		}
		return null;
	}
}
