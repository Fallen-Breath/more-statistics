package me.fallenbreath.morestatistics.mixins;

import me.fallenbreath.morestatistics.MoreStatistics;
import me.fallenbreath.morestatistics.MoreStatisticsRegistry;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(Stats.class)
public abstract class StatsMixin
{
	static
	{
		MoreStatisticsRegistry.registerStatistics();
		MoreStatistics.logger.info(String.format("%s enabled with %d new statistics", MoreStatistics.name, MoreStatisticsRegistry.stats.size()));
	}
}
