package nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.listeners;

import org.bukkit.Bukkit;

import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionRank;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoListener;

public class LeaderListener implements YesNoListener {

	public LeaderListener(IPlayerBase other) {
		this.other = other;
	}

	private IPlayerBase other;

	@Override
	public void onAgree(KingdomFactionsPlayer player) {
		if (player.getMembershipProfile().isFactionLeader()) {
			player.setFactionRank(FactionRank.OFFICER);
			other.setFactionRank(FactionRank.LEADER);
			player.getFaction().broadcast(Messages.getInstance().getPrefix(),
					player.getName() + " heeft het Faction leiderschap over gedragen aan " + other.getName());
		}

		Bukkit.getScheduler().runTaskLater(KingdomFactionsPlugin.getInstance(), new Runnable() {

			@Override
			public void run() {
				player.getPlayer().closeInventory();
			}
		}, 5);
	}

	@Override
	public void onDeny(KingdomFactionsPlayer player) {
		// TODO Auto-generated method stub

		Bukkit.getScheduler().runTaskLater(KingdomFactionsPlugin.getInstance(), new Runnable() {

			@Override
			public void run() {
				player.getPlayer().closeInventory();
			}
		}, 5);
	}

	@Override
	public void onClose(KingdomFactionsPlayer player) {
		// TODO Auto-generated method stub

	}

}
