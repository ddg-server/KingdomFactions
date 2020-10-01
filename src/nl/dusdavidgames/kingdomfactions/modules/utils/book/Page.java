package nl.dusdavidgames.kingdomfactions.modules.utils.book;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Page implements IPage {

	public Page(List<String> lines) {
		this.lines = lines;
	}
	public Page(String... lines) {
		this.lines = Arrays.asList(lines);
	}
	public Page() {
		this.lines = new ArrayList<String>();
	}
	
	
	private @Setter @Getter ChatColor pageColor;
	
	
	

	
	
	
	@Setter @Getter List<String> lines;
	
	
	public void setLine(int line, String text) {
		this.lines.set(line, text);
	}
	public String getLine(int line) {
		return this.lines.get(line);
	}
	public void addLine(String line) {
		if(this.pageColor == null) {
		this.lines.add(line);
		} else {
			this.lines.add(this.pageColor + line);
		}
	}

	public void addBlankLine() {
		this.lines.add("");
	}
	public void clear() {
		this.lines.clear();
	}
	public Iterator<String> iterator() {
		return this.lines.iterator();
	}
	
	

}
