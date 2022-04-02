package de.epsdev.minecord.version;

import de.epsdev.minecord.Minecord;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class Version {
    private final static String VERSION_URL = "https://api.github.com/repos/EliasSchramm/Minecord/releases/latest";
    private static String latestVersion = "";

    private static String get(String urlToRequest) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRequest);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return result.toString();
    }

    public static String getLatestVersion() {
        if (!latestVersion.isEmpty()) {
            return latestVersion;
        }

        try {
            String rawJson = get(VERSION_URL);
            JSONObject jsonObject = new JSONObject(rawJson);

            return jsonObject.get("tag_name").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Boolean isUpToDate() {
        String latestVersion = Version.getLatestVersion();
        String currentVersion = Minecord.pluginDescriptionFile.getVersion();

        return currentVersion.equals(latestVersion);
    }

    public static void checkVersion() {
        String currentVersion = Minecord.pluginDescriptionFile.getVersion();

        if (isUpToDate()) {
            return;
        }

        Minecord.pluginLogger.log(
                Level.WARNING,
                String.format("Minecord v%s is outdated! " +
                                "Download the latest version (v%s) here: " +
                                "https://github.com/EliasSchramm/Minecord/releases/latest",
                        currentVersion,
                        getLatestVersion()
                ));
    }

}
