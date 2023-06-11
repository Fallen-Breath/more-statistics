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

package me.fallenbreath.morestatistics.utils;

//#if MC >= 11600
//$$ import net.minecraft.util.registry.RegistryKey;
//#else
import net.minecraft.world.dimension.DimensionType;
//#endif
import net.minecraft.world.World;

import java.util.Objects;

public class DimensionWrapper
{
	//#if MC >= 11600
	//$$ private final RegistryKey<World> dimensionType;
	//#else
	private final DimensionType dimensionType;
	//#endif

	private DimensionWrapper(
			//#if MC >= 11600
			//$$ RegistryKey<World> dimensionType
			//#else
			DimensionType dimensionType
			//#endif
	)
	{
		this.dimensionType = dimensionType;
	}

	public static DimensionWrapper of(World world)
	{
		return new DimensionWrapper(
				//#if MC >= 11600
				//$$ world.getRegistryKey()
				//#else
				world.getDimension().getType()
				//#endif
		);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DimensionWrapper that = (DimensionWrapper) o;
		return Objects.equals(dimensionType, that.dimensionType);
	}

	@Override
	public int hashCode()
	{
		return this.dimensionType.hashCode();
	}
}
