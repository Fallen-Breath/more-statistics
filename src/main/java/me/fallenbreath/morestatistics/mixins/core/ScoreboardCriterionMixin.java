package me.fallenbreath.morestatistics.mixins.core;

import me.fallenbreath.morestatistics.MoreStatisticsScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardCriterion;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ScoreboardCriterion.class)
public abstract class ScoreboardCriterionMixin
{
	static
	{
		MoreStatisticsScoreboardCriterion.init();
	}
}
