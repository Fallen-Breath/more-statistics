package me.fallenbreath.morestatistics;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import me.fallenbreath.morestatistics.mixins.StatsAccessor;
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
	private static final Set<String> statsSet = Sets.newHashSet();
	private static final List<String> statsList = Lists.newArrayList();

	public static void registerStatistics()
	{
		addStat(BREAK_BEDROCK = StatsAccessor.callRegister("break_bedrock", StatFormatter.DEFAULT));
		addStat(FIREWORK_BOOST = StatsAccessor.callRegister("firework_boost", StatFormatter.DEFAULT));
	}


	public static void addStat(Identifier stat)
	{
		if (stat != null)
		{
			statsSet.add(stat.toString());
			statsList.add(stat.toString());
			MoreStatistics.logger.info("Added custom statistic " + stat);
		}
	}

	public static boolean canSend(ServerPlayerEntity player, Stat<?> stat)
	{
		String key = stat.getValue().toString();
		boolean isVanillaStat = !statsSet.contains(key);
		boolean playerAcceptThis = PlayerInformation.isPlayerAcceptStat(player, key);
		return isVanillaStat || playerAcceptThis;
	}

	public static List<String> getStatsList()
	{
		return statsList;
	}

	public static CompoundTag getStatsListNbt()
	{
		return Util.stringList2Nbt(getStatsList());
	}
}
