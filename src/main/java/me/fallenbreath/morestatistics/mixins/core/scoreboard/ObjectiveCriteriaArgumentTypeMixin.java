package me.fallenbreath.morestatistics.mixins.core.scoreboard;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import me.fallenbreath.morestatistics.MoreStatisticsScoreboardCriterion;
import me.fallenbreath.morestatistics.network.ClientHandler;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.arguments.ObjectiveCriteriaArgumentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mixin(ObjectiveCriteriaArgumentType.class)
public abstract class ObjectiveCriteriaArgumentTypeMixin
{
	@SuppressWarnings("InvalidInjectorMethodSignature")
	@ModifyVariable(
			method = "listSuggestions",
			at = @At(
					value = "INVOKE_ASSIGN",
					target = "Lcom/google/common/collect/Lists;newArrayList(Ljava/lang/Iterable;)Ljava/util/ArrayList;",
					shift = At.Shift.AFTER,
					ordinal = 0,
					remap = false
			)
	)
	private <S> List<String> adjustCriterionSuggestions(List<String> suggestions, CommandContext<S> context, SuggestionsBuilder builder)
	{
		// only modify at client side
		if (context.getSource() instanceof ClientCommandSource)
		{
			List<String> newSuggestions = Lists.newArrayList();
			Set<String> msCriterions = new HashSet<>(MoreStatisticsScoreboardCriterion.getCriterionNameList());
			suggestions.forEach(suggestion -> {
				boolean vanillaCriterion = !msCriterions.contains(suggestion);
				boolean butServerRecognizesThis = ClientHandler.getScoreboardCriterionNames().contains(suggestion);
				if (vanillaCriterion || butServerRecognizesThis)
				{
					newSuggestions.add(suggestion);
				}
			});
			suggestions = newSuggestions;
		}
		return suggestions;
	}
}
