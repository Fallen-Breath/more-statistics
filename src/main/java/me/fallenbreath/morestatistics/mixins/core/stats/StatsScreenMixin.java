package me.fallenbreath.morestatistics.mixins.core.stats;

import me.fallenbreath.morestatistics.network.ClientHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StatsScreen.class)
public abstract class StatsScreenMixin extends Screen
{
	protected StatsScreenMixin(Text title)
	{
		super(title);
	}

	@Inject(method = "init", at = @At(value = "HEAD"))
	private void onStatsScreenInit(CallbackInfo ci)
	{
		if (this.minecraft != null && this.minecraft.getNetworkHandler() != null)
		{
			ClientHandler.sendAcceptedStatList(this.minecraft.getNetworkHandler());
		}
	}
}
