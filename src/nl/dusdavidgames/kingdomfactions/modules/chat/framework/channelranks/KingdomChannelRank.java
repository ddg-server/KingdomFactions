package nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomRank;
import nl.dusdavidgames.kingdomfactions.modules.utils.IRank;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ChannelType;
import org.bukkit.ChatColor;

public final class KingdomChannelRank implements ChannelRank {
	
	private @Getter String name;
	private @Getter ChatColor messageColour = ChatColor.GRAY;
	private @Getter int cooldown;
	
	private boolean canMute = false;
	private boolean canSetMode = false;

	
	private KingdomRank rank;
	public KingdomChannelRank(KingdomRank rank) {
		this.rank = rank;
		if (rank != null) {
			if(rank == KingdomRank.SPELER) {
				name = "";
			} else {
			name = rank.getPrefix();
			}
			if (rank == KingdomRank.KONING) {
				cooldown = 0;
				messageColour = ChatColor.WHITE;
				canMute = true;
				canSetMode = true;
			} else if (rank == KingdomRank.WACHTER){ 
				cooldown = 0;
				messageColour = ChatColor.WHITE;
				canMute = true;
			} else {
				cooldown = 3;
			}
		} else {
			cooldown = 5;
		}
	}
	
	@Override
	public boolean canMute(){
		return canMute;
	}
	
	@Override
	public boolean canSetMode(){
		return canSetMode;
	}
	
	@Override
	public boolean showName(){
		return true;
	}

	@Override
	public String getRawName() {
		// TODO Auto-generated method stub
		return this.rank.toString();
	}
	@Override
	public ChannelType getChannelType() {
		// TODO Auto-generated method stub
		return ChannelType.KINGDOM;
	}
	
	
	@Override
	public void recalcPrefix(IRank unkownRank) {
	if (unkownRank != null) {
		if(unkownRank instanceof KingdomRank) {
			KingdomRank rank = (KingdomRank) unkownRank;
			this.rank = rank;

				if(rank == KingdomRank.SPELER) {
					name = "";
				} else {
				name = rank.getPrefix();
				}
				if (rank == KingdomRank.KONING) {
					cooldown = 0;
					messageColour = ChatColor.WHITE;
					canMute = true;
					canSetMode = true;
				} else if (rank == KingdomRank.WACHTER){ 
					cooldown = 0;
					messageColour = ChatColor.WHITE;
					canMute = true;
				} else {
					cooldown = 3;
				}
			} else {
				cooldown = 5;
			}
		}

	}
}
