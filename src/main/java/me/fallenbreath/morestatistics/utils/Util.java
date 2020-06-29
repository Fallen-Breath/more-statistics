package me.fallenbreath.morestatistics.utils;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Comparator;

public class Util
{
	public static void addStatsToNearestPlayers(World world, BlockPos blockPos, double radius, Identifier stat, int amount)
	{
		if (world.getServer() != null)
		{
			Vec3d pos = new Vec3d(blockPos).add(0.5D, 0.5D, 0.5D);
			world.getServer().getPlayerManager().getPlayerList().stream()
					.filter(
							player -> player.squaredDistanceTo(pos) <= radius * radius
					)
					.min(Comparator.comparingDouble(
							player -> player.squaredDistanceTo(pos)
					))
					.ifPresent(
							player -> player.increaseStat(stat, amount)
					);
		}
	}
}
