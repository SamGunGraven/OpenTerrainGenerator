package com.khorn.terraincontrol.forge.generator;

import net.minecraft.block.Block;

import com.khorn.terraincontrol.configuration.WorldConfig;
import com.khorn.terraincontrol.forge.ForgeWorld;
import com.khorn.terraincontrol.generator.ChunkProviderTC;
import com.khorn.terraincontrol.generator.ObjectSpawner;

import java.util.List;

import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class ChunkProvider implements IChunkProvider
{

    private ForgeWorld world;
    private World worldHandle;
    private boolean TestMode = false;

    private ChunkProviderTC generator;
    private ObjectSpawner spawner;

    public ChunkProvider(ForgeWorld _world)
    {
        // super(_world.getWorld(), _world.getSeed());

        this.world = _world;
        this.worldHandle = _world.getWorld();

        this.TestMode = world.getSettings().worldConfig.ModeTerrain == WorldConfig.TerrainMode.TerrainTest;

        this.generator = new ChunkProviderTC(this.world.getSettings(), this.world);
        this.spawner = new ObjectSpawner(this.world.getSettings(), this.world);

    }

    @Override
    public boolean chunkExists(int i, int i1)
    {
        return true;
    }

    @Override
    public Chunk provideChunk(int chunkX, int chunkZ)
    {
        Chunk chunk = new Chunk(this.worldHandle, chunkX, chunkZ);

        byte[] BlockArray = this.generator.generate(chunkX, chunkZ);
        ExtendedBlockStorage[] sections = chunk.getBlockStorageArray();

        int i1 = BlockArray.length / 256;
        for (int blockX = 0; blockX < 16; blockX++)
            for (int blockZ = 0; blockZ < 16; blockZ++)
                for (int blockY = 0; blockY < i1; blockY++)
                {
                    int block = BlockArray[(blockX << ChunkProviderTC.HEIGHT_BITS_PLUS_FOUR | blockZ << ChunkProviderTC.HEIGHT_BITS | blockY)];
                    if (block != 0)
                    {
                        int sectionId = blockY >> 4;
                        if (sections[sectionId] == null)
                        {
                            // Second argument is skylight
                            sections[sectionId] = new ExtendedBlockStorage(sectionId << 4, !chunk.worldObj.provider.hasNoSky);
                        }
                        // We should optimize this
                        sections[sectionId].func_150818_a(blockX, blockY & 0xF, blockZ, Block.getBlockById(block & 0xFF));
                    }
                }
        world.FillChunkForBiomes(chunk, chunkX, chunkZ);

        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public Chunk loadChunk(int i, int i1)
    {
        return provideChunk(i, i1);
    }

    @Override
    public void populate(IChunkProvider ChunkProvider, int x, int z)
    {
        if (this.TestMode)
            return;
        BlockSand.fallInstantly = true;
        this.world.LoadChunk(x, z);
        this.spawner.populate(x, z);
        BlockSand.fallInstantly = false;
    }

    @Override
    public boolean saveChunks(boolean b, IProgressUpdate il)
    {
        return true;
    }

    @Override
    public boolean unloadQueuedChunks()
    {
        return false;
    }

    @Override
    public boolean canSave()
    {
        return true;
    }

    @Override
    public String makeString()
    {
        return "TerrainControlLevelSource";
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List getPossibleCreatures(EnumCreatureType paramaca, int paramInt1, int paramInt2, int paramInt3)
    {
        BiomeGenBase Biome = this.worldHandle.getBiomeGenForCoords(paramInt1, paramInt3);
        if (Biome == null)
        {
            return null;
        }
        return Biome.getSpawnableList(paramaca);
    }

    
    @Override
    public ChunkPosition func_147416_a(World world, String s, int x, int y, int z)
    {
        // Gets the nearest stronghold
        if (("Stronghold".equals(s)) && (this.world.strongholdGen != null))
        {
            return this.world.strongholdGen.func_151545_a(world, x, y, z);
        }
        return null;
    }

    @Override
    public int getLoadedChunkCount()
    {
        return 0;
    }

    @Override
    public void recreateStructures(int chunkX, int chunkZ)
    {
        if (world.mineshaftGen != null)
        {
            world.mineshaftGen.func_151539_a(this, world.getWorld(), chunkX, chunkZ, null);
        }
        if (world.villageGen != null)
        {
            world.villageGen.func_151539_a(this, world.getWorld(), chunkX, chunkZ, null);
        }
        if (world.strongholdGen != null)
        {
            world.strongholdGen.func_151539_a(this, world.getWorld(), chunkX, chunkZ, null);
        }
        if (world.rareBuildingGen != null)
        {
            world.rareBuildingGen.func_151539_a(this, world.getWorld(), chunkX, chunkZ, null);
        }
        if (world.netherFortressGen != null)
        {
            world.netherFortressGen.func_151539_a(this, world.getWorld(), chunkX, chunkZ, null);
        }
    }

    @Override
    public void saveExtraData()
    {
        // Empty, just like Minecraft's ChunkProviderGenerate
    }
}
