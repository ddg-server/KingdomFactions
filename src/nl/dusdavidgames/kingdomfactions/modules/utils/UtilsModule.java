package nl.dusdavidgames.kingdomfactions.modules.utils;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoConfirmationModule;

public class UtilsModule {

	private static @Getter @Setter UtilsModule instance;

	public UtilsModule() {
		setInstance(this);
		new NameHistory();
		new Messages();
		new Utils();
		new Item();
		KingdomFactionsPlugin.getInstance().registerListener(new YesNoConfirmationModule());
	}

}
