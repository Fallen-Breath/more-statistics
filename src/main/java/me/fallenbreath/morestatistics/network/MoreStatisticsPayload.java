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

package me.fallenbreath.morestatistics.network;

import me.fallenbreath.fanetlib.api.nbt.FanetlibNbtUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.PacketByteBuf;

public class MoreStatisticsPayload
{
	private final int id;
	private final CompoundTag nbt;

	public MoreStatisticsPayload(int id, CompoundTag nbt)
	{
		this.id = id;
		this.nbt = nbt;
	}

	public MoreStatisticsPayload(PacketByteBuf buf)
	{
		this(buf.readVarInt(), FanetlibNbtUtils.readNbtAuto(buf));
	}

	public void write(PacketByteBuf buf)
	{
		buf.writeVarInt(this.id);
	 	buf.writeCompoundTag(this.nbt);
	}

	public int getPacketId()
	{
		return this.id;
	}

	public CompoundTag getNbt()
	{
		return this.nbt;
	}
}
