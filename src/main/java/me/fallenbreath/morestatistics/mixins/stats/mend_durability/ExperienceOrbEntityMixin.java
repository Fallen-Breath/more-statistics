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
