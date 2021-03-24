package me.scrouthtv.maps;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class VoidGenerator extends ChunkGenerator {
	
	@Nonnull
	@Override
	public ChunkData generateChunkData(@Nonnull final World world, @Nonnull final Random random, final int x, final int z, @Nonnull final ChunkGenerator.BiomeGrid biome) {
		return createChunkData(world);
	}
	
	@Nonnull
	@Override
	public List<BlockPopulator> getDefaultPopulators(@Nonnull final World world) {
		return Collections.emptyList();
	}
	
	@Override
	public boolean isParallelCapable() {
		return true;
	}
	
	@Override
	public boolean shouldGenerateCaves() {
		return false;
	}
	
	@Override
	public boolean shouldGenerateDecorations() {
		return false;
	}
	
	@Override
	public boolean shouldGenerateMobs() {
		return false;
	}
	
	@Override
	public boolean shouldGenerateStructures() {
		return false;
	}
}
