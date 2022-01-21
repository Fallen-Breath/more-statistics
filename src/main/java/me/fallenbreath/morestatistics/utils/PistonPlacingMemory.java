package me.fallenbreath.morestatistics.utils;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PistonPlacingMemory
{
	private static final Map<Pair<DimensionType, BlockPos>, ServerPlayerEntity> MEMORY = Maps.newHashMap();

	public static void onPlayerPlacedPiston(ServerPlayerEntity player, BlockPos pos)
	{
		MEMORY.put(makePair(player.getServerWorld(), pos), player);
	}

	@Nullable
	public static ServerPlayerEntity getTheOneWhoJustPlacedPiston(World world, BlockPos pos)
	{
		return MEMORY.get(makePair(world, pos));
	}

	private static Pair<DimensionType, BlockPos> makePair(World world, BlockPos pos)
	{
		return Pair.of(world.getDimension().getType(), pos);
	}

	public static void cleanMemory()
	{
		MEMORY.clear();
	}
}
