package me.fallenbreath.morestatistics;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import me.fallenbreath.morestatistics.mixins.core.StatsAccessor;
import me.fallenbreath.morestatistics.network.PlayerInformation;
import me.fallenbreath.morestatistics.utils.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Set;

public class MoreStatisticsRegistry
{
	public static Identifier BREAK_BEDROCK;
	public static Identifier FIREWORK_BOOST;
	public static Identifier ENDER_PEARL_ONE_CM;
	private static final Set<String> STATS_SET = Sets.newHashSet();

	public static void registerStatistics()
	{
		addStat(BREAK_BEDROCK = StatsAccessor.callRegister("break_bedrock", StatFormatter.DEFAULT));
		addStat(FIREWORK_BOOST = StatsAccessor.callRegister("firework_boost", StatFormatter.DEFAULT));
		addStat(ENDER_PEARL_ONE_CM = StatsAccessor.callRegister("ender_pearl_one_cm", StatFormatter.DISTANCE));
		MoreStatistics.logger.info(String.format("%s enabled with %d new statistics", MoreStatistics.name, STATS_SET.size()));
	}


	public static void addStat(Identifier stat)
	{
		if (stat != null)
		{
			STATS_SET.add(stat.toString());
			MoreStatistics.logger.info("Added custom statistic " + stat);
		}
	}

	public static boolean canSend(ServerPlayerEntity player, Stat<?> stat)
	{
		String key = stat.getValue().toString();
		boolean isVanillaStat = !STATS_SET.contains(key);
		boolean playerAcceptThis = PlayerInformation.doesPlayerAcceptStat(player, key);
		return isVanillaStat || playerAcceptThis;
	}

	public static List<String> getStatsList()
	{
		return Lists.newArrayList(STATS_SET);
	}

	public static CompoundTag getStatsListNbt()
	{
		return Util.stringList2Nbt(getStatsList());
	}
}
