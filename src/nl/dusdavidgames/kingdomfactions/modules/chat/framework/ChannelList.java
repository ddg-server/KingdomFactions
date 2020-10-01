package nl.dusdavidgames.kingdomfactions.modules.chat.framework;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.Iterator;

public class ChannelList extends ArrayList<ChatChannel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4913410788988340593L;
	
	
	
	
	
	public String toChannelList() {
	 StringBuilder b  = new StringBuilder();
	 Iterator<ChatChannel> c = this.iterator();
	 while(c.hasNext()) {
		 b.append(c.next().colouredName + ChatColor.GRAY + ", ");
	 }
	 return b.toString();
	 
	}

}
