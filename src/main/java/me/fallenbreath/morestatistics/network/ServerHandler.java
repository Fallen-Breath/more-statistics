package me.fallenbreath.morestatistics.network;

import com.google.common.collect.Sets;
import me.fallenbreath.morestatistics.MoreStatisticsMod;
import me.fallenbreath.morestatistics.MoreStatisticsRegistry;
import me.fallenbreath.morestatistics.MoreStatisticsScoreboardCriterion;
import me.fallenbreath.morestatistics.utils.Util;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Collectors;

public class ServerHandler
{
	private static final Object LOCK = new Object();
	private static final Map<ServerPlayerEntity, Set<Identifier>> acceptedStats = new WeakHashMap<>();

	public static void handleClientPacket(PacketByteBuf data, ServerPlayerEntity player)
	{
		int id = data.readVarInt();
		switch (id)
		{
			case Network.C2S.STATS_LIST:
				NbtCompound nbt = Objects.requireNonNull(data.readNbt());
				List<Identifier> list = Util.nbt2StringList(nbt).stream().map(Identifier::new).collect(Collectors.toList());
				MoreStatisticsMod.LOGGER.debug("Received accepted stats list from player {}: {}", player.getName().getString(), list);
				synchronized (LOCK)
				{
					acceptedStats.put(player, Sets.newHashSet(list));
				}
				break;

			case Network.C2S.SCOREBOARD_CRITERION_QUERY:
				MoreStatisticsMod.LOGGER.debug("Received scoreboard criterion query from player {}", player.getName().getString());
				player.networkHandler.sendPacket(Network.S2C.packet(buf -> buf.
						writeVarInt(Network.S2C.SCOREBOARD_CRITERION_LIST).
						writeNbt(Util.stringList2Nbt(MoreStatisticsScoreboardCriterion.getCriterionNameList()))
				));
				break;
		}
	}

	public static boolean canSendStat(ServerPlayerEntity player, Stat<?> stat)
	{
		Object key = stat.getValue();
		if (key instanceof Identifier)
		{
			Identifier statId = (Identifier)key;
			if (MoreStatisticsRegistry.getStatsSet().contains(statId))
			{
				synchronized (LOCK)
				{
					return acceptedStats.getOrDefault(player, Collections.emptySet()).contains(statId);
				}
			}
		}
		return true;
	}
}
