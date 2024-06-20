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

import com.google.common.collect.Sets;
import me.fallenbreath.morestatistics.mixins.core.stats.StatsAccessor;
import me.fallenbreath.morestatistics.utils.IdentifierUtil;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

//#if MC >= 11903
//$$ import net.minecraft.registry.Registries;
//#endif

import java.util.Set;

public class MoreStatisticsRegistry
{
	private static final Set<Identifier> STATS_SET = Sets.newLinkedHashSet();
	public static final Identifier BREAK_BEDROCK = register("break_bedrock", StatFormatter.DEFAULT);
	public static final Identifier ENDER_PEARL_ONE_CM = register("ender_pearl_one_cm", StatFormatter.DISTANCE);
	public static final Identifier FIREWORK_BOOST = register("firework_boost", StatFormatter.DEFAULT);
	public static final Identifier MEND_DURABILITY = register("mend_durability", StatFormatter.DEFAULT);
	public static final Identifier SUMMON_PHANTOM = register("summon_phantom", StatFormatter.DEFAULT);

	private static Identifier register(String name, StatFormatter statFormatter)
	{
		// vanilla stuffs, just like net.minecraft.stat.Stats#register
		Identifier statId = IdentifierUtil.of(name);  // using minecraft namespace. it's fine xd
		Registry.register(
				//#if MC >= 11903
				//$$ Registries.CUSTOM_STAT,
				//#else
				Registry.CUSTOM_STAT,
				//#endif
				statId, statId
		);
		StatsAccessor.getCUSTOM().getOrCreateStat(statId, statFormatter);

		// our stuffs
		STATS_SET.add(statId);
		MoreStatisticsMod.LOGGER.info("Added custom statistic " + statId);

		return statId;
	}

	public static void init()
	{
		// stuffs are done in <clinit>
		MoreStatisticsMod.LOGGER.info(String.format("%s enabled with %d new statistics", MoreStatisticsMod.NAME, STATS_SET.size()));
	}

	public static Set<Identifier> getStatsSet()
	{
		return STATS_SET;
	}
}
