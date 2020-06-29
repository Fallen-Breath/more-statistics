package me.fallenbreath.morestatistics;

import com.google.common.collect.Sets;
import me.fallenbreath.morestatistics.mixins.StatsAccessor;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;

import java.util.Set;

public class MoreStatisticsRegistry
{
	public static Identifier BREAK_BEDROCK;
	public static Identifier FIREWORK_BOOST;
	public static final Set<String> stats = Sets.newHashSet();

	public static void registerStatistics()
	{
		addStat(BREAK_BEDROCK = StatsAccessor.callRegister("break_bedrock", StatFormatter.DEFAULT));
		addStat(FIREWORK_BOOST = StatsAccessor.callRegister("firework_boost", StatFormatter.DEFAULT));
	}


	public static void addStat(Identifier stat)
	{
		if (stat != null)
		{
			stats.add(stat.toString());
			MoreStatistics.logger.info("Added stat " + stat);
		}
	}

	public static boolean isCustomStat(Stat<?> stat)
	{
		return stats.contains(stat.getValue().toString());
	}
}
