package com.cleanroommc.quantumsoundmuffler.interfaces;

import java.util.*;

import net.minecraft.util.ResourceLocation;

public interface ISoundLists {

    Set<String> forbiddenSounds = new HashSet<>();
    SortedSet<ResourceLocation> soundsList = new TreeSet<>(Comparator.comparingInt(ResourceLocation::hashCode));
    SortedSet<ResourceLocation> recentSounds = new TreeSet<>(Comparator.comparingInt(ResourceLocation::hashCode));
    Map<ResourceLocation, Float> muffledSounds = new HashMap<>();
}
