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
