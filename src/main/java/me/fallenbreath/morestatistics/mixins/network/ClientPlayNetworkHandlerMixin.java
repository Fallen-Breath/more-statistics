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

package me.fallenbreath.morestatistics.mixins.network;

import me.fallenbreath.morestatistics.network.ClientHandler;
import me.fallenbreath.morestatistics.network.MoreStatisticsPayload;
import me.fallenbreath.morestatistics.network.Network;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 12002
//$$ import net.minecraft.client.MinecraftClient;
//$$ import net.minecraft.client.network.ClientCommonNetworkHandler;
//$$ import net.minecraft.client.network.ClientConnectionState;
//$$ import net.minecraft.network.ClientConnection;
//$$ import net.minecraft.network.packet.CustomPayload;
//$$ import org.spongepowered.asm.mixin.Shadow;
//#endif

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin
		//#if MC >= 12002
		//$$ extends ClientCommonNetworkHandler
		//#endif
{
	//#if MC >= 12002
	//$$ protected ClientPlayNetworkHandlerMixin(MinecraftClient client, ClientConnection connection, ClientConnectionState connectionState)
	//$$ {
	//$$ 	super(client, connection, connectionState);
	//$$ }
	//#endif

	@Inject(
			method = "onCustomPayload",
			//#if MC >= 12002
			//$$ at = @At("HEAD"),
			//#else
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/network/packet/s2c/play/CustomPayloadS2CPacket;getChannel()Lnet/minecraft/util/Identifier;",
					ordinal = 0
			),
			//#endif
			cancellable = true
	)
	private void onCustomPayload(
			//#if MC >= 12002
			//$$ CustomPayload mcPayload,
			//#else
			CustomPayloadS2CPacket packet,
			//#endif
			CallbackInfo ci
	)
	{
		//#if MC >= 12002
		//$$ if (mcPayload instanceof MoreStatisticsPayload payload)
		//$$ {
		//$$ 	this.client.execute(() -> ClientHandler.handleServerPacket(payload, MinecraftClient.getInstance().player));
		//$$ 	ci.cancel();
		//$$ }
		//#else
		Identifier channel = ((CustomPayloadS2CPacketAccessor)packet).getChannel();
		if (Network.CHANNEL.equals(channel))
		{
			MoreStatisticsPayload payload = new MoreStatisticsPayload(((CustomPayloadS2CPacketAccessor)packet).getData());
			ClientHandler.handleServerPacket(payload, MinecraftClient.getInstance().player);
			ci.cancel();
		}
		//#endif
	}

	@Inject(method = "onGameJoin", at = @At("RETURN"))
	private void playerJoinClientHook(GameJoinS2CPacket packet, CallbackInfo ci)
	{
		ClientHandler.requestScoreboardCriterionList((ClientPlayNetworkHandler)(Object)this);
	}
}
