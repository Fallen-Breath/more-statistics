package me.fallenbreath.morestatistics;

import com.google.common.collect.Sets;
import me.fallenbreath.morestatistics.mixins.core.stats.StatsAccessor;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

//#if MC >= 11903
//$$ import net.minecraft.registry.Registries;
//#endif

import java.util.Set;

public class MoreStatisticsRegistry
{
	private static final Set<Identifier> STATS_SET = Sets.newLinkedHashSet();
	public static final Identifier BREAK_BEDROCK = register("break_bedrock", StatFormatter.DEFAULT);
	public static final Identifier ENDER_PEARL_ONE_CM = register("ender_pearl_one_cm", StatFormatter.DISTANCE);
	public static final Identifier FIREWORK_BOOST = register("firework_boost", StatFormatter.DEFAULT);
	public static final Identifier MEND_DURABILITY = register("mend_durability", StatFormatter.DEFAULT);

	private static Identifier register(String name, StatFormatter statFormatter)
	{
		// vanilla stuffs, just like net.minecraft.stat.Stats#register
		Identifier statId = new Identifier(name);  // using minecraft namespace. it's fine xd
		Registry.register(
				//#if MC >= 11903
				//$$ Registries.CUSTOM_STAT,
				//#else
				Registry.CUSTOM_STAT,
				//#endif
				statId, statId
		);
		StatsAccessor.getCUSTOM().getOrCreateStat(statId, statFormatter);

		// our stuffs
		STATS_SET.add(statId);
		MoreStatisticsMod.LOGGER.info("Added custom statistic " + statId);

		return statId;
	}

	public static void init()
	{
		// stuffs are done in <clinit>
		MoreStatisticsMod.LOGGER.info(String.format("%s enabled with %d new statistics", MoreStatisticsMod.NAME, STATS_SET.size()));
	}

	public static Set<Identifier> getStatsSet()
	{
		return STATS_SET;
	}
}
