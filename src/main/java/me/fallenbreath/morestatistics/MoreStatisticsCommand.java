package me.fallenbreath.morestatistics;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.ScoreHolderArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class MoreStatisticsCommand
{
	private static final String PREFIX = "morestatistics";

	public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher)
	{
		LiteralArgumentBuilder<ServerCommandSource> rootNode = literal(PREFIX).
                requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).
				then(literal("show").
						then(literal(MoreStatisticsScoreboardCriterion.BLOCK_PLACED_COUNT.getName()).
								then(CommandManager.argument("target", ScoreHolderArgumentType.scoreHolder()).
										executes(
												c -> showBlockPlaceCount(c.getSource(), ScoreHolderArgumentType.getScoreHolder(c, "target"))
										)
								)
						)
				);
		// maybe not
//		 dispatcher.register(rootNode);
	}

	private static int showBlockPlaceCount(ServerCommandSource source, String target)
	{
//		Registry.ITEM.stream().filter(item -> item instanceof BlockItem);
		return 1;
	}
}
