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
import me.fallenbreath.morestatistics.utils.Util;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientHandler
{
	private static Set<String> SCOREBOARD_CRITERION_NAMES = Collections.emptySet();

	public static void handleServerPacket(PacketByteBuf data, ClientPlayerEntity player)
	{
		int id = data.readVarInt();
		switch (id)
		{
			case Network.S2C.SCOREBOARD_CRITERION_LIST:
				CompoundTag nbt = Objects.requireNonNull(data.readCompoundTag());
				SCOREBOARD_CRITERION_NAMES = Sets.newHashSet(Util.nbt2StringList(nbt));
				MoreStatisticsMod.LOGGER.debug("Received CRITERION NAMES: {}", SCOREBOARD_CRITERION_NAMES);
				break;
		}
	}

	public static Set<String> getScoreboardCriterionNames()
	{
		return SCOREBOARD_CRITERION_NAMES;
	}

	public static void sendAcceptedStatList(ClientPlayNetworkHandler clientPlayNetworkHandler)
	{
		List<String> myAcceptedStats = MoreStatisticsRegistry.getStatsSet().stream().map(Identifier::toString).collect(Collectors.toList());
		clientPlayNetworkHandler.sendPacket(Network.C2S.packet(buf -> buf.
				writeVarInt(Network.C2S.STATS_LIST).
				writeCompoundTag(Util.stringList2Nbt(myAcceptedStats))
		));
	}

	public static void requestScoreboardCriterionList(ClientPlayNetworkHandler clientPlayNetworkHandler)
	{
		clientPlayNetworkHandler.sendPacket(Network.C2S.packet(buf -> buf.writeVarInt(Network.C2S.SCOREBOARD_CRITERION_QUERY)));
	}
}
