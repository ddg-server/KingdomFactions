package nl.dusdavidgames.kingdomfactions.modules.time;

import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class TimeHelper {

	@Getter
    private static TimeHelper instance;

    public TimeHelper() {
        instance = this;
    }

    public void updateTime(KingdomFactionsPlayer p) {
        if (p.getStatisticsProfile().getLastUpdate() == 0) {
            p.getStatisticsProfile().setLastUpdate(System.currentTimeMillis());
        }
        long timediff = (System.currentTimeMillis() - p.getStatisticsProfile().getLastUpdate());
        int pretime = (int) (timediff / 1000);
        long time = p.getStatisticsProfile().getSecondsConnected() + pretime;
        p.getStatisticsProfile().setSecondsConnected(time);
        p.getStatisticsProfile().setLastUpdate(System.currentTimeMillis());
    }

    /**
     * @param ms milliseconds
     * @returns a list of time units
     * days:hours:minutes:seconds
     */
    public int[] translateTime(long ms) {
        int day = (int) TimeUnit.SECONDS.toDays(ms);
        int hours = (int) (TimeUnit.SECONDS.toHours(ms) - (day * 24));
        int minute = (int) (TimeUnit.SECONDS.toMinutes(ms) - (TimeUnit.SECONDS.toHours(ms) * 60));
        int second = (int) (TimeUnit.SECONDS.toSeconds(ms) - (TimeUnit.SECONDS.toMinutes(ms) * 60));
		return new int[]{day, hours, minute, second};
    }

}
