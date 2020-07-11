package me.fallenbreath.morestatistics.interfaces;

import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;


public interface ICustomPayloadC2SPacket
{
    Identifier getPacketChannel();
    PacketByteBuf getPacketData();
}
