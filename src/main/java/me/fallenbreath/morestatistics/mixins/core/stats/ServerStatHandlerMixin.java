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

package me.fallenbreath.morestatistics.mixins.core.stats;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import me.fallenbreath.morestatistics.network.ServerHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerStatHandler.class)
public abstract class ServerStatHandlerMixin
{
	@Redirect(
			method = "sendStats",
			at = @At(
					value = "INVOKE",
					target = "Lit/unimi/dsi/fastutil/objects/Object2IntMap;put(Ljava/lang/Object;I)I",
					remap = false
			)
	)
	private int dontPutNotVanillaStat(Object2IntMap<Stat<?>> map, Object obj, int value, ServerPlayerEntity player)
	{
		if (ServerHandler.canSendStat(player, (Stat<?>)obj))
		{
			return map.put((Stat<?>)obj, value);
		}
		else
		{
			return map.defaultReturnValue();
		}
	}
}
