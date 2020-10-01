package nl.dusdavidgames.kingdomfactions.modules.utils.nms;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R2.Packet;
import net.minecraft.server.v1_9_R2.PacketPlayOutChat;
import net.minecraft.server.v1_9_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R2.PacketPlayOutTitle.EnumTitleAction;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class NMSMethods {

	

	public void sendTabTitle(KingdomFactionsPlayer player, String header, String footer) {
		if (header == null) {
			header = "";
		}
		header = ChatColor.translateAlternateColorCodes('&', header);
		if (footer == null) {
			footer = "";
		}
		footer = ChatColor.translateAlternateColorCodes('&', footer);

		header = header.replaceAll("%player%", player.getPlayer().getDisplayName());
		footer = footer.replaceAll("%player%", player.getPlayer().getDisplayName());
		try {
			Object tabHeader = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
					.getMethod("a", new Class[] { String.class })
					.invoke(null, new Object[] { "{\"text\":\"" + header + "\"}" });
			Object tabFooter = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
					.getMethod("a", new Class[] { String.class })
					.invoke(null, new Object[] { "{\"text\":\"" + footer + "\"}" });
			Constructor<?> titleConstructor = getNMSClass("PacketPlayOutPlayerListHeaderFooter")
					.getConstructor(new Class[] { getNMSClass("IChatBaseComponent") });
			Object packet = titleConstructor.newInstance(new Object[] { tabHeader });
			Field field = packet.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(packet, tabFooter);
			if(packet instanceof Packet<?>) {
				Packet<?> p = (Packet<?>) packet;
			player.sendPacket(p);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void sendTitle(KingdomFactionsPlayer p, String titleText, int inFadeTicks, int ShowTicks, int outFadeTicks) {
		PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE,
				ChatSerializer.a("{\"text\":\"" + titleText + "\"}"), inFadeTicks, ShowTicks, outFadeTicks);
		p.getEntityPlayer().playerConnection.sendPacket(title);
	    p.sendPacket(title);
	}

	public void sendSubTitle(KingdomFactionsPlayer p, String titleText, int inFadeTicks, int ShowTicks, int outFadeTicks) {
		PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE,
				ChatSerializer.a("{\"text\":\"" + titleText + "\"}"), inFadeTicks, ShowTicks, outFadeTicks);
	    p.sendPacket(title);
	}

	private Class<?> getNMSClass(String name) {
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	public void sendActionBar(KingdomFactionsPlayer player, String ActionBar) {
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + ActionBar + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
	    player.sendPacket(ppoc);
	}

}
