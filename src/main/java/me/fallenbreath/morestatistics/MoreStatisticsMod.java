package me.fallenbreath.morestatistics;

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
		return new Identifier(MOD_ID, name);
	}
}
