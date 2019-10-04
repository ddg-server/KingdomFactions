package nl.dusdavidgames.kingdomfactions.modules.player.deathban;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

public class DeathBan {

	private @Getter UUID uuid;
	private @Getter String name;
	private @Getter long unban;

	private @Getter int minutes; 
	public DeathBan(String name, UUID uuid, int minutes) {
		this.uuid = uuid;
		this.name = name;
		this.minutes = minutes;
		long timeInmunites = minutes * 60;//minutes times 60 is time in seconds
		long currentTime = System.currentTimeMillis() / 1000;//system time / 1000 is sys time in seconds
		long unbanTime = currentTime + timeInmunites;//sys time in seconds + banTime in seconds is unban time
		this.unban = unbanTime;
	}

	public void unban() {
		DeathBanModule.getInstance().getBans().remove(this);
	}

	public String getMessage() {
		long banleft = this.unban - (System.currentTimeMillis() / 1000);
		int seconds = (int) banleft;
		int day = (int) TimeUnit.SECONDS.toDays(seconds);
		@SuppressWarnings("unused")
		long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
		long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
		long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
		return ChatColor.RED + "Je bent dood gegaan! Je moet nog " + minute + " minuten en " + second
				+ " seconden wachten!";
	}

	public boolean handleBan() {
		long banleft = this.unban - (System.currentTimeMillis() / 1000);
		if (banleft <= 0) {
			DeathBanModule.getInstance().unRegister(this);
			return false;
		} else {
			return true;
		}
	}

	public String getTime() {
		long banleft = this.unban - (System.currentTimeMillis() / 1000);
		int seconds = (int) banleft;
		int day = (int) TimeUnit.SECONDS.toDays(seconds);
		@SuppressWarnings("unused")
		long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
		long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
		long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
		return minute + " minuten, " + second + " seconden";
	}
	
	
	public boolean isActive() {
		long banleft = this.unban - (System.currentTimeMillis() / 1000);
		return banleft > 0;
	}

}
