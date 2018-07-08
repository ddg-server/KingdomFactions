package nl.dusdavidgames.kingdomfactions.modules.permission;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class PermissionModule {

	private static @Getter @Setter PermissionModule instance;

	public PermissionModule() {
		setInstance(this);
	}

	public boolean isStaff(KingdomFactionsPlayer p) {
		return p.hasPermission("kingdomfactions.role.lead") || p.hasPermission("kingdomfactions.role.pl") 
			|| p.hasPermission("kingdomfactions.role.mod") || p.hasPermission("kingdomfactions.role.support");
	}
	
	public StaffList getStaffMembers() {
		StaffList list = new StaffList();
		for(KingdomFactionsPlayer player : PlayerModule.getInstance().getPlayers()) {
			if(!player.isStaff()) continue;
			list.add(player);
		}
		return list;
	}

}
