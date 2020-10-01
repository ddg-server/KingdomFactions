package nl.dusdavidgames.kingdomfactions.modules.utils.book;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SpecialPage implements IPage {

	
	public SpecialPage() {
		this.lines = new ArrayList<BaseComponent>();
	}
	
	
	
	private @Setter @Getter List<BaseComponent> lines;
	
	
	public void setLine(int line, String text, ClickEvent c, HoverEvent h) {
	   TextComponent t= new TextComponent(text + "\n");
	   t.setClickEvent(c);
	   t.setHoverEvent(h);
	   this.lines.set(line,t);
	}
	public BaseComponent getLine(int line) {
		return this.lines.get(line);
	}
	
	public void addLine(String text) {
		this.addLine(text, null, null);
	}
	public void addLine(String text, ClickEvent c, HoverEvent h) {
		   TextComponent t= new TextComponent(text + "\n");
		   if(c != null) {
		   t.setClickEvent(c);
		   }
		   if(h != null) {
		   t.setHoverEvent(h);
		   }
		   this.lines.add(t);
	}

	public void addLine(ChatPieceHolder... pieces) {
		ArrayList<TextComponent> componentList = new ArrayList<TextComponent>();
		for(ChatPieceHolder h : pieces) {
			TextComponent t = new TextComponent(h.getText());
			t.setClickEvent(h.getClick());
			t.setHoverEvent(h.getHover());
			componentList.add(t);
		}
		componentList.add(new TextComponent(" " + "\n"));
		this.lines.add(new TextComponent((BaseComponent[]) componentList.toArray(new BaseComponent[componentList.size()])));
	}
	
	public void addLine(ChatPieceHolder holder) {
		this.addLine(new ChatPieceHolder[] { holder });
	}
	public void addBlankLine() {
		   TextComponent t= new TextComponent(" " + "\n");
		   this.lines.add(t);
	}
	public void clear() {
		this.lines.clear();
	}
	public Iterator<BaseComponent> iterator() {
		return this.lines.iterator();
	}
	

}
