package nl.dusdavidgames.kingdomfactions.modules.viewdistance;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomRank;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.viewdistance.events.ChunkChangeEvent;
import nl.dusdavidgames.kingdomfactions.modules.viewdistance.events.PlayerJoinEvent;

public class ViewDistanceModule {

	private static @Getter @Setter ViewDistanceModule instance;
	@SuppressWarnings("unused")
	private final int MAX_DISTANCE = 10;
	@SuppressWarnings("unused")
	private final int MIN_DISTANCE = 4;//3 afgesproken maar tps is bijna nooit precies 20 dus vandaar +1

	public ViewDistanceModule() {
		setInstance(this);
		
		KingdomFactionsPlugin.getInstance().registerListener(new ChunkChangeEvent());
		KingdomFactionsPlugin.getInstance().registerListener(new PlayerJoinEvent());
	}
    @Deprecated
	public void setViewDistance(KingdomFactionsPlayer player) {

		if (player.isStaff() || player.getKingdomRank().equals(KingdomRank.KONING)) {
		//	player.setViewDistance(MAX_DISTANCE);
			return;
		}
		
		//player.setViewDistance(getDistance());
	}
	/**
	private int getDistance(){
		double TPSrate = KingdomFactionsPlugin.getInstance().getServer().spigot().getTPS()[0] / 20 * 100;
				   
		double viewDistance = TPSrate / 100 * 7;
		
		double distance = (viewDistance + MIN_DISTANCE);
		
		if(distance > MAX_DISTANCE)
			return MAX_DISTANCE;
		
		return (int) distance;
	}
	*/
	}