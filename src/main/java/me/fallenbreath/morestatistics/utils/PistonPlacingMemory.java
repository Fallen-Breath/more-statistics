/*
 * This file is part of the More Statistics project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2023  Fallen_Breath and contributors
 *
 * More Statistics is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * More Statistics is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with More Statistics.  If not, see <https://www.gnu.org/licenses/>.
 */

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
	private static final Map<Pair<DimensionWrapper, BlockPos>, ServerPlayerEntity> MEMORY = Maps.newHashMap();

	public static void onPlayerPlacedPiston(ServerPlayerEntity player, BlockPos pos)
	{
		MEMORY.put(makePair(player.getServerWorld(), pos), player);
	}

	@Nullable
	public static ServerPlayerEntity getTheOneWhoJustPlacedPiston(World world, BlockPos pos)
	{
		return MEMORY.get(makePair(world, pos));
	}

	private static Pair<DimensionWrapper, BlockPos> makePair(World world, BlockPos pos)
	{
		return Pair.of(DimensionWrapper.of(world), pos);
	}

	public static void cleanMemory()
	{
		MEMORY.clear();
	}
}
