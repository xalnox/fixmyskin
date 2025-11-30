package fr.xalnox.fixmyskin.utils;

import org.spongepowered.include.com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.*;

public class SkinAPIUtils {

    private static final Gson gson = new Gson();
    private static final Map<String,String> cachedUUID = new HashMap<>(); // Username -> UUID
    public static List<String> getSkinCapeURL(String UUID) throws IOException {
        String content = getJsonResource("https://sessionserver.mojang.com/session/minecraft/profile/" + UUID + "?unsigned=false");

        Gson gson = new Gson();
        try (StringReader reader = new StringReader(content)) {
            // Parse main JSON
            Profile profile = gson.fromJson(reader, Profile.class);

            // Get the Base64 texture value
            if (!profile.properties.isEmpty()) {
                String encodedValue = profile.properties.get(0).value;
                String decodedJson = new String(Base64.getDecoder().decode(encodedValue));

                // Parse the decoded texture JSON
                try (StringReader reader1 = new StringReader(decodedJson)) {
                    TextureWrapper textures = gson.fromJson(reader1, TextureWrapper.class);

                    List<String> finalTextures = new ArrayList<>();
                    // Access SKIN and CAPE URLs
                    if (textures.textures.CAPE != null) {
                        finalTextures.add(textures.textures.CAPE.url);
                    }
                    if (textures.textures.SKIN != null) {
                        finalTextures.add(0,textures.textures.SKIN.url);
                        if (textures.textures.SKIN.metadata != null && textures.textures.SKIN.metadata.model != null) {
                            finalTextures.add(textures.textures.SKIN.metadata.model);
                        }
                    }

                    if (!finalTextures.isEmpty()) {
                        return finalTextures;
                    }
                }
            }
        }
        throw new IOException();
    }

    public static String getUUIDFromUsername(String username) throws IOException {
        if (!cachedUUID.containsKey(username)) {
            String content = getJsonResource("https://api.mojang.com/minecraft/profile/lookup/name/" + username);

            try (StringReader reader = new StringReader(content)) {
                PlayerProfile profile = gson.fromJson(reader, PlayerProfile.class);
                if (profile.id != null) {
                    cachedUUID.put(username, profile.id);
                    return profile.id;
                }
            }
            throw new IOException("Error while fetching uuid");
        }
        else {
            return cachedUUID.get(username);
        }
    }


    private static String getJsonResource(String URL) throws IOException {
        URL url = new URL(URL);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        if (con.getResponseCode() / 100 != 2) {
            System.out.println(URL);
            throw new IOException();
        }
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

    static class Profile {
        List<Property> properties;
    }

    static class Property {
        String value;
    }
    // Inner decoded texture classes
    static class TextureWrapper {
        Textures textures;
    }

    static class Textures {
        Texture SKIN;
        Texture CAPE;
    }

    static class Texture {
        String url;
        Metadata metadata;
    }

    static class Metadata {
        String model;
    }
    static class PlayerProfile {
        String id;
        String name;
        Boolean legacy;
        Boolean demo;
    }
}
