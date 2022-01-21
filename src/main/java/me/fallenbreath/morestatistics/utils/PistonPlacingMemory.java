package me.fallenbreath.morestatistics.utils;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PistonPlacingMemory
{
	private static final Map<Pair<RegistryKey<World>, BlockPos>, ServerPlayerEntity> MEMORY = Maps.newHashMap();

	public static void onPlayerPlacedPiston(ServerPlayerEntity player, BlockPos pos)
	{
		MEMORY.put(makePair(player.getWorld(), pos), player);
	}

	@Nullable
	public static ServerPlayerEntity getTheOneWhoJustPlacedPiston(World world, BlockPos pos)
	{
		return MEMORY.get(makePair(world, pos));
	}

	private static Pair<RegistryKey<World>, BlockPos> makePair(World world, BlockPos pos)
	{
		return Pair.of(world.getRegistryKey(), pos);
	}

	public static void cleanMemory()
	{
		MEMORY.clear();
	}
}
