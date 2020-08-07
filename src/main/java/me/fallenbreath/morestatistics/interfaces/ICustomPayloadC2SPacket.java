package me.fallenbreath.morestatistics.interfaces;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;


public interface ICustomPayloadC2SPacket
{
    Identifier getMSPacketChannel();
    PacketByteBuf getMSPacketData();
}
