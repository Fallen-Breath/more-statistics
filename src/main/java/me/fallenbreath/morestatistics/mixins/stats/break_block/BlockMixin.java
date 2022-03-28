package me.fallenbreath.morestatistics.mixins.stats.break_block;

import me.fallenbreath.morestatistics.MoreStatisticsRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin
{
	@Inject(
			method = "onBreak",
			at = @At("TAIL")
	)
	private void onPlayerBreakBlock(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo info)
	{
		player.increaseStat(MoreStatisticsRegistry.BREAK_BLOCK, 1);
	}
}
