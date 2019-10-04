package nl.dusdavidgames.kingdomfactions.modules.utils.book;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class ChatPieceHolder {

	
	public ChatPieceHolder(String text, ClickEvent click, HoverEvent hover) {
		this.text = text;
		this.click = click;
		this.hover = hover;
	}
	public ChatPieceHolder(String text) {
		this(text, null, null);
	}
	
	private @Setter @Getter String text;
	private @Setter @Getter ClickEvent click;
	private @Setter @Getter HoverEvent hover;
	
	
	
	public void setClickLink(String link) {
		setClick(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
	}
	public void setHoverText(String text) {
		setHover(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(text).create()));
	}
}
