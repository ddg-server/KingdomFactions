package nl.dusdavidgames.kingdomfactions.modules.book;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.utils.book.BookBuilder;
import nl.dusdavidgames.kingdomfactions.modules.utils.book.ChatPieceHolder;
import nl.dusdavidgames.kingdomfactions.modules.utils.book.Page;
import nl.dusdavidgames.kingdomfactions.modules.utils.book.SpecialPage;

public class BookModule {

	public BookModule() {
		setInstance(this);
	}

	public static final String BOOK_NAME = ChatColor.RED + "The Kingdom Factions uitleg";

	private static @Getter @Setter BookModule instance;

	public ItemStack getBook() {

		BookBuilder builder = new BookBuilder();

		builder.setTitle(BOOK_NAME);
		builder.setAuthor(ChatColor.RED + "KingdomFactions Team");
		builder.setLore(ChatColor.RED
				+ "Hier lees je informatie die je nodig hebt om \nThe KingdomFactions the kunnen spelen!");


		Page info = new Page();
		info.addBlankLine();
		info.addBlankLine();
		info.addBlankLine();
		info.addLine(ChatColor.RED + "       Welkom op ");
		info.addLine(ChatColor.RED + "   THe Kingdom Factions");
		info.addBlankLine();
		info.addLine(ChatColor.RED + "Informatie Boek");
		info.addLine(ChatColor.RED + "Versie 1.0");
		info.addLine(ChatColor.RED + "Revisie 17-9-2016");

		Page info2 = new Page();
		info2.setPageColor(ChatColor.RED);
		info.addBlankLine();
		info2.addLine("Bij jouw eerste stappen in deze wereld heb je dit boekje ontvangen.");
		info2.addLine("Dit boekje is eenmalig te verkrijgen.");

		Page uitleg = new Page();
		uitleg.addLine("Kingdom Factions is anders dan de gemiddelde Faction server");
		uitleg.addLine("DDG heeft er een eigen twist aan gegeven.");
		uitleg.addLine("Het is buiten steden onmogelijk om blokken te breken.");
		uitleg.addLine("Er zijn 6 kingdoms, Tilifia, Hyvar, Eredon, Malzan, Dok.");
		uitleg.addLine("Zodra je je kingdom hebt gekozen, zal je een Faction moeten kiezen, of maken.");
		uitleg.addLine("Dit kan je doen met het commando /f create <naam>.");
		uitleg.addLine("Indien jij een Faction leider bent, is het de bedoeling dat je je eigen stad opricht.");
		uitleg.addLine(
				"Dit doe je met een Nexus. Deze kan je maken via /nexus create. Dit moet een goed eind van de hoofdstad.");
		uitleg.addLine("Verdere uitleg kotm bij het commando zelf.");
		uitleg.addLine("Dit kost 500000 Kingdom Factions Coins.");
		uitleg.addLine("Deze coins kan je op verschillende manieren krijgen.");
		uitleg.addLine("Onderandere door te minen. Je kan ores inleveren in de hoofdstad.");
		uitleg.addLine("Hier krijg je coins voor.");
		uitleg.addLine("Per Nexus kan je 5 gebouwen maken. Alle gebouwen hebben een eigen shop.");
		uitleg.addLine("Deze gebouwen kan je bouwen/upgraden via het commando /nexus build.");
		uitleg.addLine(
				"Het is alleen toegestaan om tussen 12:00 en 24:00 te pvpen. Buiten deze tijden is het niet mogelijk.");
		uitleg.addLine("Echter is het niet direct mogelijk om andere steden over te nemen. Dit moet in een oorlog.");
		uitleg.addLine("Oorlogen vinden vaak plaats in het weekend. Je moet in het weekend je Nexus dus beschermen.");
		uitleg.addLine("Mocht je een weekend afwezig zijn, is dat natuurlijk niet handig.");
		uitleg.addLine("Daarom is het mogelijk, om per oorlog een \"Beschermings Token\" te kopen.");
		uitleg.addLine("Dat kan met het commando /nexus protect.");

		Page tips = new Page();
		tips.addLine("Tips");
		tips.addBlankLine();
		tips.addLine("1 - Ores kan je krijgen in de minewereld. Echter staat PvP hier wel aan.");
		tips.addBlankLine();
		tips.addLine("2 - De minewereld krijgt elke zoveel tijd een reset! Bouwen is dus niet handig.");
		tips.addBlankLine();
		tips.addLine("3 - Zodra je een gebouw upgrade, wordt ALLES overschreven. Ook kisten!");
		tips.addBlankLine();
		tips.addLine("4 - Als je samen werkt, kom je verder.");

		SpecialPage regels = new SpecialPage();
		regels.addLine("   Regels");
		regels.addBlankLine();
		regels.addLine("op KDF gelden de normale DDGS Regels!");
		regels.addLine("Mocht je ze nog eens willen lezen,");
		ChatPieceHolder h = new ChatPieceHolder("kan dat ");
		ChatPieceHolder d = new ChatPieceHolder(ChatColor.RED + "hier" + ChatColor.RESET + "!");
		d.setHoverText(ChatColor.RED + "De regels kan je hier nog eens lezen!");
		d.setClickLink("http://forum.dusdavidgames.nl/brdc-document/ddg-server-regels.19/");
		regels.addLine(h, d);

		SpecialPage page = new SpecialPage();
		page.addBlankLine();
		page.addBlankLine();
		page.addLine(ChatColor.RED + "Developers:");
		ChatPieceHolder steen = new ChatPieceHolder("steenooo");
		steen.setClickLink("http://twitter.com/steenooo5");
		steen.setHoverText(ChatColor.RED + "Klik hier om de Twitter van steenooo te bekijken!");
		ChatPieceHolder space = new ChatPieceHolder(", ");
		ChatPieceHolder wouter = new ChatPieceHolder("wouter_MC");
		wouter.setClickLink("http://twitter.com/wouter_MC");
		wouter.setHoverText(ChatColor.RED + "Klik hier om de Twitter van wouter_MC te kijken!");
		page.addLine(steen, space, wouter);
		page.addBlankLine();
		page.addLine(ChatColor.RED + "Project Leider:");
		ChatPieceHolder roy = new ChatPieceHolder("Thrintiox");
		roy.setClickLink("http://twitter.com/Thrintiox");
		wouter.setHoverText(ChatColor.RED + "Klik hier om de Twitter van Thrintiox te kijken!");
		page.addLine(roy);
		page.addBlankLine();
		page.addLine(ChatColor.RED + "BouwTeam:");
		ChatPieceHolder bouw = new ChatPieceHolder("DDG BouwTeam");
		bouw.setClickLink("http://twitter.com/DDGBouwTeam");
		bouw.setHoverText(ChatColor.RED + "Klik hier om de Twitter van Thrintiox te kijken!");
		page.addLine(bouw);
		page.addBlankLine();
		ChatPieceHolder ddgtwitter = new ChatPieceHolder(ChatColor.RED + "" + ChatColor.BOLD + "Officiële DDG Twitter");
		ddgtwitter.setClickLink("http//twitter.com/DDG_Server");
		ddgtwitter.setHoverText("Klik hier om de officiële DDG Twitter te bekijken!");
		page.addLine(ddgtwitter);
		builder.addPage(page);

		builder.addPages(info, info2, uitleg, tips, page);
		return builder.build();
	}

}
