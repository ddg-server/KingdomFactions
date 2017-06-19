package nl.dusdavidgames.kingdomfactions.modules.server;

import lombok.Getter;

public enum ServerMode {
	CRITICAL("kingdomfactions.mode.critical", "Er ging iets fout! \n Gelieve ASAP een staff lid contacteren!"),
    LOCKDOWN("kingdomfactions.mode.lockdown", "Deze server is in LOCKDOWN"),
	TEST("kingdomfactions.mode.test", "Deze server is in TEST MODE"),
	EARLYBETA("kingdomfactions.mode.earlybeta", "&cDeze server is in EARLY BETA"),
	BETA("kingdomfactions.mode.beta", "&cDeze server is in de BETA!"),
	RELEASED("kingdomfactions.mode.released", "&aJe hebt geen toegang om deze server te joinen!"),
    RESTARTING("kingdomfactions.mode.restarting", "&aDe server is aan het restarten!");
	
	
	private @Getter String permission;
	private @Getter String message;
	ServerMode(String permission, String message) {
		this.permission = permission;
		this.message = message;
	}
	
	
	public static ServerMode getMode(String s) {
		for(ServerMode mode : ServerMode.values()) {
	       if(mode.toString().equalsIgnoreCase(s)) {
	    	   return mode;
	       }
		}
		return null;
	}
	public static String getMode(ServerMode s) {
		return s.toString();
	}
}
