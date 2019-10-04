package nl.dusdavidgames.kingdomfactions.modules.kingdom.menu;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;

public class KingdomMenuModule {

	
	
	public KingdomMenuModule() {
		KingdomFactionsPlugin.getInstance().registerListener(new KingdomMenu());
	}
	
	
	private static @Getter @Setter KingdomMenuModule instance;
	
	private @Getter ArrayList<KingdomItem> items = new ArrayList<KingdomItem>();
	
	
	public void init() {
		
	}
}
