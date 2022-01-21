package me.fallenbreath.morestatistics.mixins.scoreboard.blockPlacedCount;

import me.fallenbreath.morestatistics.MoreStatisticsScoreboardCriterion;
import net.minecraft.item.BlockItem;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin
{
	@ModifyArg(
			method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/advancement/criterion/PlacedBlockCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V"
			),
			index = 0
	)
	private ServerPlayerEntity onPlayerPlacedBlock(ServerPlayerEntity player)
	{
		player.getScoreboard().forEachScore(MoreStatisticsScoreboardCriterion.BLOCK_PLACED_COUNT, player.getEntityName(), ScoreboardPlayerScore::incrementScore);
		return player;
	}
}
