package nl.dusdavidgames.kingdomfactions.modules.utils.book;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftMetaBook;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookBuilder {

	public BookBuilder() {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta m = (BookMeta) book.getItemMeta();
		this.bookMeta = m;
	}

	private ArrayList<IPage> pages = new ArrayList<>();

	public BookBuilder setTitle(String title) {
		this.bookMeta.setTitle(title);
		return this;
	}

	public BookBuilder setAuthor(String name) {
		this.bookMeta.setAuthor(name);
		return this;
	}

	public BookBuilder addPage(String... page) {
		return this.addPage(new Page(page));
	}
	public BookBuilder addPages(IPage... pages) {
		for(IPage p : pages) {
			return this.addPage(p);
		}
		return this;
	}

	public BookBuilder addPage(IPage page) {
		this.pages.add(page);
		return this;
	}

	public BookBuilder setLore(List<String> lore) {
		this.bookMeta.setLore(lore);
		return this;
	}

	public BookBuilder setLore(String... list) {
		this.bookMeta.setLore(Arrays.asList(list));
		return this;
	}

	public void setPage(int i, Page page) {
		this.pages.set(i, page);
	}

	public IPage getPage(int i) {
		return this.pages.get(i);
	}

	private BookMeta bookMeta;

	public ItemStack build() {
		ItemStack i = new ItemStack(Material.WRITTEN_BOOK, 1);

	    this.createPages(this.pages);
		i.setItemMeta(this.bookMeta);
		return i;
	}

   private void createPages(List<IPage> list) {

		for (IPage unkownPage : list) {

			if (unkownPage instanceof Page) {
				Page p = (Page) unkownPage;
				StringBuilder builder = new StringBuilder();

				for (String s : p.lines) {
					builder.append(s);
					builder.append("\n");
				}
				this.bookMeta.addPage(builder.toString());
			} else if (unkownPage instanceof SpecialPage) {
				SpecialPage special = (SpecialPage) unkownPage;

				CraftMetaBook b = (CraftMetaBook) this.bookMeta;
				b.pages.add(ChatSerializer.a(ComponentSerializer
						.toString(new TextComponent((BaseComponent[]) special.getLines().toArray(new BaseComponent[special.getLines().size()])))));
			}

		}

	}
}
