package me.fallenbreath.morestatistics.mixins.stats.break_bedrock;

import me.fallenbreath.morestatistics.MoreStatisticsRegistry;
import me.fallenbreath.morestatistics.utils.PistonPlacingMemory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PistonBlock.class)
public abstract class PistonBlockMixin
{
	@ModifyArgs(
			//#if MC >= 11600
			//$$ method = "onSyncedBlockEvent",
			//#else
			method = "onBlockAction",
			//#endif
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"
			),
			require = 2
	)
	private void removeBlock(Args args, BlockState state, World world, BlockPos pistonPos, int type, int data)
	{
		BlockPos posToRemove = args.get(0);
		if (world.getBlockState(posToRemove).getBlock() == Blocks.BEDROCK)
		{
			ServerPlayerEntity player = PistonPlacingMemory.getTheOneWhoJustPlacedPiston(world, pistonPos);
			if (player != null)
			{
				player.increaseStat(MoreStatisticsRegistry.BREAK_BEDROCK, 1);
			}
		}
	}
}
