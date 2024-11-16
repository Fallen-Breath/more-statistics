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

import me.fallenbreath.morestatistics.network.Network;
import me.fallenbreath.morestatistics.utils.IdentifierUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoreStatisticsMod implements ModInitializer
{
	public static final String NAME = "More Statistics";
	public static final String MOD_ID = "more-statistics";
	public static String version;
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize()
	{
		version = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();

		Network.initEvents();
	}

	public static String getModId()
	{
		return MOD_ID;
	}

	public static String getModVersion()
	{
		return version;
	}

	public static Identifier id(String name)
	{
		return IdentifierUtil.of(MOD_ID, name);
	}
}
