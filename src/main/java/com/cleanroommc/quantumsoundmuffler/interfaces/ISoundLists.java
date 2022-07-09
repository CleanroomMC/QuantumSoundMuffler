package com.cleanroommc.quantumsoundmuffler.interfaces;

import com.cleanroommc.quantumsoundmuffler.utils.Anchor;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public interface ISoundLists {

    Set<String> forbiddenSounds = new HashSet<>();
    SortedSet<ResourceLocation> soundsList = new TreeSet<>();
    SortedSet<ResourceLocation> recentSounds = new TreeSet<>();
    Map<ResourceLocation, Float> muffledSounds = new HashMap<>();
    List<Anchor> anchorList = new ArrayList<>();
}
