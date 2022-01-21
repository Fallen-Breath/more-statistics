package me.fallenbreath.morestatistics.utils;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;

public class Util
{
	public static void addStatsToNearestPlayers(World world, BlockPos blockPos, double radius, Identifier stat, int amount, boolean noSpectator)
	{
		if (world.getServer() != null)
		{
			Vec3d pos = Vec3d.ofCenter(blockPos);
			world.getServer().getPlayerManager().getPlayerList().stream()
					.filter(player -> !(noSpectator && player.isSpectator()))
					.filter(player -> player.squaredDistanceTo(center) <= radius * radius)
					.min(Comparator.comparingDouble(player -> player.squaredDistanceTo(center)))
					.ifPresent(player -> player.increaseStat(stat, amount));
		}
	}

	public static CompoundTag stringList2Nbt(List<String> list)
	{
		CompoundTag nbt = new CompoundTag();
		nbt.putInt("length", list.size());
		for (int i = 0; i < list.size(); i++)
		{
			nbt.putString(String.valueOf(i), list.get(i));
		}
		return nbt;
	}

	public static List<String> nbt2StringList(CompoundTag nbt)
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
