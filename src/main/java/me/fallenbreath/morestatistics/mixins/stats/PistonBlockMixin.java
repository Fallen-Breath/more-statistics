package me.fallenbreath.morestatistics.mixins.stats;

import me.fallenbreath.morestatistics.MoreStatisticsRegistry;
import me.fallenbreath.morestatistics.utils.Util;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(PistonBlock.class)
public abstract class PistonBlockMixin
{
	@Redirect(
			method = "onSyncedBlockEvent",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"
			),
			require = 2
	)
	private boolean removeBlock(World world, BlockPos pos, boolean move)
	{
		if (world.getBlockState(pos).getBlock() == Blocks.BEDROCK)
		{
			Util.addStatsToNearestPlayers(world, pos, 5, MoreStatisticsRegistry.BREAK_BEDROCK, 1, true);
		}
		return world.removeBlock(pos, move);
	}
}
