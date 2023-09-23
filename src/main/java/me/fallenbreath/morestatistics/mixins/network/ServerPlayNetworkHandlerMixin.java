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

import me.fallenbreath.morestatistics.network.MoreStatisticsPayload;
import me.fallenbreath.morestatistics.network.Network;
import me.fallenbreath.morestatistics.network.ServerHandler;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 12002
//$$ import net.minecraft.server.network.ServerCommonNetworkHandler;
//#endif

@Mixin(
		//#if MC >= 12002
		//$$ ServerCommonNetworkHandler.class
		//#else
		ServerPlayNetworkHandler.class
		//#endif
)
public abstract class ServerPlayNetworkHandlerMixin
{
	@Inject(method = "onCustomPayload", at = @At("HEAD"), cancellable = true)
	private void onCustomPayload(CustomPayloadC2SPacket packet, CallbackInfo ci)
	{
		//#if MC >= 12002
		//$$ if (packet.payload() instanceof MoreStatisticsPayload payload && (Object)this instanceof ServerPlayNetworkHandler self)
		//$$ {
		//$$ 	ServerHandler.handleClientPacket(payload, self.player);
		//$$ 	ci.cancel();
		//$$ }
		//#else
		Identifier channel = ((CustomPayloadC2SPacketAccessor)packet).getChannel();
		if (Network.CHANNEL.equals(channel))
		{
			MoreStatisticsPayload payload = new MoreStatisticsPayload(((CustomPayloadC2SPacketAccessor)packet).getData());
			ServerHandler.handleClientPacket(payload, ((ServerPlayNetworkHandler)(Object)this).player);
			ci.cancel();
		}
		//#endif
	}
}
