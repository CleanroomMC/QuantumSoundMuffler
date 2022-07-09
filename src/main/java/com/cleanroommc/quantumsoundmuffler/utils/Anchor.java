package com.cleanroommc.quantumsoundmuffler.utils;

import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import scala.Int;


import java.util.SortedMap;
import java.util.TreeMap;

public class Anchor
{

	private final int id;
	private BlockPos anchorPos;
	private String name;
	private Integer dimension;
	private int Radius;
	private SortedMap<String, Float> muffledSounds = new TreeMap<>();

	public Anchor(int id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public Anchor(int id, String name, BlockPos anchorPos, Integer dimension, int Radius, SortedMap<String, Float> muffledSounds)
	{
		this.id = id;
		this.name = name;
		this.anchorPos = anchorPos;
		this.dimension = dimension;
		this.Radius = Radius;
		this.muffledSounds = muffledSounds;
	}

	public BlockPos getAnchorPos()
	{
		return anchorPos;
	}

	private void setAnchorPos(BlockPos anchorPos)
	{
		this.anchorPos = anchorPos;
	}

	public int getAnchorId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public int getRadius()
	{
		return Radius;
	}

	public void setRadius(int Radius)
	{
		this.Radius = Radius;
	}

	private void setName(String name)
	{
		this.name = name;
	}

	public SortedMap<ResourceLocation, Float> getMuffledSounds()
	{
		SortedMap<ResourceLocation, Float> temp = new TreeMap<>();
		this.muffledSounds.forEach((R, F) -> temp.put(new ResourceLocation(R), F));
		return temp;
	}

	public void setMuffledSounds(SortedMap<ResourceLocation, Float> muffledSounds)
	{
		muffledSounds.forEach((R, F) -> this.muffledSounds.put(R.toString(), F));
	}

	public void addSound(ResourceLocation sound, float volume)
	{
		muffledSounds.put(sound.toString(), volume);
	}

	public void replaceSound(ResourceLocation sound, float volume)
	{
		muffledSounds.replace(sound.toString(), volume);
	}

	public String getX()
	{
		return anchorPos != null ? String.valueOf(anchorPos.getX()) : "";
	}

	public String getY()
	{
		return anchorPos != null ? String.valueOf(anchorPos.getY()) : "";
	}

	public String getZ()
	{
		return anchorPos != null ? String.valueOf(anchorPos.getZ()) : "";
	}

	public Integer getDimension()
	{
		return dimension;
	}

	private void setDimension(Integer dimension)
	{
		this.dimension = dimension;
	}

	public void removeSound(ResourceLocation sound)
	{
		muffledSounds.remove(sound.toString());
	}

	public void setAnchor()
	{
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		setAnchorPos(player.getPosition());
		setDimension(player.dimension);
		setRadius(this.getRadius() == 0 ? 32 : this.getRadius());
	}

	public void deleteAnchor()
	{
		setName("Anchor: " + this.getAnchorId());
		setAnchorPos(null);
		setDimension(null);
		setRadius(0);
		muffledSounds.clear();
	}

	public void editAnchor(String title, int Radius)
	{
		setName(title);
		setRadius(Radius);
	}

	private static boolean closerThan(BlockPos soundPos, BlockPos anchorPos, int radius)
	{
		return soundPos.distanceSq((double) anchorPos.getX(), (double) anchorPos.getY(), (double) anchorPos.getZ()) < radius * radius;
	}

	public static Anchor getAnchor(ISound sound)
	{
		BlockPos soundPos = new BlockPos(sound.getXPosF(), sound.getYPosF(), sound.getZPosF());
		int dim = Minecraft.getMinecraft().player.dimension;

		return ISoundLists.anchorList.stream()
									 .filter(anc -> anc.getAnchorPos() != null && anc.getDimension() == dim && closerThan(soundPos, anc.getAnchorPos(), anc.getRadius()) && anc.getMuffledSounds()
																																											   .containsKey(sound.getSoundLocation()))
									 .findFirst()
									 .orElse(null);
	}

}
