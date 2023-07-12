package net.panda.tutorialmod.world.feature;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.panda.tutorialmod.TutorialMod;

import static net.panda.tutorialmod.Util.id;

public class ModConfiguredFeatures {

    private static final Identifier OVERWORLD_VIRUS_BLOCK_ID = id("overworld_virus_block");
    public static final RegistryKey<PlacedFeature> OVERWORLD_VIRUS_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, OVERWORLD_VIRUS_BLOCK_ID);

    public static void registerConfiguredFeatures() {
        TutorialMod.LOGGER.debug("Registering ModConfiguredFeatures " + TutorialMod.MODID);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.TOP_LAYER_MODIFICATION, OVERWORLD_VIRUS_PLACED_KEY);
    }
}
