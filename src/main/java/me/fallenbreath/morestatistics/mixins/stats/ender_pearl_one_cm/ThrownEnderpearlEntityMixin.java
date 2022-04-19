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
