package me.fallenbreath.morestatistics.network;

import me.fallenbreath.morestatistics.utils.Util;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;

import java.util.List;


public class ServerHandler
{
	public static void handleStatsListUpdate(PacketByteBuf data, ServerPlayerEntity player)
	{
		int id = data.readVarInt();
		if (id == Network.STATS_LIST)
		{
			List<String> list = Util.nbt2StringList(data.readCompoundTag());
			if (list != null)
			{
				PlayerInformation.setAcceptedStatsList(player, list);
			}
		}
	}

	public static void onPlayerLoggedOut(ServerPlayerEntity player)
	{
		PlayerInformation.removePlayer(player);
	}
}
