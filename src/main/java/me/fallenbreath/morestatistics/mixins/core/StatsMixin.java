package me.fallenbreath.morestatistics.mixins.core;

import me.fallenbreath.morestatistics.MoreStatisticsRegistry;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(Stats.class)
public abstract class StatsMixin
{
	static
	{
		MoreStatisticsRegistry.registerStatistics();
	}
}
