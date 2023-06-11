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

package me.fallenbreath.morestatistics.mixins.stats.break_bedrock;

import me.fallenbreath.morestatistics.MoreStatisticsRegistry;
import me.fallenbreath.morestatistics.utils.PistonPlacingMemory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PistonBlock.class)
public abstract class PistonBlockMixin
{
	@ModifyArgs(
			//#if MC >= 11600
			//$$ method = "onSyncedBlockEvent",
			//#else
			method = "onBlockAction",
			//#endif
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"
			),
			require = 2
	)
	private void removeBlock(Args args, BlockState state, World world, BlockPos pistonPos, int type, int data)
	{
		BlockPos posToRemove = args.get(0);
		if (world.getBlockState(posToRemove).getBlock() == Blocks.BEDROCK)
		{
			ServerPlayerEntity player = PistonPlacingMemory.getTheOneWhoJustPlacedPiston(world, pistonPos);
			if (player != null)
			{
				player.increaseStat(MoreStatisticsRegistry.BREAK_BEDROCK, 1);
			}
		}
	}
}
