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

package me.fallenbreath.morestatistics.mixins.core.scoreboard;

import net.minecraft.scoreboard.ScoreboardCriterion;
import org.spongepowered.asm.mixin.Mixin;
//#if MC >= 11700
//$$ import org.spongepowered.asm.mixin.gen.Invoker;
//#endif

@Mixin(ScoreboardCriterion.class)
public interface ScoreboardCriterionAccessor
{
	//#if MC >= 11700
	//$$ @Invoker
	//$$ static ScoreboardCriterion invokeCreate(String name)
	//$$ {
	//$$ 	throw new RuntimeException();
	//$$ }
	//#endif
}