package nl.dusdavidgames.kingdomfactions.modules.utils.logger;

public class Logger {

	public static final Logger DEBUG = new Debugger();
	public static final Logger INFO = new Logger("[INFO]");
	public static final Logger ERROR = new Logger("[ERROR]");
	public static final Logger WARNING = new Logger("[WARNING]");
	public static final Logger CRITICAL = new Logger("[CRITICAL]");
	public static final Logger MEMLEAK =  new MemLeakHunter();
	public static final Logger BROADCAST = new Logger("[BROADCAST]");
	
	
	public Logger(String prefix) {
		this.prefix = prefix;
	}
	
	protected String prefix;
	
	
	public void log(String message) {
		System.out.println("[KingdomFactions] " + prefix + " " + message);
	}
}
