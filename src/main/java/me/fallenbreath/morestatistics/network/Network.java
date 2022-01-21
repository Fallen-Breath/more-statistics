package me.fallenbreath.morestatistics.network;

import io.netty.buffer.Unpooled;
import me.fallenbreath.morestatistics.MoreStatisticsMod;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class Network
{
	public static final Identifier CHANNEL = MoreStatisticsMod.id("network");

	public static class C2S
	{
		public static final int STATS_LIST = 1;
		public static final int SCOREBOARD_CRITERION_QUERY = 2;

		public static CustomPayloadC2SPacket packet(Consumer<PacketByteBuf> byteBufBuilder)
		{
			PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
			byteBufBuilder.accept(packetByteBuf);
			return new CustomPayloadC2SPacket(CHANNEL, packetByteBuf);
		}
	}

	public static class S2C
	{
		public static final int SCOREBOARD_CRITERION_LIST = 1;

		public static CustomPayloadS2CPacket packet(Consumer<PacketByteBuf> byteBufBuilder)
		{
			PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
			byteBufBuilder.accept(packetByteBuf);
			return new CustomPayloadS2CPacket(CHANNEL, packetByteBuf);
		}
	}
}
