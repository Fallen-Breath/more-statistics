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

import me.fallenbreath.fanetlib.api.event.FanetlibClientEvents;
import me.fallenbreath.fanetlib.api.packet.FanetlibPackets;
import me.fallenbreath.fanetlib.api.packet.PacketCodec;
import me.fallenbreath.fanetlib.api.packet.PacketId;
import me.fallenbreath.morestatistics.MoreStatisticsMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;

import java.util.function.Consumer;

public class Network
{
	public static final PacketId<MoreStatisticsPayload> FANETLIB_PACKET_ID = PacketId.of(MoreStatisticsMod.id("network_v2"));

	public static class C2S
	{
		public static final int STATS_LIST = 1;
		public static final int SCOREBOARD_CRITERION_QUERY = 2;

		public static CustomPayloadC2SPacket packet(int packetId, Consumer<CompoundTag> payloadBuilder)
		{
			CompoundTag nbt = new CompoundTag();
			payloadBuilder.accept(nbt);
			return FanetlibPackets.createC2S(FANETLIB_PACKET_ID, new MoreStatisticsPayload(packetId, nbt));
		}
	}

	public static class S2C
	{
		public static final int SCOREBOARD_CRITERION_LIST = 1;

		public static CustomPayloadS2CPacket packet(int packetId, Consumer<CompoundTag> payloadBuilder)
		{
			CompoundTag nbt = new CompoundTag();
			payloadBuilder.accept(nbt);
			return FanetlibPackets.createS2C(FANETLIB_PACKET_ID, new MoreStatisticsPayload(packetId, nbt));
		}
	}

	public static void initPackets()
	{
		FanetlibPackets.registerDual(
				FANETLIB_PACKET_ID,
				PacketCodec.of(MoreStatisticsPayload::write, MoreStatisticsPayload::new),
				(payload, ctx) -> ServerHandler.handleClientPacket(payload, ctx.getPlayer()),
				(payload, ctx) -> ClientHandler.handleServerPacket(payload, ctx.getPlayer())
		);
	}

	public static void initEvents()
	{
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
		{
			FanetlibClientEvents.registerGameJoinListener((client, networkHandler) -> {
				ClientHandler.requestScoreboardCriterionList(networkHandler);
			});
		}
	}
}
