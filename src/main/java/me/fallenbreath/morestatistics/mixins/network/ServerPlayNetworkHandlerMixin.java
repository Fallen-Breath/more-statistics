package me.fallenbreath.morestatistics.mixins.network;

import me.fallenbreath.morestatistics.interfaces.ICustomPayloadC2SPacket;
import me.fallenbreath.morestatistics.network.Network;
import me.fallenbreath.morestatistics.network.ServerHandler;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin
{
	@Shadow
	public ServerPlayerEntity player;

	@Inject(
			method = "onCustomPayload",
			at = @At("HEAD"),
			cancellable = true
	)
	private void onCustomPayload(CustomPayloadC2SPacket packet, CallbackInfo ci)
	{
		synchronized (Network.sync)
		{
			Identifier channel = ((ICustomPayloadC2SPacket) packet).getMSPacketChannel();
			if (Network.CHANNEL.equals(channel))
			{
				ServerHandler.handleStatsListUpdate(((ICustomPayloadC2SPacket) packet).getMSPacketData(), player);
				ci.cancel();
			}
		}
	}

	@Inject(
			method = "onDisconnected",
			at = @At("HEAD")
	)
	private void onPlayerDisconnect(Text reason, CallbackInfo ci)
	{
		ServerHandler.onPlayerLoggedOut(this.player);
	}
}
