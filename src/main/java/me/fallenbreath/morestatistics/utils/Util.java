package me.fallenbreath.morestatistics.utils;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;

import java.util.List;

public class Util
{
	public static CompoundTag stringList2Nbt(List<String> list)
	{
		NbtCompound nbt = new NbtCompound();
		nbt.putInt("length", list.size());
		for (int i = 0; i < list.size(); i++)
		{
			nbt.putString(String.valueOf(i), list.get(i));
		}
		return nbt;
	}

	public static List<String> nbt2StringList(NbtCompound nbt)
	{
		List<String> list = Lists.newArrayList();
		int length = nbt.getInt("length");
		for (int i = 0; i < length; i++)
		{
			list.add(nbt.getString(String.valueOf(i)));
		}
		return list;
	}
}
