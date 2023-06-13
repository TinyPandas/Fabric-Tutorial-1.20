package net.panda.tutorialmod;

import net.fabricmc.api.ModInitializer;

import net.panda.tutorialmod.block.ModBlocks;
import net.panda.tutorialmod.block.entity.ModBlockEntities;
import net.panda.tutorialmod.item.ModItemGroup;
import net.panda.tutorialmod.item.ModItems;
import net.panda.tutorialmod.networking.ModMessages;
import net.panda.tutorialmod.screen.ModScreenHandlers;
import net.panda.tutorialmod.world.feature.ModConfiguredFeatures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	public static final String MODID = "tutorialmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		ModConfiguredFeatures.registerConfiguredFeatures();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModItemGroup.registerModItemGroup();
		ModMessages.registerC2SPackets();

		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerAllScreenHandlers();
	}
}