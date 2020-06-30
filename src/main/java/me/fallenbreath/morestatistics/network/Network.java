package me.fallenbreath.morestatistics.network;

import me.fallenbreath.morestatistics.MoreStatistics;
import net.minecraft.util.Identifier;

public class Network
{
	public static final Identifier CHANNEL = new Identifier(MoreStatistics.id);
	public static final Object sync = new Object();
	public static final int STATS_LIST = 23333;
}
