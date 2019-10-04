package nl.dusdavidgames.kingdomfactions.modules.utils.rank;

public abstract class Rank {

	public abstract String getName();
    public abstract boolean isStaff();
    public String getPermission() {
    	return "kingdomfactions.rank." + getName();
    }
}
