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

package me.fallenbreath.morestatistics.mixins.scoreboard.blockPlacedCount;

import me.fallenbreath.morestatistics.MoreStatisticsScoreboardCriterion;
import net.minecraft.item.BlockItem;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

//#if MC >= 12004
//$$ import net.minecraft.scoreboard.ScoreAccess;
//#else
import net.minecraft.scoreboard.ScoreboardPlayerScore;
//#endif

@Mixin(BlockItem.class)
public abstract class BlockItemMixin
{
	@ModifyArg(
			method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;",
			at = @At(
					value = "INVOKE",
					//#if MC >= 12000
					//$$ target = "Lnet/minecraft/advancement/criterion/ItemCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V"
					//#else
					target = "Lnet/minecraft/advancement/criterion/PlacedBlockCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V"
					//#endif
			),
			index = 0
	)
	private ServerPlayerEntity onPlayerPlacedBlock(ServerPlayerEntity player)
	{
		player.getScoreboard().forEachScore(
				MoreStatisticsScoreboardCriterion.BLOCK_PLACED_COUNT,
				//#if MC >= 12004
				//$$ player, ScoreAccess::incrementScore
				//#else
				player.getEntityName(), ScoreboardPlayerScore::incrementScore
				//#endif
		);
		return player;
	}
}
