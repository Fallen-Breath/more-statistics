package me.fallenbreath.morestatistics.mixins.core;

import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;


@Mixin(Stats.class)
public interface StatsAccessor
{
	@Invoker
	static Identifier callRegister(String string, StatFormatter statFormatter)
	{
		return null;
	}

	@Invoker
	static <T> StatType<T> callRegisterType(String string, Registry<T> registry)
	{
		return null;
	}
}
