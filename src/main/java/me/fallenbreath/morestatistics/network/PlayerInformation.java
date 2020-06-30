package me.fallenbreath.morestatistics.network;

import com.google.common.collect.Maps;
import me.fallenbreath.morestatistics.MoreStatistics;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;
import java.util.Map;

public class PlayerInformation
{
	private static final Map<ServerPlayerEntity, List<String>> statsListAcceptedByPlayer = Maps.newHashMap();

	public static void setAcceptedStatsList(ServerPlayerEntity player, List<String> list)
	{
		MoreStatistics.logger.info(String.format("Player %s Added with accepted stats list:", player.getName().getString()));
		for (String stat : list)
		{
			MoreStatistics.logger.info("- " + stat);
		}
		statsListAcceptedByPlayer.put(player, list);
	}

	public static void removePlayer(ServerPlayerEntity player)
	{
		statsListAcceptedByPlayer.remove(player);
	}

	public static List<String> getAcceptedStatsList(ServerPlayerEntity player)
	{
		return statsListAcceptedByPlayer.get(player);
	}

	public static boolean isPlayerAcceptStat(ServerPlayerEntity player, String stat)
	{
		List<String> list = getAcceptedStatsList(player);
		return list != null && list.contains(stat);
	}
}
