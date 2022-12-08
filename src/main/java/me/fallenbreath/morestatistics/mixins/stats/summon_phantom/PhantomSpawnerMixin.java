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
