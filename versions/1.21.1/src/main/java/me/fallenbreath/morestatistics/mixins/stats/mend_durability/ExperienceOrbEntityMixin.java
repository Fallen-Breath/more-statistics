/*
 * This file is part of the More Statistics project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2024  Fallen_Breath and contributors
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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.fallenbreath.morestatistics.MoreStatisticsRegistry;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// impl for mc >= 1.21
@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin
{
	@ModifyExpressionValue(
			method = "repairPlayerGears",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/enchantment/EnchantmentHelper;getRepairWithXp(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/item/ItemStack;I)I",
					ordinal = 0
			)
	)
	private int onMendingApplied(int durabilityMended, @Local(argsOnly = true) ServerPlayerEntity player)
	{
		player.increaseStat(MoreStatisticsRegistry.MEND_DURABILITY, durabilityMended);
		return durabilityMended;
	}
}
