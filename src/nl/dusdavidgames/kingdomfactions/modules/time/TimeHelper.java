package nl.dusdavidgames.kingdomfactions.modules.time;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

import java.util.concurrent.TimeUnit;

public class TimeHelper {
    private static @Getter @Setter TimeHelper instance;
	public TimeHelper() {
		setInstance(this);
	}
	
	public void updateTime(KingdomFactionsPlayer p) {
		if (	p.getStatisticsProfile().getLastUpdate() == 0) {
			p.getStatisticsProfile().setLastUpdate(System.currentTimeMillis());
		}
		long timediff = (System.currentTimeMillis() - 	p.getStatisticsProfile().getLastUpdate());
		int pretime = (int) (timediff / 1000);
		long time = p.getStatisticsProfile().getSecondsConnected() + pretime;
		p.getStatisticsProfile().setSecondsConnected(time);
		p.getStatisticsProfile().setLastUpdate(System.currentTimeMillis());
	}
	 
	/**
	 * 
	 * @param ms
	 * @returns a list of time units
	 * days:hours:minutes:seconds
	 */
	 public int[] translateTime(long ms) {
			int day = (int)TimeUnit.SECONDS.toDays(ms);        
			 int hours = (int) (TimeUnit.SECONDS.toHours(ms) - (day *24));
			 int minute = (int) (TimeUnit.SECONDS.toMinutes(ms) - (TimeUnit.SECONDS.toHours(ms)* 60));
			 int second = (int) (TimeUnit.SECONDS.toSeconds(ms) - (TimeUnit.SECONDS.toMinutes(ms) *60));
			int[] i= {day, hours, minute, second};
			 return i;
	 }
	 
}
