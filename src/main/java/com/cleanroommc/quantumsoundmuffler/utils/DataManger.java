package com.cleanroommc.quantumsoundmuffler.utils;

import com.cleanroommc.quantumsoundmuffler.QuantumSoundMuffler;
import com.cleanroommc.quantumsoundmuffler.QuantumSoundMufflerConfig;
import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataManger implements ISoundLists
{
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void loadData()
	{
		loadMuffledMap().forEach((R, F) -> muffledSounds.put(new ResourceLocation(R), F));
		if (!QuantumSoundMufflerConfig.disableAnchors)
		{
			anchorList.clear();
			List<Anchor> anchors = loadAnchors();
			if (anchors != null)
			{
				anchorList.addAll(anchors);
			}
		}
	}

	public static void saveData()
	{
		saveMuffledMap();

		if (!QuantumSoundMufflerConfig.disableAnchors)
		{
			saveAnchors();
		}
	}

	private static String getWorldName()
	{

		if (Minecraft.getMinecraft().getIntegratedServer() == null)
		{ // is on a server
			return Minecraft.getMinecraft().world.getWorldInfo().getWorldName();
		} else
		{
			return Minecraft.getMinecraft().getIntegratedServer().getFolderName();
		}
	}

	private static NBTTagCompound serializeAnchor(Anchor anchor)
	{

		NBTTagCompound anchorNBT = new NBTTagCompound();
		NBTTagCompound muffledNBT = new NBTTagCompound();

		anchorNBT.setInteger("ID", anchor.getAnchorId());
		anchorNBT.setString("NAME", anchor.getName());

		if (anchor.getAnchorPos() == null)
		{
			return anchorNBT;
		}

		anchorNBT.setLong("POS", anchor.getAnchorPos().toLong());
		anchorNBT.setInteger("DIM", anchor.getDimension());
		anchorNBT.setInteger("RAD", anchor.getRadius());
		anchor.getMuffledSounds().forEach((R, F) -> muffledNBT.setFloat(R.toString(), F));
		anchorNBT.setTag("MUFFLED", muffledNBT);

		return anchorNBT;
	}

	public static Anchor deserializeAnchor(NBTTagCompound nbt)
	{
		SortedMap<String, Float> muffledSounds = new TreeMap<>();
		NBTTagCompound muffledNBT = nbt.getCompoundTag("MUFFLED");

		for (String key : muffledNBT.getKeySet())
		{
			muffledSounds.put(key, muffledNBT.getFloat(key));
		}

		if (!nbt.hasKey("POS"))
		{
			return new Anchor(nbt.getInteger("ID"), nbt.getString("NAME"));
		} else
		{
			return new Anchor(nbt.getInteger("ID"), nbt.getString("NAME"), BlockPos.fromLong(nbt.getLong("POS")), nbt.getInteger("DIM"), nbt.getInteger("RAD"), muffledSounds);
		}
	}

	private static void saveMuffledMap()
	{
		new File("QSM/").mkdir();
		try (Writer writer = new OutputStreamWriter(new FileOutputStream("QSM/soundsMuffled.dat"), StandardCharsets.UTF_8))
		{
			writer.write(gson.toJson(muffledSounds));
		} catch (IOException ignored)
		{
		}
	}

	private static Map<String, Float> loadMuffledMap()
	{
		try (InputStreamReader reader = new InputStreamReader(new FileInputStream("QSM/soundsMuffled.dat"), StandardCharsets.UTF_8))
		{
			return gson.fromJson(new JsonReader(reader), new TypeToken<Map<String, Float>>()
			{
			}.getType());
		} catch (JsonSyntaxException | IOException e)
		{
			return new HashMap<>();
		}
	}

	private static void saveAnchors()
	{
		new File("QSM/", getWorldName()).mkdirs();
		try (Writer writer = new OutputStreamWriter(new FileOutputStream("QSM/" + getWorldName() + "/anchors.dat"), StandardCharsets.UTF_8))
		{
			writer.write(gson.toJson(anchorList));
		} catch (IOException ignored)
		{
		}
	}

	private static List<Anchor> loadAnchors()
	{
		try (InputStreamReader reader = new InputStreamReader(new FileInputStream("QSM/" + getWorldName() + "/anchors.dat"), StandardCharsets.UTF_8))
		{
			return gson.fromJson(new JsonReader(reader), new TypeToken<List<Anchor>>()
			{
			}.getType());
		} catch (JsonSyntaxException | IOException ignored)
		{
			return null;
		}
	}

}
