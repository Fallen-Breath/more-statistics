package me.fallenbreath.morestatistics.mixins.network;

import me.fallenbreath.morestatistics.interfaces.ICustomPayloadC2SPacket;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(CustomPayloadC2SPacket.class)
public abstract class CustomPayloadC2SPacketMixin implements ICustomPayloadC2SPacket
{
	@Shadow
	private Identifier channel;

	@Shadow
	private PacketByteBuf data;

	@Override
	public Identifier getMSPacketChannel()
	{
		return channel;
	}

	@Override
	public PacketByteBuf getMSPacketData()
	{
		return new PacketByteBuf(this.data.copy());
	}
}
