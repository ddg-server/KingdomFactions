package nl.dusdavidgames.kingdomfactions.modules.kingdom.menu;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;

import java.util.ArrayList;

public class KingdomMenuModule {

	
	
	public KingdomMenuModule() {
		KingdomFactionsPlugin.getInstance().registerListener(new KingdomMenu());
	}
	
	
	private static @Getter @Setter KingdomMenuModule instance;
	
	private @Getter ArrayList<KingdomItem> items = new ArrayList<KingdomItem>();
	
	
	public void init() {
		
	}
}
