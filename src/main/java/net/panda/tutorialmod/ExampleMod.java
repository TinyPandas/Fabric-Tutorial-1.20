package net.panda.tutorialmod;

import net.fabricmc.api.ModInitializer;

import net.panda.tutorialmod.block.ModBlocks;
import net.panda.tutorialmod.item.ModItemGroup;
import net.panda.tutorialmod.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	public static final String MODID = "tutorialmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModItemGroup.registerModItemGroup();
	}
}