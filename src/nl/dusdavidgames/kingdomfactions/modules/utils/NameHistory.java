package nl.dusdavidgames.kingdomfactions.modules.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

public class NameHistory {
	private static @Getter @Setter NameHistory instance;

	public NameHistory() {
		setInstance(this);
	}

	public String getUUID(String playername) throws IOException {
		String str = "";
		URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playername);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		if ((str = in.readLine()) != null) {
			if (str.contains("{\"id\":")) {
				String uuid = str.toLowerCase().replace("{\"id\":", "").replace("\"", "").replace(",", "")
						.replace("legacy:true", "").replace("name:" + playername.toLowerCase() + "}", "");
				return uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-"
						+ uuid.substring(16, 20) + "-" + uuid.substring(20, 32);
			}
		}
		return str;
	}

	public ArrayList<String> getNames(String playername) throws IOException {
		String str = getPrevNames(playername);
		if (str == null) {
			Logger.WARNING.log("No name history was found");
		}
		Iterator<JsonElement> iter = (new JsonParser()).parse(str).getAsJsonArray().iterator();
		ArrayList<String> names = new ArrayList<String>();
		while (iter.hasNext()) {
			names.add(iter.next().getAsJsonObject().get("name").getAsString().concat("\n"));
		}
		return names;
	}

	/**
	 * 
	 * @param playername
	 * @return previous names
	 * @throws IOException
	 */
	public String getPrevNames(String playername) throws IOException {
		String target = String.format("https://api.mojang.com/user/profiles/%s/names",
				getUUID(playername).toString().replace("-", ""));
		String r = null;

		HttpURLConnection connection = null;
		try {
			URL url = new URL(target);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			connection.setUseCaches(false);
			connection.setDoOutput(true);

			connection.connect();

			if (connection.getResponseCode() != 200) {
				return null;
			}

			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r').append('\n');
			}
			rd.close();

			r = response.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return r;
	}
}
