package nl.dusdavidgames.kingdomfactions.modules.scoreboard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.coins.event.CoinEditEvent;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.event.FactionSwitchEvent;
import nl.dusdavidgames.kingdomfactions.modules.influence.event.InfluenceEditEvent;
import nl.dusdavidgames.kingdomfactions.modules.mine.DelayedPlayerTeleportEvent;
import nl.dusdavidgames.kingdomfactions.modules.mine.MineTravelEvent;
import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.detection.TerritoryUpdateEvent;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.war.WarModule;
import nl.dusdavidgames.kingdomfactions.modules.war.event.WarDurationChangeEvent;
import nl.dusdavidgames.kingdomfactions.modules.war.event.WarStartEvent;
import nl.dusdavidgames.kingdomfactions.modules.war.event.WarStopEvent;

public class ScoreboardListeners implements Listener {

	@EventHandler
	public void onCoinEdit(CoinEditEvent e) {
		KingdomFactionsPlayer p = e.getPlayer();
		p.getScoreboard().editLine(2, ChatColor.GRAY + "Coins: " + ChatColor.RED + p.getStatisticsProfile().getCoins());
	}

	@EventHandler
	public void onInfluenceEdit(InfluenceEditEvent e) {
		KingdomFactionsPlayer p = e.getPlayer();
		p.getScoreboard().editLine(1,
				ChatColor.GRAY + "Influence: " + ChatColor.RED + p.getStatisticsProfile().getInfluence());
	}

	@EventHandler
	public void onFactionSwitch(FactionSwitchEvent e) {
		KingdomFactionsPlayer p = e.getPlayer();
		Faction f = e.getNewFaction();
		if (f != null) {
			p.getScoreboard().editLine(8, ChatColor.GRAY + "[" +p.getKingdom().getType().getColor()+ f.getName() + ChatColor.GRAY + "]");
		} else {
			p.getScoreboard().editLine(8, p.getKingdom().getType().getColor() + "Geen Faction!");
		}
	}
	
	@EventHandler
	public void onMineTravel(MineTravelEvent e) {
		e.getPlayer().getScoreboard().editLine(5, ChatColor.GRAY + "Wereld: " +ScoreboardModule.getInstance().getWorld(e.getPlayer().getLocation().getWorld()));
	}
    
	@EventHandler
	public void onTerritorySwitch(TerritoryUpdateEvent e) {
		e.getPlayer().getScoreboard().editLine(4, ScoreboardModule.getInstance()
				.getTerritory(e.getPlayer().getTerritoryId(), e.getPlayer().getKingdomTerritory()));
	}

	@EventHandler
	public void onWarStart(WarStartEvent e) {
		for (KingdomFactionsPlayer p : PlayerModule.getInstance().getPlayers()) {
			p.getScoreboard().editLine(12, ChatColor.GRAY + "Oorlog:");
			p.getScoreboard().editLine(11,
					ChatColor.GRAY + "Resterende tijd: " + WarModule.getInstance().getWar().getRemainingTime());
		}
	}

	@EventHandler
	public void onWarStop(WarStopEvent e) {
		for (KingdomFactionsPlayer p : PlayerModule.getInstance().getPlayers()) {
			p.getScoreboard().editLine(12, ChatColor.GRAY + "Oorlog:");
			p.getScoreboard().editLine(11, ChatColor.GRAY + "Geen Oorlog");
		}
	}

	@EventHandler
	public void onWarDurationChange(WarDurationChangeEvent e) {
		for (KingdomFactionsPlayer p : PlayerModule.getInstance().getPlayers()) {
			p.getScoreboard().editLine(12, ChatColor.GRAY + "Oorlog:");
			p.getScoreboard().editLine(11,
					ChatColor.GRAY + "Resterende tijd: " + WarModule.getInstance().getWar().getRemainingTime());
		}
	}
	
	
	@EventHandler
	public void onTeleport(DelayedPlayerTeleportEvent e) {
		e.getPlayer().updateScoreboard();
	}

}
