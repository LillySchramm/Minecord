package de.epsdev.minecord.bot;

import de.epsdev.minecord.Minecord;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NameLink {
    private static Map<String, String> links = new HashMap<>();

    private static Path getSaveFile() {
        String uri = Minecord.dataFolder.getAbsolutePath() + "/links.json";
        return Paths.get(uri);
    }

    public static void load() {
        Path saveFile = getSaveFile();
        if (saveFile.toFile().exists()) {
            try {
                String jsonString = new String(Files.readAllBytes(saveFile));
                JSONObject jsonObject = new JSONObject(jsonString);

                links = new HashMap<>();

                for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                    String key = it.next();
                    links.put(key, jsonObject.getString(key));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getLink(String tag) {
        return links.getOrDefault(tag, null);
    }

    public static void setLink(String tag, String name) {
        links.put(tag, name);
        save();
    }

    public static boolean removeLink(String name) {
        if (!links.containsValue(name)) {
            return false;
        }

        String tag = "";
        for (String key: links.keySet()) {
            if (links.getOrDefault(key, null) != null) {
                tag = key;
                break;
            }
        }

        links.remove(tag);
        save();

        return true;
    }

    public static void save() {
        JSONObject jsonObject = new JSONObject();

        for (String key : links.keySet()) {
            jsonObject.put(key, links.get(key));
        }

        String jsonString = jsonObject.toString();

        Path saveFile = getSaveFile();
        try {
            Files.write(
                    saveFile,
                    jsonString.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
