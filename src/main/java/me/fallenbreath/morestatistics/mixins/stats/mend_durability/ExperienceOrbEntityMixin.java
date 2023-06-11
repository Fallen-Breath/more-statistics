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

package me.fallenbreath.morestatistics.mixins.stats.mend_durability;

import me.fallenbreath.morestatistics.MoreStatisticsRegistry;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin
{
	@ModifyVariable(
			//#if MC >= 11700
			//$$ method = "repairPlayerGears",
			//#else
			method = "onPlayerCollision",
			//#endif
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/ItemStack;setDamage(I)V"
			//#if MC >= 11700
			//$$ ),
			//$$ ordinal = 1
			//#else
			)
			//#endif
	)
	private int onMendingApplied(
			int durabilityMended, PlayerEntity player
			//#if MC >= 11700
			//$$ , int amount
			//#endif
	)
	{
		player.increaseStat(MoreStatisticsRegistry.MEND_DURABILITY, durabilityMended);
		return durabilityMended;
	}
}
