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

package me.fallenbreath.morestatistics.mixins.stats.summon_phantom;

import me.fallenbreath.morestatistics.MoreStatisticsRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.gen.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PhantomSpawner.class)
public abstract class PhantomSpawnerMixin
{
	private PlayerEntity currentPlayer$moreStatistics = null;

	@ModifyVariable(
			method = "spawn",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/util/math/MathHelper;clamp(III)I"
			)
	)
	private PlayerEntity recordsCurrentPlayer$phantomLogger(PlayerEntity player)
	{
		this.currentPlayer$moreStatistics = player;
		return player;
	}

	@ModifyVariable(
			method = "spawn",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/entity/EntityType;PHANTOM:Lnet/minecraft/entity/EntityType;"
			),
			ordinal = 1
	)
	private int logPlayerSpawningPhantoms(int amount)
	{
		if (this.currentPlayer$moreStatistics != null)
		{
			this.currentPlayer$moreStatistics.increaseStat(MoreStatisticsRegistry.SUMMON_PHANTOM, amount);
			this.currentPlayer$moreStatistics = null;
		}
		return amount;
	}
}
