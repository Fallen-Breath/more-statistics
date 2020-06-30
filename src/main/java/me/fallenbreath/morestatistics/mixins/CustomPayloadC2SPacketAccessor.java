package me.fallenbreath.morestatistics.mixins;

import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(CustomPayloadC2SPacket.class)
public interface CustomPayloadC2SPacketAccessor
{
	@Accessor
	public Identifier getChannel();

	@Accessor
	public PacketByteBuf getData();
}
