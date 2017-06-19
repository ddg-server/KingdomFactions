package nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks;

import org.bukkit.ChatColor;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionRank;
import nl.dusdavidgames.kingdomfactions.modules.utils.IRank;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ChannelType;

public class FactionChannelRank implements ChannelRank{

	private @Getter String name;
	private @Getter ChatColor messageColour = ChatColor.GRAY;
	private @Getter int cooldown;

	private @Getter FactionRank rank;
	private boolean canSetMode = false;

	private @Getter boolean showName;
	
	public FactionChannelRank(FactionRank rank) {
		if (rank != null) {
			this.rank = rank;
			name = rank.getPrefix();
		
			if (rank == FactionRank.LEADER) {
				cooldown = 0;
				messageColour = ChatColor.WHITE;
				this.showName = true;
				canSetMode = true;
			} else if (rank == FactionRank.OFFICER){ 
				cooldown = 2;
				this.showName = true;
				messageColour = ChatColor.WHITE;
				
			} else {
				this.showName = false;
				cooldown = 5;
			}
		} else {
			this.showName = false;
			cooldown = 5;
		}
		

	}
	
	
	@Override
	public boolean canSetMode(){
		return canSetMode;
	}
	
	@Override
	public boolean showName(){
		return this.showName;
	}


	@Override
	public String getRawName() {
		// TODO Auto-generated method stub
		return this.rank.toString();
	}
	
	@Override
	public ChannelType getChannelType() {
		// TODO Auto-generated method stub
		return ChannelType.FACTION;
	}
	
	//	
	@Override
	public void recalcPrefix(IRank unkownRank) {
		if (unkownRank != null) {
		if(unkownRank instanceof FactionRank) {
	        FactionRank rank = (FactionRank) unkownRank;
	        
			this.rank = rank;
			if(rank == FactionRank.SPELER) {
				name = "";
			} else {
			name = rank.getPrefix();
			}
			if (rank == FactionRank.LEADER) {
				cooldown = 0;
				messageColour = ChatColor.WHITE;
				this.showName = true;
				canSetMode = true;
			} else if (rank == FactionRank.OFFICER){ 
				cooldown = 2;
				this.showName = true;
				messageColour = ChatColor.WHITE;
				
			} else {
				this.showName = false;
				cooldown = 5;
			}
		} else {
			this.showName = false;
			cooldown = 5;
		}
	}
}
}
