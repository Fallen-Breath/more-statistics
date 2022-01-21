package me.fallenbreath.morestatistics.mixins.stats.break_bedrock;

import me.fallenbreath.morestatistics.utils.PistonPlacingMemory;
import net.minecraft.block.Block;
import net.minecraft.block.PistonBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin
{
	@Shadow public abstract Block getBlock();

	@ModifyArgs(
			method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/advancement/criterion/PlacedBlockCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V"
			)
	)
	private void rememberPistonPlacing(Args args)
	{
		if (this.getBlock() instanceof PistonBlock)
		{
			ServerPlayerEntity player = args.get(0);
			BlockPos blockPos = args.get(1);
			PistonPlacingMemory.onPlayerPlacedPiston(player, blockPos);
		}
	}
}
