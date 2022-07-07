package com.cleanroommc.quantumsoundmuffler.interfaces;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public interface ISoundLists {
    SortedSet<ResourceLocation> soundsList = new TreeSet<>();
    SortedSet<ResourceLocation> recentSounds = new TreeSet<>();
    Map<ResourceLocation, Float> muffledSounds = new HashMap<>();
}
