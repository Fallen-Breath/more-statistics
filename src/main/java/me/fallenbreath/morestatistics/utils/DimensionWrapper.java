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
