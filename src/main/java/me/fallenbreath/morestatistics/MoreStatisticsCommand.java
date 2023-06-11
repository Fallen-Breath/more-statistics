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

package me.fallenbreath.morestatistics;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.arguments.ScoreHolderArgumentType;
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
