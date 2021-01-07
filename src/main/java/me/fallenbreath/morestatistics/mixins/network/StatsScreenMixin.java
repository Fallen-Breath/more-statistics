package me.fallenbreath.morestatistics.mixins.network;

import io.netty.buffer.Unpooled;
import me.fallenbreath.morestatistics.MoreStatisticsRegistry;
import me.fallenbreath.morestatistics.network.Network;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.stat.StatHandler;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(StatsScreen.class)
public abstract class StatsScreenMixin extends Screen
{
	public StatsScreenMixin(Screen parent, StatHandler statHandler)
	{
		super(new TranslatableText("gui.stats"));
	}

	@Inject(
			method = "init",
			at = @At(value = "HEAD")
	)
	void onStatsScreenInit(CallbackInfo ci)
	{
		if (this.client != null && this.client.getNetworkHandler() != null)
		{
			this.client.getNetworkHandler().sendPacket(new CustomPayloadC2SPacket(
					Network.CHANNEL,
					(new PacketByteBuf(Unpooled.buffer()))
							.writeVarInt(Network.STATS_LIST)
							.writeCompoundTag(MoreStatisticsRegistry.getStatsListNbt())
			));
		}
	}
}
