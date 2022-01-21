package me.fallenbreath.morestatistics.mixins.core.scoreboard;

import net.minecraft.scoreboard.ScoreboardCriterion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ScoreboardCriterion.class)
public interface ScoreboardCriterionAccessor
{
	@Invoker
	static ScoreboardCriterion invokeCreate(String name)
	{
		throw new RuntimeException();
	}
}
