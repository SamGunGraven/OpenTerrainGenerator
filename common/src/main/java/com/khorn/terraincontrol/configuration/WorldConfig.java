package com.khorn.terraincontrol.configuration;

import com.khorn.terraincontrol.LocalMaterialData;
import com.khorn.terraincontrol.LocalWorld;
import com.khorn.terraincontrol.TerrainControl;
import com.khorn.terraincontrol.configuration.io.SettingsMap;
import com.khorn.terraincontrol.configuration.settingType.Setting;
import com.khorn.terraincontrol.configuration.standard.BiomeStandardValues;
import com.khorn.terraincontrol.configuration.standard.PluginStandardValues;
import com.khorn.terraincontrol.configuration.standard.WorldStandardValues;
import com.khorn.terraincontrol.customobjects.CustomObjectCollection;
import com.khorn.terraincontrol.generator.biome.BiomeGenerator;
import com.khorn.terraincontrol.logging.LogMarker;
import com.khorn.terraincontrol.util.minecraftTypes.DefaultBiome;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

public class WorldConfig extends ConfigFile
{	
    public final File settingsDir;
    private final Comparator<Entry<String, Integer>> CBV = new Comparator<Entry<String, Integer>>()
    {
        @Override
        public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2)
        {
            return o1.getValue() - o2.getValue();
        }
    };

    public Map<String, Integer> customBiomeGenerationIds = new HashMap<String, Integer>();

    // Holds all world CustomObjects.
    public final CustomObjectCollection worldObjects;

    // Biome Groups and special biome lists
    public BiomeGroupManager biomeGroupManager;

    public List<String> IsleBiomes = new ArrayList<String>();
    public List<String> BorderBiomes = new ArrayList<String>();

    // Dimensions
    public List<String> Dimensions = new ArrayList<String>();
    
    // For old biome generator
    public double oldBiomeSize;

    public double minMoisture;
    public double maxMoisture;
    public double minTemperature;
    public double maxTemperature;

    // Biome generator
    public int GenerationDepth;
    public int BiomeRarityScale;

    public int LandRarity;
    public int LandSize;
    public int LandFuzzy;

    public int maxSmoothRadius = 2;

    public boolean FrozenOcean;
    public boolean FreezeAllColdGroupBiomes;
    public double FrozenOceanTemperature;

    // Rivers

    public int riverRarity;
    public int riverSize;
    public boolean riversEnabled;
    public boolean improvedRivers;
    public boolean randomRivers;

    // Biome image

    public String imageFile;
    public ImageOrientation imageOrientation;
    public ImageMode imageMode;
    // public int imageZoom;
    public String imageFillBiome;
    public int imageXOffset;
    public int imageZOffset;

    public HashMap<Integer, Integer> biomeColorMap;

    // Look settings
    public int WorldFog;
    public float WorldFogR;
    public float WorldFogG;
    public float WorldFogB;

    public int WorldNightFog;
    public float WorldNightFogR;
    public float WorldNightFogG;
    public float WorldNightFogB;

    // Specific biome settings

    // Caves
    public int caveRarity;
    public int caveFrequency;
    public int caveMinAltitude;
    public int caveMaxAltitude;
    public int individualCaveRarity;
    public int caveSystemFrequency;
    public int caveSystemPocketChance;
    public int caveSystemPocketMinSize;
    public int caveSystemPocketMaxSize;
    public boolean evenCaveDistribution;

    // Ravines
    public int ravineRarity;
    public int ravineMinAltitude;
    public int ravineMaxAltitude;
    public int ravineMinLength;
    public int ravineMaxLength;
    public double ravineDepth;

    // Strongholds
    public boolean strongholdsEnabled;
    public double strongholdDistance;
    public int strongholdCount;
    public int strongholdSpread;

    // Villages
    public boolean villagesEnabled;
    public int villageSize;
    public int villageDistance; // Has a minimum of 9

    // Rare buildings (also swamp huts and jungle temples)
    public boolean rareBuildingsEnabled;
    public int minimumDistanceBetweenRareBuildings;
    public int maximumDistanceBetweenRareBuildings;

    // Ocean monuments
    public boolean oceanMonumentsEnabled;
    public int oceanMonumentGridSize;
    public int oceanMonumentRandomOffset;

    // Other structures
    public boolean mineshaftsEnabled;
    public boolean netherFortressesEnabled;

    // Terrain
    public boolean oldTerrainGenerator;

    public int waterLevelMax;
    public int waterLevelMin;
    public LocalMaterialData waterBlock;
    public LocalMaterialData iceBlock;
    public LocalMaterialData cooledLavaBlock;
    public boolean betterSnowFall;
    public boolean fullyFreezeLakes;
    public boolean useTemperatureForSnowHeight;

    public double fractureHorizontal;
    public double fractureVertical;

    public boolean disableBedrock;
    public boolean flatBedrock;
    public boolean ceilingBedrock;
    public LocalMaterialData bedrockBlock;
    public boolean populationBoundsCheck;
    public boolean populateUsingSavedBiomes;
    public boolean removeSurfaceStone;

    public int objectSpawnRatio;
    public File customObjectsDirectory;

    public ConfigMode SettingsMode;
    public TerrainMode ModeTerrain;
    public Class<? extends BiomeGenerator> biomeMode;

    public boolean BiomeConfigsHaveReplacement = false;

    public int worldHeightScaleBits;
    public int worldHeightScale;
    public int worldHeightCapBits;
    public int worldHeightCap;

    public long resourcesSeed;
    public int maximumCustomStructureRadius;

    // Settings for console commands
    public String author;
    public String description;

	public int PreGenerationRadius;
	public int WorldBorderRadius;
    public String worldSeed;
    
    public boolean Cartographer;
    public ArrayList<LocalMaterialData> DimensionPortalMaterials;
    public String dimensionBelow;
    public String dimensionAbove;   
    
    public String BO3AtSpawn;    
    
    public boolean teleportToSpawnOnly; // If this is set to true then portals to this dimension will always teleport players to the world's spawn point.
    
	// Game rules for worlds used as dimensions with Forge // TODO: Apply to overworld too?
	
    public String commandBlockOutput; // Whether command blocks should notify admins when they perform commands
    public String disableElytraMovementCheck; // Whether the server should skip checking player speed when the player is wearing elytra. Often helps with jittering due to lag in multiplayer, but may also be used to travel unfairly long distances in survival mode (cheating).
    public String doDaylightCycle; // Whether the day-night cycle and moon phases progress
    public String doEntityDrops; // Whether entities that are not mobs should have drops
    public String doFireTick; // Whether fire should spread and naturally extinguish
    //public String doLimitedCrafting; // Whether players should only be able to craft recipes that they've unlocked first // TODO: Implement for 1.12
    public String doMobLoot; // Whether mobs should drop items
    public String doMobSpawning; // Whether mobs should naturally spawn. Does not affect monster spawners.
    public String doTileDrops; // Whether blocks should have drops
    public String doWeatherCycle; // Whether the weather will change
    //public String gameLoopFunction = "true"; // The function to run every game tick // TODO: Implement for 1.12
    public String keepInventory; // Whether the player should keep items in their inventory after death
    public String logAdminCommands; // Whether to log admin commands to server log
    //public String maxCommandChainLength = "65536"; // Determines the number at which the chain command block acts as a "chain". // TODO: Implement for 1.12
    public String maxEntityCramming; // The maximum number of other pushable entities a mob or player can push, before taking 3 doublehearts suffocation damage per half-second. Setting to 0 disables the rule. Damage affects survival-mode or adventure-mode players, and all mobs but bats. Pushable entities include non-spectator-mode players, any mob except bats, as well as boats and minecarts.
    public String mobGriefing; // Whether creepers, zombies, endermen, ghasts, withers, ender dragons, rabbits, sheep, and villagers should be able to change blocks and whether villagers, zombies, skeletons, and zombie pigmen can pick up items
    public String naturalRegeneration; // Whether the player can regenerate health naturally if their hunger is full enough (doesn't affect external healing, such as golden apples, the Regeneration effect, etc.)
    public String randomTickSpeed; // How often a random block tick occurs (such as plant growth, leaf decay, etc.) per chunk section per game tick. 0 will disable random ticks, higher numbers will increase random ticks 
    public String reducedDebugInfo; // Whether the debug screen shows all or reduced information; and whether the effects of F3+B (entity hitboxes) and F3+G (chunk boundaries) are shown.
    public String sendCommandFeedback; // Whether the feedback from commands executed by a player should show up in chat. Also affects the default behavior of whether command blocks store their output text
    public String showDeathMessages; // Whether death messages are put into chat when a player dies. Also affects whether a message is sent to the pet's owner when the pet dies.
    public String spawnRadius; // The number of blocks outward from the world spawn coordinates that a player will spawn in when first joining a server or when dying without a spawnpoint.
    public String spectatorsGenerateChunks; // Whether players in spectator mode can generate chunks	
	
    // World provider settings for worlds used as dimensions with Forge : TODO: Apply to overworld too?
    
	public String welcomeMessage; // A message to display to the user when they transfer to this dimension.
	public String departMessage; // A Message to display to the user when they transfer out of this dimension.
	//public boolean isHellWorld = false; // DoesWaterVaporize sets this
	public boolean hasSkyLight; // A boolean that tells if a world does not have a sky. Used in calculating weather and skylight. Also affects GetActualHeight(), hasNoSky = true worlds are seen as 128 height worlds, which affects nether portal placement/detection.
	public boolean isSurfaceWorld; // Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions. Affects: Clock, Compass, sky/cloud rendering, allowed to sleep here, zombie pigmen spawning in portal frames. 
	//public boolean canCoordinateBeSpawn; // Will check if the x, z position specified is alright to be set as the map spawn point
	public boolean canRespawnHere; // True if the player can respawn in this dimension (true = overworld, false = nether).
	
	public boolean doesWaterVaporize; // True for nether, any water that is placed vaporises.
	
	public boolean doesXZShowFog; // Returns true if the given X,Z coordinate should show environmental fog. True for Nether.
	
	public boolean useCustomFogColor = false;
	public double fogColorRed;
	public double fogColorGreen;
	public double fogColorBlue;
	
	public boolean isSkyColored; // Is set to false for End (black sky?)
	
	//public int averageGroundlevel; // Affects spawn point location and village spawning. Should be equal to sea level + 1(?)
	
	//public int horizonHeight; // Returns horizon height for use in rendering the sky. Should be equal to sea level(?)
	
	public int cloudHeight;
	
    public boolean canDoLightning;
    
    public boolean canDoRainSnowIce;
    
    //public boolean canMineBlock; // If set to false players are unable to mine blocks
    
    public boolean isNightWorld; // Sky is always moon and stars but light levels are same as day, used for Cartographer
    
    public double voidFogYFactor; // A double value representing the Y value relative to the top of the map at which void fog is at its maximum. The default factor of 0.03125 relative to 256, for example, means the void fog will be at its maximum at (256*0.03125), or 8.
    
    public boolean shouldMapSpin; // Determine if the cursor on the map should 'spin' when rendered, like it does for the player in the nether.
    
    public boolean canDropChunk; // Called to determine if the chunk at the given chunk coordinates within the provider's world can be dropped. Used in WorldProviderSurface to prevent spawn chunks from being unloaded.
    
	//
    
    /**
     * Creates a WorldConfig from the WorldConfig.ini file found in the given
     * directory.
     *
     * @param settingsDir    The directory the WorldConfig is in.
     * @param settingsReader The raw settings of the WorldConfig.
     * @param world          The LocalWorld instance of the world.
     * @param customObjects  The customs objects of the world.
     */
    public WorldConfig(File settingsDir, SettingsMap settingsReader, LocalWorld world, CustomObjectCollection customObjects)
    {
        super(settingsReader.getName());

        this.settingsDir = settingsDir;
        this.worldObjects = customObjects;

        // Fix older names
        this.renameOldSettings(settingsReader);
        // Set the local fields based on what was read from the file
        this.readConfigSettings(settingsReader);
        // Clamp Settings to acceptable values
        this.correctSettings();
                
        ArrayList<Integer> usedIds = new ArrayList<Integer>();
        
        if(world != null) // If world is null then this method was called from WorldCreationMenu and only needs pre-generator and worldborder settings. TODO: Make this prettier?
        {        
	        // Check biome ids, These are the names from the worldConfig file
	        // Corrects any instances of incorrect biome id.
	        for (Iterator<Entry<String, Integer>> it = customBiomeGenerationIds.entrySet().iterator(); it.hasNext();)
	        {
	            Entry<String, Integer> entry = it.next();
	
	            // Check name
	            
	            String biomeName = entry.getKey();
	            if (DefaultBiome.Contain(biomeName))
	            {
	                TerrainControl.log(LogMarker.WARN, "CustomBiomes only accepts custom biomes,"
	                        + " {} is a vanilla biome. Removing it from the list.", biomeName);
	                it.remove();
	                continue;
	            }
	
	            // Check id
	            
	            int biomeId = entry.getValue();
	            if(!((biomeId > 39 && biomeId < 127) || (biomeId > 167)))
	            {
	                TerrainControl.log(LogMarker.WARN, "CustomBiomes cannot use vanilla biome id's (0-39 and 127-167) . " 
                		+ "Removing biome " + biomeName + " from the list.");
	                it.remove();
	                continue;
	            }
	            
	            if(usedIds.contains(biomeId))
	            {
	                TerrainControl.log(LogMarker.WARN, "CustomBiomes contains two biomes with id " 
                		+ biomeId + ". Removing biome " + biomeName + " from the list.");
	                it.remove();
	                continue;
	            }
	            usedIds.add(biomeId);
	            
	            if (biomeId == -1)
	            {
	                entry.setValue(world.getFreeBiomeId());
	            }
	        }
        }
    }

    @Override
    protected void renameOldSettings(SettingsMap reader)
    {
        reader.renameOldSetting("WaterLevel", WorldStandardValues.WATER_LEVEL_MAX);
        reader.renameOldSetting("ModeTerrain", WorldStandardValues.TERRAIN_MODE);
        reader.renameOldSetting("ModeBiome", WorldStandardValues.BIOME_MODE);
        reader.renameOldSetting("NetherFortressEnabled", WorldStandardValues.NETHER_FORTRESSES_ENABLED);
        reader.renameOldSetting("PyramidsEnabled", WorldStandardValues.RARE_BUILDINGS_ENABLED);
        // WorldHeightBits was split into two different settings
        reader.renameOldSetting("WorldHeightBits", WorldStandardValues.WORLD_HEIGHT_SCALE_BITS);
        reader.renameOldSetting("WorldHeightBits", WorldStandardValues.WORLD_HEIGHT_CAP_BITS);

        // Put BiomeMode in compatibility mode when the old setting
        // NormalBiomes is found, and create default groups
        if (reader.hasSetting(WorldStandardValues.NORMAL_BIOMES))
        {
            if (reader.getSetting(WorldStandardValues.BIOME_MODE).equals("Normal"))
            {
                reader.putSetting(WorldStandardValues.BIOME_MODE, "BeforeGroups");
            }

            int landSize = reader.getSetting(WorldStandardValues.LAND_SIZE);
            int landRarity = reader.getSetting(WorldStandardValues.LAND_RARITY);
            List<String> normalBiomes = reader.getSetting(WorldStandardValues.NORMAL_BIOMES);
            BiomeGroup normalGroup = new BiomeGroup(this, WorldStandardValues.BiomeGroupNames.NORMAL,
                    landSize, landRarity, normalBiomes);

            int iceSize = reader.getSetting(WorldStandardValues.ICE_SIZE);
            int iceRarity = reader.getSetting(WorldStandardValues.ICE_RARITY);
            List<String> iceBiomes = reader.getSetting(WorldStandardValues.ICE_BIOMES);
            BiomeGroup iceGroup = new BiomeGroup(this, WorldStandardValues.BiomeGroupNames.ICE,
                    iceSize, iceRarity, iceBiomes);

            reader.addConfigFunctions(Arrays.asList(normalGroup, iceGroup));
        }

        // Migrate bounds
        if (reader.hasSetting(WorldStandardValues.CANYON_DEPTH))
        {
            reader.renameOldSetting("CanyonDepth", WorldStandardValues.RAVINE_DEPTH);
            reader.renameOldSetting("CanyonRarity", WorldStandardValues.RAVINE_RARITY);
            reader.renameOldSetting("CanyonMinAltitude", WorldStandardValues.RAVINE_MIN_ALTITUDE);
            reader.renameOldSetting("CanyonMaxAltitude", WorldStandardValues.RAVINE_MAX_ALTITUDE);
            reader.renameOldSetting("CanyonMinLength", WorldStandardValues.RAVINE_MIN_LENGTH);
            reader.renameOldSetting("CanyonMaxLength", WorldStandardValues.RAVINE_MAX_LENGTH);

            decrementByOne(reader, WorldStandardValues.CAVE_MAX_ALTITUDE);
            decrementByOne(reader, WorldStandardValues.CAVE_SYSTEM_POCKET_MAX_SIZE);
            decrementByOne(reader, WorldStandardValues.RAVINE_MAX_ALTITUDE);
            decrementByOne(reader, WorldStandardValues.RAVINE_MAX_LENGTH);
        }
    }

    private void decrementByOne(SettingsMap reader, Setting<Integer> setting)
    {
        if (reader.hasSetting(setting))
        {
            reader.putSetting(setting, reader.getSetting(setting) - 1);
        }
    }

    @Override
    protected void correctSettings()
    {
        LandSize = lowerThanOrEqualTo(LandSize, GenerationDepth);
        LandFuzzy = lowerThanOrEqualTo(LandFuzzy, GenerationDepth - LandSize);

        riverRarity = lowerThanOrEqualTo(riverRarity, GenerationDepth);
        riverSize = lowerThanOrEqualTo(riverSize, GenerationDepth - riverRarity);

        biomeGroupManager.filterBiomes(customBiomeGenerationIds.keySet());
        IsleBiomes = filterBiomes(IsleBiomes, customBiomeGenerationIds.keySet());
        BorderBiomes = filterBiomes(BorderBiomes, customBiomeGenerationIds.keySet());

        if (biomeMode == TerrainControl.getBiomeModeManager().FROM_IMAGE)
        {
            File mapFile = new File(settingsDir, imageFile);
            if (!mapFile.exists())
            {
                TerrainControl.log(LogMarker.WARN, "Biome map file not found. Switching BiomeMode to Normal");
                biomeMode = TerrainControl.getBiomeModeManager().NORMAL;
            }
        }

        imageFillBiome = (DefaultBiome.Contain(imageFillBiome) || customBiomeGenerationIds.keySet().contains(imageFillBiome)) ? imageFillBiome : WorldStandardValues.IMAGE_FILL_BIOME.getDefaultValue();

        maxMoisture = higherThan(maxMoisture, minMoisture);
        maxTemperature = higherThan(maxTemperature, minTemperature);

        caveMaxAltitude = higherThanOrEqualTo(caveMaxAltitude, caveMinAltitude);
        caveSystemPocketMaxSize = higherThanOrEqualTo(caveSystemPocketMaxSize, caveSystemPocketMinSize);
        ravineMaxAltitude = higherThanOrEqualTo(ravineMaxAltitude, ravineMinAltitude);
        ravineMaxLength = higherThanOrEqualTo(ravineMaxLength, ravineMinLength);

        waterLevelMax = higherThanOrEqualTo(waterLevelMax, waterLevelMin);

        maximumDistanceBetweenRareBuildings = higherThanOrEqualTo(maximumDistanceBetweenRareBuildings, minimumDistanceBetweenRareBuildings);
        oceanMonumentRandomOffset = lowerThanOrEqualTo(oceanMonumentRandomOffset, oceanMonumentGridSize);

        if (biomeMode == TerrainControl.getBiomeModeManager().OLD_GENERATOR && ModeTerrain != TerrainMode.OldGenerator)
        {
            TerrainControl.log(LogMarker.WARN, "Old biome generator works only with old terrain generator!");
            biomeMode = TerrainControl.getBiomeModeManager().NORMAL;

        }
    }

    @Override
    protected void readConfigSettings(SettingsMap reader)
    {
        // Main modes
        this.SettingsMode = reader.getSetting(WorldStandardValues.SETTINGS_MODE);
        this.ModeTerrain = reader.getSetting(WorldStandardValues.TERRAIN_MODE);
        this.biomeMode = TerrainControl.getBiomeModeManager().getBiomeManager(reader.getSetting(WorldStandardValues.BIOME_MODE));

        // World and water height
        this.worldHeightCapBits = reader.getSetting(WorldStandardValues.WORLD_HEIGHT_CAP_BITS);
        this.worldHeightCap = 1 << this.worldHeightCapBits;
        this.worldHeightScaleBits = reader.getSetting(WorldStandardValues.WORLD_HEIGHT_SCALE_BITS);
        this.worldHeightScaleBits = lowerThanOrEqualTo(this.worldHeightScaleBits, this.worldHeightCapBits);
        this.worldHeightScale = 1 << this.worldHeightScaleBits;
        this.waterLevelMax = worldHeightCap / 2 - 1;

        // Biome placement
        this.GenerationDepth = reader.getSetting(WorldStandardValues.GENERATION_DEPTH);

        this.BiomeRarityScale = reader.getSetting(WorldStandardValues.BIOME_RARITY_SCALE);
        this.LandRarity = reader.getSetting(WorldStandardValues.LAND_RARITY);
        this.LandSize = reader.getSetting(WorldStandardValues.LAND_SIZE);
        this.LandFuzzy = reader.getSetting(WorldStandardValues.LAND_FUZZY);

        // Ice Area Settings
        this.FrozenOcean = reader.getSetting(WorldStandardValues.FROZEN_OCEAN);
        this.FrozenOceanTemperature = reader.getSetting(WorldStandardValues.FROZEN_OCEAN_TEMPERATURE);
        this.FreezeAllColdGroupBiomes = reader.getSetting(WorldStandardValues.GROUP_FREEZE_ENABLED);

        // Freeze & Snow Settings
        this.useTemperatureForSnowHeight = reader.getSetting(WorldStandardValues.USE_TEMPERATURE_FOR_SNOW_HEIGHT);
        this.betterSnowFall = reader.getSetting(WorldStandardValues.BETTER_SNOW_FALL);
        this.fullyFreezeLakes = reader.getSetting(WorldStandardValues.FULLY_FREEZE_LAKES);

        // Rivers
        this.riverRarity = reader.getSetting(WorldStandardValues.RIVER_RARITY);
        this.riverSize = reader.getSetting(WorldStandardValues.RIVER_SIZE);
        this.riversEnabled = reader.getSetting(WorldStandardValues.RIVERS_ENABLED);
        this.improvedRivers = reader.getSetting(WorldStandardValues.IMPROVED_RIVERS);
        this.randomRivers = reader.getSetting(WorldStandardValues.RANDOM_RIVERS);
        
        // Biome Groups
        readBiomeGroups(reader);

        // Specialized Biomes
        this.IsleBiomes = reader.getSetting(WorldStandardValues.ISLE_BIOMES);
        this.BorderBiomes = reader.getSetting(WorldStandardValues.BORDER_BIOMES);
        readCustomBiomes(reader);

        // Images
        this.imageMode = reader.getSetting(WorldStandardValues.IMAGE_MODE);
        this.imageFile = reader.getSetting(WorldStandardValues.IMAGE_FILE);
        this.imageOrientation = reader.getSetting(WorldStandardValues.IMAGE_ORIENTATION);
        this.imageFillBiome = reader.getSetting(WorldStandardValues.IMAGE_FILL_BIOME);
        this.imageXOffset = reader.getSetting(WorldStandardValues.IMAGE_X_OFFSET);
        this.imageZOffset = reader.getSetting(WorldStandardValues.IMAGE_Z_OFFSET);

        // Old biomes
        this.oldBiomeSize = reader.getSetting(WorldStandardValues.OLD_BIOME_SIZE);
        this.minMoisture = reader.getSetting(WorldStandardValues.MIN_MOISTURE);
        this.maxMoisture = reader.getSetting(WorldStandardValues.MAX_MOISTURE);
        this.minTemperature = reader.getSetting(WorldStandardValues.MIN_TEMPERATURE);
        this.maxTemperature = reader.getSetting(WorldStandardValues.MAX_TEMPERATURE);

        // Fog
        this.WorldFog = reader.getSetting(WorldStandardValues.WORLD_FOG);
        this.WorldNightFog = reader.getSetting(WorldStandardValues.WORLD_NIGHT_FOG);

        this.WorldFogR = ((WorldFog & 0xFF0000) >> 16) / 255F;
        this.WorldFogG = ((WorldFog & 0xFF00) >> 8) / 255F;
        this.WorldFogB = (WorldFog & 0xFF) / 255F;

        this.WorldNightFogR = ((WorldNightFog & 0xFF0000) >> 16) / 255F;
        this.WorldNightFogG = ((WorldNightFog & 0xFF00) >> 8) / 255F;
        this.WorldNightFogB = (WorldNightFog & 0xFF) / 255F;

        // Structures
        this.strongholdsEnabled = reader.getSetting(WorldStandardValues.STRONGHOLDS_ENABLED);
        this.strongholdCount = reader.getSetting(WorldStandardValues.STRONGHOLD_COUNT);
        this.strongholdDistance = reader.getSetting(WorldStandardValues.STRONGHOLD_DISTANCE);
        this.strongholdSpread = reader.getSetting(WorldStandardValues.STRONGHOLD_SPREAD);

        this.villagesEnabled = reader.getSetting(WorldStandardValues.VILLAGES_ENABLED);
        this.villageDistance = reader.getSetting(WorldStandardValues.VILLAGE_DISTANCE);
        this.villageSize = reader.getSetting(WorldStandardValues.VILLAGE_SIZE);

        this.rareBuildingsEnabled = reader.getSetting(WorldStandardValues.RARE_BUILDINGS_ENABLED);
        this.minimumDistanceBetweenRareBuildings = reader.getSetting(WorldStandardValues.MINIMUM_DISTANCE_BETWEEN_RARE_BUILDINGS);
        this.maximumDistanceBetweenRareBuildings = reader.getSetting(WorldStandardValues.MAXIMUM_DISTANCE_BETWEEN_RARE_BUILDINGS);

        this.oceanMonumentsEnabled = reader.getSetting(WorldStandardValues.OCEAN_MONUMENTS_ENABLED);
        this.oceanMonumentRandomOffset = reader.getSetting(WorldStandardValues.OCEAN_MONUMENT_RANDOM_OFFSET);
        this.oceanMonumentGridSize = reader.getSetting(WorldStandardValues.OCEAN_MONUMENT_GRID_SIZE);

        this.maximumCustomStructureRadius = reader.getSetting(WorldStandardValues.MAXIMUM_CUSTOM_STRUCTURE_RADIUS);
        this.mineshaftsEnabled = reader.getSetting(WorldStandardValues.MINESHAFTS_ENABLED);
        this.netherFortressesEnabled = reader.getSetting(WorldStandardValues.NETHER_FORTRESSES_ENABLED);

        // Caves
        this.caveRarity = reader.getSetting(WorldStandardValues.CAVE_RARITY);
        this.caveFrequency = reader.getSetting(WorldStandardValues.CAVE_FREQUENCY);
        this.caveMinAltitude = reader.getSetting(WorldStandardValues.CAVE_MIN_ALTITUDE);
        this.caveMaxAltitude = reader.getSetting(WorldStandardValues.CAVE_MAX_ALTITUDE);
        this.individualCaveRarity = reader.getSetting(WorldStandardValues.INDIVIDUAL_CAVE_RARITY);
        this.caveSystemFrequency = reader.getSetting(WorldStandardValues.CAVE_SYSTEM_FREQUENCY);
        this.caveSystemPocketChance = reader.getSetting(WorldStandardValues.CAVE_SYSTEM_POCKET_CHANCE);
        this.caveSystemPocketMinSize = reader.getSetting(WorldStandardValues.CAVE_SYSTEM_POCKET_MIN_SIZE);
        this.caveSystemPocketMaxSize = reader.getSetting(WorldStandardValues.CAVE_SYSTEM_POCKET_MAX_SIZE);
        this.evenCaveDistribution = reader.getSetting(WorldStandardValues.EVEN_CAVE_DISTRIBUTION);

        // Ravines
        this.ravineRarity = reader.getSetting(WorldStandardValues.RAVINE_RARITY);
        this.ravineMinAltitude = reader.getSetting(WorldStandardValues.RAVINE_MIN_ALTITUDE);
        this.ravineMaxAltitude = reader.getSetting(WorldStandardValues.RAVINE_MAX_ALTITUDE);
        this.ravineMinLength = reader.getSetting(WorldStandardValues.RAVINE_MIN_LENGTH);
        this.ravineMaxLength = reader.getSetting(WorldStandardValues.RAVINE_MAX_LENGTH);
        this.ravineDepth = reader.getSetting(WorldStandardValues.RAVINE_DEPTH);

        // Water
        this.waterLevelMax = reader.getSetting(WorldStandardValues.WATER_LEVEL_MAX);
        this.waterLevelMin = reader.getSetting(WorldStandardValues.WATER_LEVEL_MIN);
        this.waterBlock = reader.getSetting(WorldStandardValues.WATER_BLOCK);
        this.iceBlock = reader.getSetting(WorldStandardValues.ICE_BLOCK);

        // Lava
        this.cooledLavaBlock = reader.getSetting(WorldStandardValues.COOLED_LAVA_BLOCK);

        // Fracture
        this.fractureHorizontal = reader.getSetting(WorldStandardValues.FRACTURE_HORIZONTAL);
        this.fractureVertical = reader.getSetting(WorldStandardValues.FRACTURE_VERTICAL);

        // Bedrock
        this.disableBedrock = reader.getSetting(WorldStandardValues.DISABLE_BEDROCK);
        this.ceilingBedrock = reader.getSetting(WorldStandardValues.CEILING_BEDROCK);
        this.flatBedrock = reader.getSetting(WorldStandardValues.FLAT_BEDROCK);
        this.bedrockBlock = reader.getSetting(WorldStandardValues.BEDROCK_BLOCK);

        // Misc
        this.removeSurfaceStone = reader.getSetting(WorldStandardValues.REMOVE_SURFACE_STONE);
        this.objectSpawnRatio = reader.getSetting(WorldStandardValues.OBJECT_SPAWN_RATIO);
        this.resourcesSeed = reader.getSetting(WorldStandardValues.RESOURCES_SEED);
        this.populationBoundsCheck = reader.getSetting(WorldStandardValues.POPULATION_BOUNDS_CHECK);
        this.populateUsingSavedBiomes = reader.getSetting(WorldStandardValues.POPULATE_USING_SAVED_BIOMES);

        this.oldTerrainGenerator = this.ModeTerrain == TerrainMode.OldGenerator;

        this.author = reader.getSetting(WorldStandardValues.AUTHOR);
        this.description = reader.getSetting(WorldStandardValues.DESCRIPTION);

        this.PreGenerationRadius = reader.getSetting(WorldStandardValues.PREGENERATION_RADIUS);
        this.WorldBorderRadius = reader.getSetting(WorldStandardValues.WORLD_BORDER_RADIUS);

        this.worldSeed = reader.getSetting(WorldStandardValues.WORLD_SEED);

        this.BO3AtSpawn = reader.getSetting(WorldStandardValues.BO3_AT_SPAWN);

        this.Cartographer = reader.getSetting(WorldStandardValues.CARTOGRAPHER);
        
        this.DimensionPortalMaterials = reader.getSetting(WorldStandardValues.DIMENSION_PORTAL_MATERIALS);
        
        // Dimensions 
        this.Dimensions = reader.getSetting(WorldStandardValues.DIMENSIONS);
        
        this.dimensionBelow = reader.getSetting(WorldStandardValues.DIMENSIONBELOW);
        this.dimensionAbove = reader.getSetting(WorldStandardValues.DIMENSIONABOVE);
        
        this.teleportToSpawnOnly = reader.getSetting(WorldStandardValues.teleportToSpawnOnly);
        
        this.commandBlockOutput = reader.getSetting(WorldStandardValues.commandBlockOutput).toString(); // "true"; // Whether command blocks should notify admins when they perform commands
        this.disableElytraMovementCheck = reader.getSetting(WorldStandardValues.disableElytraMovementCheck).toString(); // "false"; // Whether the server should skip checking player speed when the player is wearing elytra. Often helps with jittering due to lag in multiplayer, but may also be used to travel unfairly long distances in survival mode (cheating).
        this.doDaylightCycle = reader.getSetting(WorldStandardValues.doDaylightCycle).toString(); // "true"; // Whether the day-night cycle and moon phases progress
        this.doEntityDrops = reader.getSetting(WorldStandardValues.doEntityDrops).toString(); // "true"; // Whether entities that are not mobs should have drops
        this.doFireTick = reader.getSetting(WorldStandardValues.doFireTick).toString(); // "true"; // Whether fire should spread and naturally extinguish
        //this.doLimitedCrafting = reader.getSetting(WorldStandardValues.doLimitedCrafting).toString(); // "false"; // Whether players should only be able to craft recipes that they've unlocked first // TODO: Implement for 1.12
        this.doMobLoot = reader.getSetting(WorldStandardValues.doMobLoot).toString(); // "true"; // Whether mobs should drop items
        this.doMobSpawning = reader.getSetting(WorldStandardValues.doMobSpawning).toString(); // "true"; // Whether mobs should naturally spawn. Does not affect monster spawners.
        this.doTileDrops = reader.getSetting(WorldStandardValues.doTileDrops).toString(); // "true"; // Whether blocks should have drops
        this.doWeatherCycle = reader.getSetting(WorldStandardValues.doWeatherCycle).toString(); // "true"; // Whether the weather will change
        //public String gameLoopFunction = "true"; // The function to run every game tick // TODO: Implement for 1.12
        this.keepInventory = reader.getSetting(WorldStandardValues.keepInventory).toString(); // "false"; // Whether the player should keep items in their inventory after death
        this.logAdminCommands = reader.getSetting(WorldStandardValues.logAdminCommands).toString(); // "true"; // Whether to log admin commands to server log
        //public String maxCommandChainLength = "65536"; // Determines the number at which the chain command block acts as a "chain". // TODO: Implement for 1.12
        this.maxEntityCramming = reader.getSetting(WorldStandardValues.maxEntityCramming).toString(); // "24"; // The maximum number of other pushable entities a mob or player can push, before taking 3 doublehearts suffocation damage per half-second. Setting to 0 disables the rule. Damage affects survival-mode or adventure-mode players, and all mobs but bats. Pushable entities include non-spectator-mode players, any mob except bats, as well as boats and minecarts.
        this.mobGriefing = reader.getSetting(WorldStandardValues.mobGriefing).toString(); // "true"; // Whether creepers, zombies, endermen, ghasts, withers, ender dragons, rabbits, sheep, and villagers should be able to change blocks and whether villagers, zombies, skeletons, and zombie pigmen can pick up items
        this.naturalRegeneration = reader.getSetting(WorldStandardValues.naturalRegeneration).toString(); // "true"; // Whether the player can regenerate health naturally if their hunger is full enough (doesn't affect external healing, such as golden apples, the Regeneration effect, etc.)
        this.randomTickSpeed = reader.getSetting(WorldStandardValues.randomTickSpeed).toString(); // "3"; // How often a random block tick occurs (such as plant growth, leaf decay, etc.) per chunk section per game tick. 0 will disable random ticks, higher numbers will increase random ticks 
        this.reducedDebugInfo = reader.getSetting(WorldStandardValues.reducedDebugInfo).toString(); // "false"; // Whether the debug screen shows all or reduced information; and whether the effects of F3+B (entity hitboxes) and F3+G (chunk boundaries) are shown.
        this.sendCommandFeedback = reader.getSetting(WorldStandardValues.sendCommandFeedback).toString(); // "true"; // Whether the feedback from commands executed by a player should show up in chat. Also affects the default behavior of whether command blocks store their output text
        this.showDeathMessages = reader.getSetting(WorldStandardValues.showDeathMessages).toString(); // "true"; // Whether death messages are put into chat when a player dies. Also affects whether a message is sent to the pet's owner when the pet dies.
        this.spawnRadius = reader.getSetting(WorldStandardValues.spawnRadius).toString(); // "10"; // The number of blocks outward from the world spawn coordinates that a player will spawn in when first joining a server or when dying without a spawnpoint.
        this.spectatorsGenerateChunks = reader.getSetting(WorldStandardValues.spectatorsGenerateChunks).toString(); // "true"; // Whether players in spectator mode can generate chunks	
    	
        // World provider settings for worlds used as dimensions with Forge : TODO: Apply to overworld too?
        
        this.welcomeMessage = reader.getSetting(WorldStandardValues.welcomeMessage); // ""; // A message to display to the user when they transfer to this dimension.
        this.departMessage = reader.getSetting(WorldStandardValues.departMessage); // A Message to display to the user when they transfer out of this dimension.
        //public boolean isHellWorld = false; // DoesWaterVaporize sets this
        this.hasSkyLight = reader.getSetting(WorldStandardValues.hasSkyLight); // false; // A boolean that tells if a world does not have a sky. Used in calculating weather and skylight. Also affects GetActualHeight(), hasNoSky = true worlds are seen as 128 height worlds, which affects nether portal placement/detection.
        this.isSurfaceWorld = reader.getSetting(WorldStandardValues.isSurfaceWorld); // true; // Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions. Affects: Clock, Compass, sky/cloud rendering, allowed to sleep here, zombie pigmen spawning in portal frames. 
        //this.canCoordinateBeSpawn = reader.getSetting(WorldStandardValues.canCoordinateBeSpawn); // false; // Will check if the x, z position specified is alright to be set as the map spawn point
        this.canRespawnHere = reader.getSetting(WorldStandardValues.canRespawnHere); // true; // True if the player can respawn in this dimension (true = overworld, false = nether).
    	
        this.doesWaterVaporize = reader.getSetting(WorldStandardValues.doesWaterVaporize); // false; // True for nether, any water that is placed vaporises.
    	
        this.doesXZShowFog = reader.getSetting(WorldStandardValues.doesXZShowFog); // false; // Returns true if the given X,Z coordinate should show environmental fog. True for Nether.
    	
        this.useCustomFogColor = reader.getSetting(WorldStandardValues.useCustomFogColor); // false
        this.fogColorRed = reader.getSetting(WorldStandardValues.fogColorRed); // 0.20000000298023224D;
        this.fogColorGreen = reader.getSetting(WorldStandardValues.fogColorGreen); // 0.029999999329447746D;
        this.fogColorBlue = reader.getSetting(WorldStandardValues.fogColorBlue); // 0.029999999329447746D;
    	
        this.isSkyColored = reader.getSetting(WorldStandardValues.isSkyColored); // true; // Is set to false for End (black sky?)
    	
        //this.averageGroundlevel = reader.getSetting(WorldStandardValues.averageGroundlevel); // 0; // Affects spawn point location and village spawning. Should be equal to sea level + 1(?)
    	
        //this.horizonHeight = reader.getSetting(WorldStandardValues.horizonHeight); // 0; // Returns horizon height for use in rendering the sky. Should be equal to sea level(?)
    	
        this.cloudHeight = reader.getSetting(WorldStandardValues.cloudHeight); // 0;
    	
        this.canDoLightning = reader.getSetting(WorldStandardValues.canDoLightning); // false;
        
        this.canDoRainSnowIce = reader.getSetting(WorldStandardValues.canDoRainSnowIce); // false;
        
        //this.canMineBlock = reader.getSetting(WorldStandardValues.canMineBlock); // false; // If set to false players are unable to mine blocks
        
        this.isNightWorld = reader.getSetting(WorldStandardValues.isNightWorld); // false;
        
        this.voidFogYFactor = reader.getSetting(WorldStandardValues.voidFogYFactor); // 0.03125D; // A double value representing the Y value relative to the top of the map at which void fog is at its maximum. The default factor of 0.03125 relative to 256, for example, means the void fog will be at its maximum at (256*0.03125), or 8.
        
        this.shouldMapSpin = reader.getSetting(WorldStandardValues.shouldMapSpin); // false; // Determine if the cursor on the map should 'spin' when rendered, like it does for the player in the nether.
        
        this.canDropChunk = reader.getSetting(WorldStandardValues.canDropChunk); // true; // // Determine if the chunk at the given chunk coordinates within the provider's world can be dropped. Used in WorldProviderSurface to prevent spawn chunks from being unloaded.
    }

    private void readBiomeGroups(SettingsMap reader)
    {
        this.biomeGroupManager = new BiomeGroupManager();
        if (reader.isNewConfig())
        {
            createDefaultBiomeGroups();
        }
        for (ConfigFunction<WorldConfig> res : reader.getConfigFunctions(this, false))
        {
            if (res != null)
            {
                if (res instanceof BiomeGroup)
                {
                    biomeGroupManager.registerGroup((BiomeGroup) res);
                }
            }
        }
    }

    private void createDefaultBiomeGroups()
    {
        BiomeGroup normalGroup = new BiomeGroup(this, WorldStandardValues.BiomeGroupNames.NORMAL, 0, 98,
                Arrays.asList("Forest", "Roofed Forest", "Extreme Hills", "Plains",
                        "Birch Forest", "Swampland", "Flower Forest", "Roofed Forest M",
                        "Extreme Hills+", "Sunflower Plains", "Birch Forest M", "Swampland M"));
        this.biomeGroupManager.registerGroup(normalGroup);

        BiomeGroup iceGroup = new BiomeGroup(this, WorldStandardValues.BiomeGroupNames.ICE, 2, 40,
                Arrays.asList("Ice Plains", "Cold Taiga", "Ice Plains Spikes", "Cold Taiga M"));
        this.biomeGroupManager.registerGroup(iceGroup);

        BiomeGroup hotGroup = new BiomeGroup(this, WorldStandardValues.BiomeGroupNames.HOT, 1, 98,
                Arrays.asList("Desert", "Savanna", "Plains", "Desert M", "Savanna M", "Sunflower Plains"));
        this.biomeGroupManager.registerGroup(hotGroup);

        BiomeGroup coldGroup = new BiomeGroup(this, WorldStandardValues.BiomeGroupNames.COLD, 0, 98,
                Arrays.asList("Forest", "Extreme Hills", "Taiga", "Plains",
                        "Flower Forest", "Extreme Hills+", "Taiga M", "Sunflower Plains"));
        this.biomeGroupManager.registerGroup(coldGroup);

        BiomeGroup mesaGroup = new BiomeGroup(this, WorldStandardValues.BiomeGroupNames.MESA, 2, 40,
                Arrays.asList("Mesa"));
        this.biomeGroupManager.registerGroup(mesaGroup);

        BiomeGroup jungleGroup = new BiomeGroup(this, WorldStandardValues.BiomeGroupNames.JUNGLE, 1, 40,
                Arrays.asList("Jungle", "Jungle M"));
        this.biomeGroupManager.registerGroup(jungleGroup);

        BiomeGroup megaTaigaGroup = new BiomeGroup(this, WorldStandardValues.BiomeGroupNames.MEGA_TAIGA, 1, 40,
                Arrays.asList("Mega Taiga", "Mega Spruce Taiga"));
        this.biomeGroupManager.registerGroup(megaTaigaGroup);
    }

    private void readCustomBiomes(SettingsMap reader)
    {

        List<String> biomes = reader.getSetting(WorldStandardValues.CUSTOM_BIOMES);

        for (String biome : biomes)
        {
            try
            {
                String[] keys = biome.split(":");
                if (keys[0].isEmpty())
                {
                    // Don't allow biomes with empty names
                    continue;
                }
                if (keys.length == 2)
                {
                    int generationBiomeId = Integer.parseInt(keys[1]);
                    customBiomeGenerationIds.put(keys[0], generationBiomeId);
                } else
                {
                    customBiomeGenerationIds.put(keys[0], -1);
                }

            } catch (NumberFormatException e)
            {
                System.out.println("Wrong custom biome id settings: '" + biome + "'");
            }
        }
    }

    @Override
    protected void writeConfigSettings(SettingsMap writer)
    {
        // About the world
        writer.bigTitle("WorldConfig");
        writer.putSetting(WorldStandardValues.AUTHOR, this.author,
                "The author of this world");

        writer.putSetting(WorldStandardValues.DESCRIPTION, this.description,
                "A short description of this world");

        writer.putSetting(WorldStandardValues.SETTINGS_MODE, this.SettingsMode,
                "What " + PluginStandardValues.PLUGIN_NAME + " does with the config files.",
                "Possible modes:",
                "   WriteAll - default",
                "   WriteWithoutComments - write config files without help comments",
                "   WriteDisable - doesn't write to the config files, it only reads. Doesn't auto-update the configs. Use with care!");

        // The modes
        writer.bigTitle("The modes");

        writer.putSetting(WorldStandardValues.TERRAIN_MODE, this.ModeTerrain,
                "Possible terrain modes:",
                "   Normal - use all features",
                "   TerrainTest - generate only terrain without any resources",
                "   NotGenerate - generate empty chunks",
                "   Default - use default terrain generator",
                "   OldGenerator - Minecraft Beta 1.7.3-like land generator");

        writer.putSetting(WorldStandardValues.BIOME_MODE, TerrainControl.getBiomeModeManager().getName(biomeMode),
                "Possible biome modes:",
                "   Normal - use all features",
                "   FromImage - get biomes from image file",
                "   Default - use default Notch biome generator",
                "For old maps two more modes are available:",
                "   BeforeGroups - Minecraft 1.0 - 1.6.4 biome generator, only supports the biome groups NormalBiomes and IceBiomes",
                "   OldGenerator - Minecraft Beta 1.7.3 biome generator");
        
        // Custom biomes
        writer.bigTitle("Custom biomes");

        WriteCustomBiomes(writer);

        // Settings for BiomeMode:Normal
        writer.bigTitle("Settings for BiomeMode: Normal",
                "Also used in BiomeMode:FromImage when ImageMode is set to ContinueNormal");

        writer.putSetting(WorldStandardValues.GENERATION_DEPTH, this.GenerationDepth,
                "Important value for generation. Bigger values appear to zoom out. All 'Sizes' must be smaller than this.",
                "Large %/total area biomes (Continents) must be set small, (limit=0)",
                "Small %/total area biomes (Oasis,Mountain Peaks) must be larger (limit=GenerationDepth)",
                "This could also represent \"Total number of biome sizes\" ",
                "Small values (about 1-2) and Large values (about 20) may affect generator performance.");

        writer.putSetting(WorldStandardValues.BIOME_RARITY_SCALE, this.BiomeRarityScale,
                "Max biome rarity from 1 to infinity. By default this is 100, but you can raise it for",
                "fine-grained control, or to create biomes with a chance of occurring smaller than 1/100.");

        // Biome groups
        writer.smallTitle("Biome Groups",
                "Minecraft groups similar biomes together, so that they spawn next to each other.",
                "",
                "Syntax: BiomeGroup(Name, Size, Rarity, BiomeName[, AnotherName[, ...]])",
                "Name - just for clarity, choose something descriptive",
                "Size - layer to generate on, from 0 to GenerationDepth. All biomes in the group must have a BiomeSize",
                "       larger than or equal to this value.",
                "Rarity - relative spawn chances.",
                "BiomeName... - names of the biome that spawn in the group. Case sensitive.",
                "",
                "Note: if you're using BiomeMode: BeforeGroups, only the biome names of the groups named NormalBiomes",
                "and IceBiomes and the size and rarity of the group named IceBiomes will be used. Other groups are",
                "ignored. The size and rarity of the NormalBiomes group is ignored as well, use LandSize and",
                "LandRarity instead.",
                "");

        writer.addConfigFunctions(this.biomeGroupManager.getGroups());

        // Biome lists
        writer.smallTitle("Biome lists",
                "Don't forget to register your custom biomes first in CustomBiomes!",
                "");

        writer.putSetting(WorldStandardValues.ISLE_BIOMES, this.IsleBiomes,
                "Biomes used as isles in other biomes. You must set IsleInBiome in biome config for each biome here. Biome name is case sensitive.",

                "Biomes used as borders of other biomes. You must set BiomeIsBorder in biome config for each biome here. Biome name is case sensitive.");

        writer.putSetting(WorldStandardValues.BORDER_BIOMES, this.BorderBiomes);

        writer.smallTitle("Landmass settings (for NormalBiomes)");

        writer.putSetting(WorldStandardValues.LAND_RARITY, this.LandRarity,
                "Land rarity from 100 to 1. If you set smaller than 90 and LandSize near 0 beware Big oceans.");

        writer.putSetting(WorldStandardValues.LAND_SIZE, this.LandSize,
                "Land size from 0 to GenerationDepth. Biome groups are placed on this.");

        writer.putSetting(WorldStandardValues.LAND_FUZZY, this.LandFuzzy,
                "Make land more fuzzy and make lakes. Must be from 0 to GenerationDepth - LandSize");

        writer.smallTitle("Ice area settings");

        writer.putSetting(WorldStandardValues.FROZEN_OCEAN, this.FrozenOcean,
                "Set this to false to stop the ocean from freezing near when an \"ice area\" intersects with an ocean.");

        writer.putSetting(WorldStandardValues.FROZEN_OCEAN_TEMPERATURE, this.FrozenOceanTemperature,
                "This is the biome temperature when water freezes if \"FrozenOcean\" is set to true.",
                "This used to be the case for all biomes in the \"IceBiomes\" list. Default: 0.15; Min: 0.0; Max: 2.0",
                "Temperature Reference from Vanilla: <0.15 for snow, 0.15 - 0.95 for rain, or >1.0 for dry");

        writer.putSetting(WorldStandardValues.GROUP_FREEZE_ENABLED, this.FreezeAllColdGroupBiomes,
                "If the average of all biome temperatures in a biome group is less than \"OceanFreezingTemperature\", then:",
                " - When this setting is true, all biomes in the group will have frozen oceans",
                " - When this setting is false, only biomes with a temperature below \"OceanFreezingTemperature\" will have frozen oceans",
                "Default: false");

        writer.smallTitle("Rivers");

        writer.putSetting(WorldStandardValues.RIVER_RARITY, this.riverRarity,
                "River rarity. Must be from 0 to GenerationDepth.");

        writer.putSetting(WorldStandardValues.RIVER_SIZE, this.riverSize,
                "River size from 0 to GenerationDepth - RiverRarity");

        writer.putSetting(WorldStandardValues.RIVERS_ENABLED, this.riversEnabled,
                "Set this to false to prevent the river generator from doing anything.");

        writer.putSetting(WorldStandardValues.IMPROVED_RIVERS, this.improvedRivers,
                "When this is set to false, the standard river generator of Minecraft will be used.",
                "This means that a technical biome, determined by the RiverBiome setting of the biome",
                "the river is flowing through, will be used to generate the river.",
                "",
                "When enabled, the rivers won't use a technical biome in your world anymore, instead",
                "you can control them using the river settings in the BiomeConfigs.");

        writer.putSetting(WorldStandardValues.RANDOM_RIVERS, this.randomRivers,
                "When set to true the rivers will no longer follow biome border most of the time.");

        // Settings for BiomeMode:FromImage
        writer.bigTitle("Settings for BiomeMode:FromImage");

        writer.putSetting(WorldStandardValues.IMAGE_MODE, this.imageMode,
                "Possible modes when generator outside image boundaries: Repeat, ContinueNormal, FillEmpty",
                "   Repeat - repeat image",
                "   Mirror - advanced repeat image mode",
                "   ContinueNormal - continue normal generation",
                "   FillEmpty - fill by biome in \"ImageFillBiome settings\" ");

        writer.putSetting(WorldStandardValues.IMAGE_FILE, this.imageFile,
                "Source png file for FromImage biome mode.");

        writer.putSetting(WorldStandardValues.IMAGE_ORIENTATION, this.imageOrientation,
                "Where the png's north is oriented? Possible values: North, East, South, West",
                "   North - the top of your picture if north (no any rotation)",
                "   West - previous behavior (you should rotate png CCW manually)",
                "   East - png should be rotated CW manually",
                "   South - rotate png 180 degrees before generating world");

        writer.putSetting(WorldStandardValues.IMAGE_FILL_BIOME, this.imageFillBiome,
                "Biome name for fill outside image boundaries with FillEmpty mode.");

        writer.putSetting(WorldStandardValues.IMAGE_X_OFFSET, this.imageXOffset,
                "Shifts map position from x=0 and z=0 coordinates.");
        writer.putSetting(WorldStandardValues.IMAGE_Z_OFFSET, this.imageZOffset);

        // Terrain height and volatility
        writer.bigTitle("Terrain height and volatility");

        writer.putSetting(WorldStandardValues.WORLD_HEIGHT_SCALE_BITS, this.worldHeightScaleBits,
                "Scales the height of the world. Adding 1 to this doubles the",
                "height of the terrain, substracting 1 to this halves the height",
                "of the terrain. Values must be between 5 and 8, inclusive.");

        writer.putSetting(WorldStandardValues.WORLD_HEIGHT_CAP_BITS, this.worldHeightCapBits,
                "Height cap of the base terrain. Setting this to 7 makes no terrain",
                "generate above y = 2 ^ 7 = 128. Doesn't affect resources (trees, objects, etc.).",
                "Values must be between 5 and 8, inclusive. Values may not be lower",
                "than WorldHeightScaleBits.");

        writer.putSetting(WorldStandardValues.FRACTURE_HORIZONTAL, this.fractureHorizontal,
                "Can increase (values greater than 0) or decrease (values less than 0) how much the landscape is fractured horizontally.");

        writer.putSetting(WorldStandardValues.FRACTURE_VERTICAL, this.fractureVertical,
                "Can increase (values greater than 0) or decrease (values less than 0) how much the landscape is fractured vertically.",
                "Positive values will lead to large cliffs/overhangs, floating islands, and/or a cavern world depending on other settings.");

        // Blocks
        writer.bigTitle("Blocks");

        writer.putSetting(WorldStandardValues.REMOVE_SURFACE_STONE, this.removeSurfaceStone,
                "Attempts to replace all surface stone with biome surface block.");

        writer.putSetting(WorldStandardValues.DISABLE_BEDROCK, this.disableBedrock,
                "Disable bottom of map bedrock generation. Doesn't affect bedrock on the ceiling of the map.");

        writer.putSetting(WorldStandardValues.CEILING_BEDROCK, this.ceilingBedrock,
                "Enable ceiling of map bedrock generation.");

        writer.putSetting(WorldStandardValues.FLAT_BEDROCK, this.flatBedrock,
                "Make layer of bedrock flat.");

        writer.putSetting(WorldStandardValues.BEDROCK_BLOCK, this.bedrockBlock,
                "Block used as bedrock.");

        writer.putSetting(WorldStandardValues.POPULATION_BOUNDS_CHECK, this.populationBoundsCheck,
                "Set this to false to disable the bounds check during chunk population.",
                "While this allows you to spawn larger objects, it also makes terrain generation",
                "dependant on the direction you explored the world in.");

        if (this.populateUsingSavedBiomes)
        {

            writer.putSetting(WorldStandardValues.POPULATE_USING_SAVED_BIOMES, this.populateUsingSavedBiomes,
                    "Advanced setting, only written to this file when set to true.",
                    "If it is set to true the biome populator will use the biome ids present in the",
                    "chunk data, ignoring the biome generator. This is useful if you have a premade",
                    "map made with for example WorldPainter, but still want to populate it using "
                            + PluginStandardValues.PLUGIN_NAME + ".",
                    "Using this together with " + BiomeStandardValues.REPLACE_TO_BIOME_NAME + " is discouraged: it uses the biome",
                    "specified in " + BiomeStandardValues.REPLACE_TO_BIOME_NAME
                            + " to populate the chunk, instead of the biome itself.");
        }

        writer.smallTitle("Water / Lava & Frozen States");

        writer.putSetting(WorldStandardValues.WATER_LEVEL_MAX, this.waterLevelMax,
                "Set water level. Every empty block under this level will be fill water or another block from WaterBlock ");
        writer.putSetting(WorldStandardValues.WATER_LEVEL_MIN, this.waterLevelMin);

        writer.putSetting(WorldStandardValues.WATER_BLOCK, this.waterBlock,
                "Block used as water in WaterLevel.");

        writer.putSetting(WorldStandardValues.ICE_BLOCK, this.iceBlock,
                "Block used as ice.");

        writer.putSetting(WorldStandardValues.COOLED_LAVA_BLOCK, this.cooledLavaBlock,
                "Block used as cooled or frozen lava.",
                "Set this to OBSIDIAN for \"frozen\" lava lakes in cold biomes");

        writer.smallTitle("World only");

        writer.putSetting(WorldStandardValues.FULLY_FREEZE_LAKES, this.fullyFreezeLakes,
                "By Default in cold biomes, lakes freeze but only water exposed to sky is frozen.",
                "Setting this to true causes any lake in a cold biome with at least one block exposed to sky to completely freeze");

        writer.putSetting(WorldStandardValues.USE_TEMPERATURE_FOR_SNOW_HEIGHT, this.useTemperatureForSnowHeight,
                "By Default, all snow is 1 layer high. When this setting is set to true, snow height is",
                "determined by biome temperature and therefore height.",
                "For now: A block temp > -.5 yields a single snow layer. A block temp < -.75 yields max snow layers.",
                "All values in the range -.75 < temp < -.5 are evenly distributed.");

        writer.putSetting(WorldStandardValues.BETTER_SNOW_FALL, this.betterSnowFall,
                "By Default, snow falls on the highest block only.",
                "Setting this to true will cause snow to fall through leaves but leave a little snow on the way");

        writer.bigTitle("Resources");

        writer.putSetting(WorldStandardValues.RESOURCES_SEED, this.resourcesSeed,
                "Seed used for the resource generation. Can only be numeric. Set to 0 to use the world seed.");

        if (objectSpawnRatio != 1)
        {
            // Write the old objectSpawnRatio

            writer.putSetting(WorldStandardValues.OBJECT_SPAWN_RATIO, this.objectSpawnRatio,
                    "LEGACY setting for compability with old worlds. This setting should be kept at 1.",
                    "If the setting is set at 1, the setting will vanish from the config file. Readd it",
                    "manually with another value and it will be back.",
                    "",
                    "When using the UseWorld or UseBiome keyword for spawning custom objects, Open Terrain Generator",
                    "spawns one of the possible custom objects. There is of course a chance that",
                    "the chosen object cannot spawn. This setting tells TC how many times it should",
                    "try to spawn that object.",
                    "This setting doesn't affect growing saplings anymore.");
        }

        // Structures
        writer.bigTitle("Structures",
                "Generate-structures in the server.properties file is ignored by Open Terrain Generator. Use these settings instead.",
                "");

        // Strongholds
        writer.smallTitle("Strongholds");

        writer.putSetting(WorldStandardValues.STRONGHOLDS_ENABLED, this.strongholdsEnabled,
                "Set this to false to prevent the stronghold generator from doing anything.");

        writer.putSetting(WorldStandardValues.STRONGHOLD_COUNT, this.strongholdCount,
                "The number of strongholds in the world.");

        writer.putSetting(WorldStandardValues.STRONGHOLD_DISTANCE, this.strongholdDistance,
                "How far strongholds are from the spawn and other strongholds (minimum is 1.0, default is 32.0).");

        writer.putSetting(WorldStandardValues.STRONGHOLD_SPREAD, this.strongholdSpread,
                "How concentrated strongholds are around the spawn (minimum is 1, default is 3). Lower number, lower concentration.");

        // Villages
        writer.smallTitle("Villages");

        writer.putSetting(WorldStandardValues.VILLAGES_ENABLED, this.villagesEnabled,
                "Whether the villages are enabled or not.");

        writer.putSetting(WorldStandardValues.VILLAGE_SIZE, this.villageSize,
                "The size of the village. Larger is bigger. Normal worlds have 0 as default, superflat worlds 1.");

        writer.putSetting(WorldStandardValues.VILLAGE_DISTANCE, this.villageDistance,
                "The minimum distance between the village centers in chunks. Minimum value is 9.");

        // Rare buildings
        writer.smallTitle("Rare buildings",
                "Rare buildings are either desert pyramids, jungle temples or swamp huts.");

        writer.putSetting(WorldStandardValues.RARE_BUILDINGS_ENABLED, this.rareBuildingsEnabled,
                "Whether rare buildings are enabled.");

        writer.putSetting(WorldStandardValues.MINIMUM_DISTANCE_BETWEEN_RARE_BUILDINGS, this.minimumDistanceBetweenRareBuildings,
                "The minimum distance between rare buildings in chunks.");

        writer.putSetting(WorldStandardValues.MAXIMUM_DISTANCE_BETWEEN_RARE_BUILDINGS, this.maximumDistanceBetweenRareBuildings,
                "The maximum distance between rare buildings in chunks.");

        // Ocean monuments
        writer.smallTitle("Ocean monuments");

        writer.putSetting(WorldStandardValues.OCEAN_MONUMENTS_ENABLED, this.oceanMonumentsEnabled,
                "Whether ocean monuments are enabled.");

        writer.putSetting(WorldStandardValues.OCEAN_MONUMENT_GRID_SIZE, this.oceanMonumentGridSize,
                "Ocean monuments are placed on the corners of a grid, with a random offset added to each corner.",
                "The first variable is the size of the grid in chunks.",
                "Setting this to 8 will give a grid with cells of 8x8 chunks.");

        writer.putSetting(WorldStandardValues.OCEAN_MONUMENT_RANDOM_OFFSET, this.oceanMonumentRandomOffset,
                "Random offset from each corner in chunks, on both the x and z axis.",
                "May not be smaller than 0, and may not be larger than " + WorldStandardValues.OCEAN_MONUMENT_GRID_SIZE + ".");

        // Custom structures
        writer.smallTitle("Custom structues");

        writer.putSetting(WorldStandardValues.MAXIMUM_CUSTOM_STRUCTURE_RADIUS, this.maximumCustomStructureRadius,
                "Maximum radius of custom structures in chunks. Custom structures are spawned by",
                "the CustomStructure resource in the biome configuration files.");

        // Other structures
        writer.smallTitle("Other structures");
        writer.putSetting(WorldStandardValues.MINESHAFTS_ENABLED, this.mineshaftsEnabled);
        writer.putSetting(WorldStandardValues.NETHER_FORTRESSES_ENABLED, this.netherFortressesEnabled);

        // Visual settings
        writer.bigTitle("Visual settings",
                "Warning: this section will work only for players with the single version of Open Terrain Generator installed.");

        writer.putSetting(WorldStandardValues.WORLD_FOG, this.WorldFog,
                "World fog color");

        writer.putSetting(WorldStandardValues.WORLD_NIGHT_FOG, this.WorldNightFog,
                "World night fog color");

        // Cave settings (still using code from Bucyruss' BiomeTerrainMod)
        writer.bigTitle("Cave settings");

        writer.putSetting(WorldStandardValues.CAVE_RARITY, this.caveRarity,
                "This controls the odds that a given chunk will host a single cave and/or the start of a cave system.");

        writer.putSetting(WorldStandardValues.CAVE_FREQUENCY, this.caveFrequency,
                "The number of times the cave generation algorithm will attempt to create single caves and cave",
                "systems in the given chunk. This value is larger because the likelihood for the cave generation",
                "algorithm to bailout is fairly high and it is used in a randomizer that trends towards lower",
                "random numbers. With an input of 40 (default) the randomizer will result in an average random",
                "result of 5 to 6. This can be turned off by setting evenCaveDistribution (below) to true.");

        writer.putSetting(WorldStandardValues.CAVE_MIN_ALTITUDE, this.caveMinAltitude,
                "Sets the minimum and maximum altitudes at which caves will be generated. These values are",
                "used in a randomizer that trends towards lower numbers so that caves become more frequent",
                "the closer you get to the bottom of the map. Setting even cave distribution (above) to true",
                "will turn off this randomizer and use a flat random number generator that will create an even",
                "density of caves at all altitudes.");
        writer.putSetting(WorldStandardValues.CAVE_MAX_ALTITUDE, this.caveMaxAltitude);

        writer.putSetting(WorldStandardValues.INDIVIDUAL_CAVE_RARITY, this.individualCaveRarity,
                "The odds that the cave generation algorithm will generate a single cavern without an accompanying",
                "cave system. Note that whenever the algorithm generates an individual cave it will also attempt to",
                "generate a pocket of cave systems in the vicinity (no guarantee of connection or that the cave system",
                "will actually be created).");

        writer.putSetting(WorldStandardValues.CAVE_SYSTEM_FREQUENCY, this.caveSystemFrequency,
                "The number of times the algorithm will attempt to start a cave system in a given chunk per cycle of",
                "the cave generation algorithm (see cave frequency setting above). Note that setting this value too",
                "high with an accompanying high cave frequency value can cause extremely long world generation time.");

        writer.putSetting(WorldStandardValues.CAVE_SYSTEM_POCKET_CHANCE, this.caveSystemPocketChance,
                "This can be set to create an additional chance that a cave system pocket (a higher than normal",
                "density of cave systems) being started in a given chunk. Normally, a cave pocket will only be",
                "attempted if an individual cave is generated, but this will allow more cave pockets to be generated",
                "in addition to the individual cave trigger.");

        writer.putSetting(WorldStandardValues.CAVE_SYSTEM_POCKET_MIN_SIZE, this.caveSystemPocketMinSize,
                "The minimum and maximum size that a cave system pocket can be. This modifies/overrides the",
                "cave system frequency setting (above) when triggered.");
        writer.putSetting(WorldStandardValues.CAVE_SYSTEM_POCKET_MAX_SIZE, this.caveSystemPocketMaxSize);

        writer.putSetting(WorldStandardValues.EVEN_CAVE_DISTRIBUTION, this.evenCaveDistribution,
                "Setting this to true will turn off the randomizer for cave frequency (above). Do note that",
                "if you turn this on you will probably want to adjust the cave frequency down to avoid long",
                "load times at world creation.");

        // Ravine settings
        writer.bigTitle("Ravine settings");
        writer.putSetting(WorldStandardValues.RAVINE_RARITY, this.ravineRarity);
        writer.putSetting(WorldStandardValues.RAVINE_MIN_ALTITUDE, this.ravineMinAltitude);
        writer.putSetting(WorldStandardValues.RAVINE_MAX_ALTITUDE, this.ravineMaxAltitude);
        writer.putSetting(WorldStandardValues.RAVINE_MIN_LENGTH, this.ravineMinLength);
        writer.putSetting(WorldStandardValues.RAVINE_MAX_LENGTH, this.ravineMaxLength);
        writer.putSetting(WorldStandardValues.RAVINE_DEPTH, this.ravineDepth);

        // Settings for BiomeMode:OldGenerator
        writer.bigTitle("Settings for BiomeMode:OldGenerator",
                "This generator works only with old terrain generator!");
        writer.putSetting(WorldStandardValues.OLD_BIOME_SIZE, this.oldBiomeSize);
        writer.putSetting(WorldStandardValues.MIN_MOISTURE, this.minMoisture);
        writer.putSetting(WorldStandardValues.MAX_MOISTURE, this.maxMoisture);
        writer.putSetting(WorldStandardValues.MIN_TEMPERATURE, this.minTemperature);
        writer.putSetting(WorldStandardValues.MAX_TEMPERATURE, this.maxTemperature);
        
        writer.bigTitle("World Seed");        
        writer.putSetting(WorldStandardValues.WORLD_SEED, this.worldSeed,
	        "The seed that will be used for this world unless it is overriden in the world creation menu.",
	        "Leave blank for a random seed."
    	);

        writer.bigTitle("BO3 at spawn");        
        writer.putSetting(WorldStandardValues.BO3_AT_SPAWN, this.BO3AtSpawn,
	        "This BO3 will be spawned at the world's spawn point as a CustomObject."
    	);
        
        writer.bigTitle("Pre-generation radius");        
        writer.putSetting(WorldStandardValues.PREGENERATION_RADIUS, this.PreGenerationRadius,
	        "This is the radius in chunks around the spawn chunk within which chunks will automatically be spawned (uses a rectangle, not a circle around the spawn location!",
			"Defaults to: 0 (disabled)"        		
		);
        
        writer.bigTitle("World border radius");        
        writer.putSetting(WorldStandardValues.WORLD_BORDER_RADIUS, this.WorldBorderRadius,
	        "This is the radius in chunks around the spawn chunk within which chunks will have blocks spawned (uses a rectangle, not a circle around the spawn location!)",
			"Defaults to: 0 (disabled)"
		);
        
        // Dimensions
        writer.bigTitle("Dimension");
        writer.putSetting(WorldStandardValues.DIMENSIONS, this.Dimensions,
                "Dimensions that should be loaded for this world at world creation. A world directory of the same name must be present in mods/OpenTerrainGenerator/worlds/");
        writer.putSetting(WorldStandardValues.DIMENSIONBELOW, this.dimensionBelow,
                "When a player goes below Y 0, they will be teleported to this dimension. The dimension must be registered either via Dimensions in the worldconfig or via the /otg dim -c <dimname> console command.");
        writer.putSetting(WorldStandardValues.DIMENSIONABOVE, this.dimensionAbove,
        		"When a player goes above Y 255, they will be teleported to this dimension. The dimension must be registered either via Dimensions in the worldconfig or via the /otg dim -c <dimname> console command.");
        writer.putSetting(WorldStandardValues.DIMENSION_PORTAL_MATERIALS, this.DimensionPortalMaterials,
                "A comma seperated list of blocks, dimension portals made of one or more of these blocks will lead to this world.",
                "For blocks that have rotation such as stairs be sure to add all rotations (0,1,2,3,4,5,6,7), for instance: QUARTZ_STAIRS:0, QUARTZ_STAIRS:1, QUARTZ_STAIRS:2 etc.",
                "For blocks that have rotation such as QUARTZ_STAIRS, \"QUARTZ_STAIRS\" is the same as \"QUARTZ_STAIRS:3\"."
        		);
        
        writer.putSetting(WorldStandardValues.teleportToSpawnOnly, this.teleportToSpawnOnly,
        		"If this is set to true then portals to this dimension will always teleport players to the world's spawn point.");        
        
        writer.putSetting(WorldStandardValues.CARTOGRAPHER, this.Cartographer,
                "Currently in development, the Cartographer is a miniature version of the world (1/16th scale) that can be used to view the world (including players) and teleport players and items. Setting this to true loads and updates the Cartographer world map in the Cartographer dimension. The Cartographer can be reached via a Quartz portal with a chiseled quartz base. The mods/OpenTerrainGenerator/worlds/DIM-Cartographer directory must be present (if you also have OTG-Cartographer.jar in your mods directory worlds/DIM-Cartographer should be created automatically).");
        
        writer.smallTitle("Game rules (dimensions only)", "Game rules for dimensions (these do not work for the overworld at the moment). These settings are still in development and may be subject to change in upcoming releases.", "");        
                      
        writer.putSetting(WorldStandardValues.commandBlockOutput, Boolean.parseBoolean(this.commandBlockOutput), 
        		"Whether command blocks should notify admins when they perform commands");
        writer.putSetting(WorldStandardValues.disableElytraMovementCheck, Boolean.parseBoolean(this.disableElytraMovementCheck),
        		"Whether the server should skip checking player speed when the player is wearing elytra. Often helps with jittering due to lag in multiplayer, but may also be used to travel unfairly long distances in survival mode (cheating).");
        writer.putSetting(WorldStandardValues.doDaylightCycle, Boolean.parseBoolean(this.doDaylightCycle),
        		"Whether the day-night cycle and moon phases progress");
        writer.putSetting(WorldStandardValues.doEntityDrops, Boolean.parseBoolean(this.doEntityDrops),
        		"Whether entities that are not mobs should have drops");
        writer.putSetting(WorldStandardValues.doFireTick, Boolean.parseBoolean(this.doFireTick),
        		"Whether fire should spread and naturally extinguish");
        //writer.putSetting(WorldStandardValues.doLimitedCrafting, Boolean.parseBoolean(this.doLimitedCrafting),
        		//"Whether players should only be able to craft recipes that they've unlocked first");
        writer.putSetting(WorldStandardValues.doMobLoot, Boolean.parseBoolean(this.doMobLoot),
        		"Whether mobs should drop items");
        writer.putSetting(WorldStandardValues.doMobSpawning, Boolean.parseBoolean(this.doMobSpawning),
        		"Whether mobs should naturally spawn. Does not affect monster spawners.");
        writer.putSetting(WorldStandardValues.doTileDrops, Boolean.parseBoolean(this.doTileDrops),
        		"Whether blocks should have drops");
        writer.putSetting(WorldStandardValues.doWeatherCycle, Boolean.parseBoolean(this.doWeatherCycle),
        		"Whether the weather will change");
        //public String gameLoopFunction = "true"; // The function to run every game tick // TODO: Implement for 1.12
        writer.putSetting(WorldStandardValues.keepInventory, Boolean.parseBoolean(this.keepInventory),
        		"Whether the player should keep items in their inventory after death");
        writer.putSetting(WorldStandardValues.logAdminCommands, Boolean.parseBoolean(this.logAdminCommands),
        		"Whether to log admin commands to server log");
        //public String maxCommandChainLength = "65536"; // Determines the number at which the chain command block acts as a "chain". // TODO: Implement for 1.12
        writer.putSetting(WorldStandardValues.maxEntityCramming, Integer.parseInt(this.maxEntityCramming),
        		"The maximum number of other pushable entities a mob or player can push, before taking 3 doublehearts suffocation damage per half-second. Setting to 0 disables the rule. Damage affects survival-mode or adventure-mode players, and all mobs but bats. Pushable entities include non-spectator-mode players, any mob except bats, as well as boats and minecarts.");
        writer.putSetting(WorldStandardValues.mobGriefing, Boolean.parseBoolean(this.mobGriefing),
        		"Whether creepers, zombies, endermen, ghasts, withers, ender dragons, rabbits, sheep, and villagers should be able to change blocks and whether villagers, zombies, skeletons, and zombie pigmen can pick up items");
        writer.putSetting(WorldStandardValues.naturalRegeneration, Boolean.parseBoolean(this.naturalRegeneration),
        		"Whether the player can regenerate health naturally if their hunger is full enough (doesn't affect external healing, such as golden apples, the Regeneration effect, etc.)");
        writer.putSetting(WorldStandardValues.randomTickSpeed, Integer.parseInt(this.randomTickSpeed),
        		"How often a random block tick occurs (such as plant growth, leaf decay, etc.) per chunk section per game tick. 0 will disable random ticks, higher numbers will increase random ticks"); 
        writer.putSetting(WorldStandardValues.reducedDebugInfo, Boolean.parseBoolean(this.reducedDebugInfo),
        		"Whether the debug screen shows all or reduced information; and whether the effects of F3+B (entity hitboxes) and F3+G (chunk boundaries) are shown.");
        writer.putSetting(WorldStandardValues.sendCommandFeedback, Boolean.parseBoolean(this.sendCommandFeedback),
        		"Whether the feedback from commands executed by a player should show up in chat. Also affects the default behavior of whether command blocks store their output text");
        writer.putSetting(WorldStandardValues.showDeathMessages, Boolean.parseBoolean(this.showDeathMessages),
        		"Whether death messages are put into chat when a player dies. Also affects whether a message is sent to the pet's owner when the pet dies");
        writer.putSetting(WorldStandardValues.spawnRadius, Integer.parseInt(this.spawnRadius),
        		"The number of blocks outward from the world spawn coordinates that a player will spawn in when first joining a server or when dying without a spawnpoint.");
        writer.putSetting(WorldStandardValues.spectatorsGenerateChunks, Boolean.parseBoolean(this.spectatorsGenerateChunks),
        		"Whether players in spectator mode can generate chunks");	
    	
        // World provider settings for worlds used as dimensions with Forge : TODO: Apply to overworld too?
        writer.smallTitle("World provider settings (dimensions only)", "World provider settings for dimensions (these do not work for the overworld at the moment). These settings are still in development and may be subject to change in upcoming releases.", "");
        
        writer.putSetting(WorldStandardValues.welcomeMessage, this.welcomeMessage, 
        		"A message to display to the user when they transfer to this dimension.");
        writer.putSetting(WorldStandardValues.departMessage, this.departMessage,
        		"A Message to display to the user when they transfer out of this dimension.");
    	//public boolean isHellWorld = false; // DoesWaterVaporize sets this
        writer.putSetting(WorldStandardValues.hasSkyLight, this.hasSkyLight,
        		"A boolean that tells if a world has a sky or not. Used for calculating weather and skylight. Also affects GetActualHeight(), hasSkyLight = false worlds are seen as 128 height worlds, which affects nether portal placement/detection.");
        writer.putSetting(WorldStandardValues.isSurfaceWorld, this.isSurfaceWorld,
        		"Returns 'true' if in the \"main surface world\", but 'false' if in the Nether or End dimensions. Affects: Clock, Compass, sky/cloud rendering, allowed to sleep here, zombie pigmen spawning in portal frames."); 
        //writer.putSetting(WorldStandardValues.canCoordinateBeSpawn, this.canCoordinateBeSpawn,
        		//"Will check if the x, z position specified is alright to be set as the map spawn point.");
        writer.putSetting(WorldStandardValues.canRespawnHere, this.canRespawnHere,
        		"True if the player can respawn in this dimension (true = overworld, false = nether).");    	
        writer.putSetting(WorldStandardValues.doesWaterVaporize, this.doesWaterVaporize,
        		"True for nether, any water that is placed vaporises.");    	
        writer.putSetting(WorldStandardValues.doesXZShowFog, this.doesXZShowFog,
        		"Returns true if the given X,Z coordinate should show environmental fog. True for Nether.");
        writer.putSetting(WorldStandardValues.useCustomFogColor, this.useCustomFogColor, 
        		"Set this to true if you want to use the fog color settings below.");
        writer.putSetting(WorldStandardValues.fogColorRed, this.fogColorRed);
        writer.putSetting(WorldStandardValues.fogColorGreen, this.fogColorGreen);
        writer.putSetting(WorldStandardValues.fogColorBlue, this.fogColorBlue);
        writer.putSetting(WorldStandardValues.isSkyColored, this.isSkyColored,
        		"Is set to false for End (black sky?)");    	
        //writer.putSetting(WorldStandardValues.averageGroundlevel, this.averageGroundlevel,
        		//"Affects spawn point location and village spawning. Should be equal to sea level + 1(?)");    	
        //writer.putSetting(WorldStandardValues.horizonHeight, this.horizonHeight,
        		//"Returns horizon height for use in rendering the sky. Should be equal to sea level(?)");    	
        writer.putSetting(WorldStandardValues.cloudHeight, this.cloudHeight);
        writer.putSetting(WorldStandardValues.canDoLightning, this.canDoLightning);        
        writer.putSetting(WorldStandardValues.canDoRainSnowIce, this.canDoRainSnowIce);        
        //writer.putSetting(WorldStandardValues.canMineBlock, this.canMineBlock,
        		//"If set to false players are unable to mine blocks");        
        writer.putSetting(WorldStandardValues.isNightWorld, this.isNightWorld,
        		"If true then the sky will be locked at midnight with the moon and stars above but the world will be lit as if it were day time. Useful for space dimensions.");        
        writer.putSetting(WorldStandardValues.voidFogYFactor, this.voidFogYFactor, 
        		"A double value representing the Y value relative to the top of the map at which void fog is at its maximum. The default factor of 0.03125 relative to 256, for example, means the void fog will be at its maximum at (256*0.03125), or 8.");        
        writer.putSetting(WorldStandardValues.shouldMapSpin, this.shouldMapSpin, 
        		"Determine if the cursor on the map should 'spin' when rendered, like it does for the player in the nether.");                
        writer.putSetting(WorldStandardValues.canDropChunk, this.canDropChunk, 
        		"Called to determine if the chunk at the given chunk coordinates within the provider's world can be dropped. Used in WorldProviderSurface to prevent spawn chunks from being unloaded.");       
    }

    private void WriteCustomBiomes(SettingsMap writer)
    {
        List<String> output = new ArrayList<String>();
        // Custom biome id
        List<Entry<String, Integer>> cbi = new ArrayList<Entry<String, Integer>>(this.customBiomeGenerationIds.entrySet());
        Collections.sort(cbi, CBV);
        // Print all custom biomes
        for (Iterator<Entry<String, Integer>> it = cbi.iterator(); it.hasNext();)
        {
            Entry<String, Integer> entry = it.next();
    		if(!((entry.getValue() > 39 && entry.getValue() < 127) || (entry.getValue() > 167)))
        	{
    			// Skip custom biomes with vanilla id's. 
    			// Forge adds these to the custom biomes list 
    			// to make vanilla biomes fully customisable
    			// but the vanilla biome id's shouldn't actually
    			// be written to the WorldConfig.ini file
    			continue;
        	}
            output.add(entry.getKey() + ":" + entry.getValue());
        }
        writer.putSetting(WorldStandardValues.CUSTOM_BIOMES, output,
                "You need to register your custom biomes here. This setting will make Open Terrain Generator",
                "generate setting files for them. However, it won't place them in the world automatically.",
                "See the settings for your BiomeMode below on how to add them to the world.",
                "",
                "Syntax: CustomBiomes:BiomeName:id[,AnotherBiomeName:id[,...]]",
                "Example: CustomBiomes:TestBiome1:30,BiomeTest2:31",
                "This will add two biomes and generate the BiomeConfigs for them.",
                "All changes here need a server restart.",
                "",
                "Due to the way Mojang's loading code works, all biome ids need to be unique",
                "on the server. If you don't do this, the client will display the biomes just fine,",
                "but the server can think it is another biome with the same id. This will cause saplings,",
                "snowfall and mobs to work as in the other biome.",
                "",
                "The available ids range from 0 to 1023 and the ids 0-39 and 127-167 are taken by vanilla.",
                "The ids 256-1023 cannot be saved to the map files, so use ReplaceToBiomeName in that biome.",
				"The Cartographer feature for Forge uses biome id 888.");
    }

    public double getFractureHorizontal()
    {
        return this.fractureHorizontal < 0.0D ? 1.0D / (Math.abs(this.fractureHorizontal) + 1.0D) : this.fractureHorizontal + 1.0D;
    }

    public double getFractureVertical()
    {
        return this.fractureVertical < 0.0D ? 1.0D / (Math.abs(this.fractureVertical) + 1.0D) : this.fractureVertical + 1.0D;
    }

    public enum TerrainMode
    {
        Normal,
        OldGenerator,
        TerrainTest,
        NotGenerate,
        Default
    }

    public enum ImageMode
    {
        Repeat,
        Mirror,
        ContinueNormal,
        FillEmpty,
    }

    public enum ImageOrientation
    {
        North,
        East,
        South,
        West,
    }

    public enum ConfigMode
    {
        WriteAll,
        WriteDisable,
        WriteWithoutComments
    }
}
