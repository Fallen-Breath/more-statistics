package me.fallenbreath.morestatistics.mixins.stats;

import me.fallenbreath.morestatistics.MoreStatisticsRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlEntityMixin
{
	@Inject(
			method = "onCollision",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/Entity;requestTeleport(DDD)V",
					ordinal = 0
			),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void onEnderPearlHit(HitResult hitResult, CallbackInfo ci, LivingEntity livingEntity)
	{
		if (livingEntity instanceof ServerPlayerEntity)
		{
			ServerPlayerEntity player = (ServerPlayerEntity)livingEntity;
			int distance = Math.round(player.distanceTo((EnderPearlEntity)(Object)this) * 100.0F);
			if (distance > 0) {
				player.increaseStat(MoreStatisticsRegistry.ENDER_PEARL_ONE_CM, distance);
			}
		}
	}
}
