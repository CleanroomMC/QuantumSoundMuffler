package com.cleanroommc.quantumsoundmuffler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class DataManager implements ISoundLists {

    private static final String dataFile = Tags.MODID + "/muffled.dat";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting()
        .create();

    public static void loadData() {
        Map<String, Float> map = loadMuffledMap();
        if (map != null) map.forEach((R, F) -> muffledSounds.put(new ResourceLocation(R), F));
    }

    public static void saveData() {
        saveMuffledMap();
    }

    private static void saveMuffledMap() {
        new File(Tags.MODID).mkdir();
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(dataFile), StandardCharsets.UTF_8)) {
            writer.write(gson.toJson(muffledSounds));
        } catch (IOException ignored) {}
    }

    private static Map<String, Float> loadMuffledMap() {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(dataFile), StandardCharsets.UTF_8)) {
            return gson.fromJson(new JsonReader(reader), new TypeToken<Map<String, Float>>() {}.getType());
        } catch (JsonSyntaxException | IOException e) {
            return new HashMap<>();
        }
    }
}
