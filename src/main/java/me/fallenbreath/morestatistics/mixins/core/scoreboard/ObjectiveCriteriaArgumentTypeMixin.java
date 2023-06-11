/*
 * This file is part of the More Statistics project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2023  Fallen_Breath and contributors
 *
 * More Statistics is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * More Statistics is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with More Statistics.  If not, see <https://www.gnu.org/licenses/>.
 */

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
