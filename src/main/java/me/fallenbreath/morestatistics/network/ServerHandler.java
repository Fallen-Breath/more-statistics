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

package me.fallenbreath.morestatistics.network;

import com.google.common.collect.Sets;
import me.fallenbreath.morestatistics.MoreStatisticsMod;
import me.fallenbreath.morestatistics.MoreStatisticsRegistry;
import me.fallenbreath.morestatistics.MoreStatisticsScoreboardCriterion;
import me.fallenbreath.morestatistics.utils.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

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
				CompoundTag nbt = Objects.requireNonNull(data.readCompoundTag());
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
						writeCompoundTag(Util.stringList2Nbt(MoreStatisticsScoreboardCriterion.getCriterionNameList()))
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
