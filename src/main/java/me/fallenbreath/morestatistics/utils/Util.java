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

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;

import java.util.List;

public class Util
{
	public static CompoundTag stringList2Nbt(List<String> list)
	{
		CompoundTag nbt = new CompoundTag();
		nbt.putInt("length", list.size());
		for (int i = 0; i < list.size(); i++)
		{
			nbt.putString(String.valueOf(i), list.get(i));
		}
		return nbt;
	}

	public static List<String> nbt2StringList(CompoundTag nbt)
	{
		List<String> list = Lists.newArrayList();
		int length = nbt.getInt("length");
		for (int i = 0; i < length; i++)
		{
			list.add(nbt.getString(String.valueOf(i)));
		}
		return list;
	}
}
