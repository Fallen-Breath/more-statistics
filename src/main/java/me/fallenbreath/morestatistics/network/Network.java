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

import io.netty.buffer.Unpooled;
import me.fallenbreath.morestatistics.MoreStatisticsMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

import java.util.function.Consumer;

public class Network
{
	public static final Identifier CHANNEL = MoreStatisticsMod.id("network_v2");

	public static class C2S
	{
		public static final int STATS_LIST = 1;
		public static final int SCOREBOARD_CRITERION_QUERY = 2;

		public static CustomPayloadC2SPacket packet(int packetId, Consumer<CompoundTag> payloadBuilder)
		{
			CompoundTag nbt = new CompoundTag();
			payloadBuilder.accept(nbt);

			//#if MC >= 12002
			//$$ return new CustomPayloadC2SPacket(new MoreStatisticsPayload(packetId, nbt));
			//#else
			PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
			packetByteBuf.writeVarInt(packetId);
			packetByteBuf.writeCompoundTag(nbt);
			return new CustomPayloadC2SPacket(CHANNEL, packetByteBuf);
			//#endif
		}
	}

	public static class S2C
	{
		public static final int SCOREBOARD_CRITERION_LIST = 1;

		public static CustomPayloadS2CPacket packet(int packetId, Consumer<CompoundTag> payloadBuilder)
		{
			CompoundTag nbt = new CompoundTag();
			payloadBuilder.accept(nbt);

			//#if MC >= 12002
			//$$ return new CustomPayloadS2CPacket(new MoreStatisticsPayload(packetId, nbt));
			//#else
			PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
			packetByteBuf.writeVarInt(packetId);
			packetByteBuf.writeCompoundTag(nbt);
			return new CustomPayloadS2CPacket(CHANNEL, packetByteBuf);
			//#endif
		}
	}
}
