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

package me.fallenbreath.morestatistics;

import com.google.common.collect.Sets;
import net.minecraft.scoreboard.ScoreboardCriterion;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
//#if MC >= 11700
//$$ import me.fallenbreath.morestatistics.mixins.core.scoreboard.ScoreboardCriterionAccessor;
//#endif

public class MoreStatisticsScoreboardCriterion
{
	private static final Set<ScoreboardCriterion> SCOREBOARD_CRITERION_SET = Sets.newLinkedHashSet();
	public static final ScoreboardCriterion BLOCK_PLACED_COUNT = createCriterion("blockPlacedCount");

	private static ScoreboardCriterion createCriterion(String name)
	{
		ScoreboardCriterion scoreboardCriterion =
				//#if MC >= 11700
				//$$ ScoreboardCriterionAccessor.invokeCreate(name);
				//#else
				new ScoreboardCriterion(name);
				//#endif
		SCOREBOARD_CRITERION_SET.add(scoreboardCriterion);
		return scoreboardCriterion;
	}

	public static Set<ScoreboardCriterion> getCriterionSet()
	{
		return SCOREBOARD_CRITERION_SET;
	}

	public static List<String> getCriterionNameList()
	{
		return getCriterionSet().stream().map(ScoreboardCriterion::getName).collect(Collectors.toList());
	}

	public static void init()
	{
	}
}
