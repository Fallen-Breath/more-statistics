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
			method = "repairPlayerGears",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/ItemStack;setDamage(I)V"
			),
			ordinal = 1
	)
	private int onMendingApplied(int durabilityMended, PlayerEntity player, int amount)
	{
		player.increaseStat(MoreStatisticsRegistry.MEND_DURABILITY, durabilityMended);
		return durabilityMended;
	}
}
