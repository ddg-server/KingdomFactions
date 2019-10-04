package nl.dusdavidgames.kingdomfactions.modules.buycraft;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.PurchaseSource;

public abstract class PurchasedPackage {
	
	
	
	public PurchasedPackage(String purchaseId, UUID uuid, int price, Date purchaseDate, String type, PurchaseSource source, boolean active, boolean requireOnline) {
		this.purchaseId = purchaseId;
		this.uuid = uuid;
		this.price = price;
		this.purchaseDate = purchaseDate;
		this.name = type;
		this.source = source;
		this.active = active;

		this.requireOnline = requireOnline;
	}

	
	private @Getter String purchaseId;
	private @Getter int price;
	
	private @Getter UUID uuid;
	
	private @Getter Date purchaseDate;

	private @Getter String name;
	
	private @Getter PurchaseSource source;
	
	private @Getter @Setter boolean active;

    private boolean requireOnline;
    
    
    public boolean doesRequireOnline() {
    	return requireOnline;
    }
	
	public abstract void execute();
}
