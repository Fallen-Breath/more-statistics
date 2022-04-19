package me.fallenbreath.morestatistics.mixins.core.scoreboard;

import net.minecraft.scoreboard.ScoreboardCriterion;
import org.spongepowered.asm.mixin.Mixin;
//#if MC >= 11700
//$$ import org.spongepowered.asm.mixin.gen.Invoker;
//#endif

@Mixin(ScoreboardCriterion.class)
public interface ScoreboardCriterionAccessor
{
	//#if MC >= 11700
	//$$ @Invoker
	//$$ static ScoreboardCriterion invokeCreate(String name)
	//$$ {
	//$$ 	throw new RuntimeException();
	//$$ }
	//#endif
}