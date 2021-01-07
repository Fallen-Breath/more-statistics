package me.fallenbreath.morestatistics.mixins.network;

import me.fallenbreath.morestatistics.network.PlayerInformation;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin
{
	@Inject(
			method = "shutdown",
			at = @At("HEAD")
	)
	private void onServerShutdown(CallbackInfo ci)
	{
		PlayerInformation.removeAllPlayers();
	}
}
