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

package me.fallenbreath.morestatistics.mixins.stats.ender_pearl_one_cm;

import me.fallenbreath.morestatistics.MoreStatisticsRegistry;
//#if MC >= 11600
//$$ import net.minecraft.entity.Entity;
//#else
import net.minecraft.entity.LivingEntity;
//#endif
import net.minecraft.entity.thrown.ThrownEnderpearlEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ThrownEnderpearlEntity.class)
public abstract class ThrownEnderpearlEntityMixin
{
	@Inject(
			method = "onCollision",
			at = @At(
					value = "INVOKE",
					//#if MC >= 11600
					//$$ target = "Lnet/minecraft/entity/Entity;requestTeleport(DDD)V",
					//#else
					target = "Lnet/minecraft/entity/LivingEntity;requestTeleport(DDD)V",
					//#endif
					ordinal = 0
			),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void onEnderPearlHit(
			HitResult hitResult, CallbackInfo ci,
			//#if MC >= 11600
			//$$ Entity entity
			//#else
			LivingEntity entity
			//#endif
	)
	{
		if (entity instanceof ServerPlayerEntity)
		{
			ServerPlayerEntity player = (ServerPlayerEntity)entity;
			int distance = Math.round(player.distanceTo((ThrownEnderpearlEntity)(Object)this) * 100.0F);
			if (distance > 0) {
				player.increaseStat(MoreStatisticsRegistry.ENDER_PEARL_ONE_CM, distance);
			}
		}
	}
}
