package net.panda.tutorialmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.panda.tutorialmod.event.KeyInputHandler;
import net.panda.tutorialmod.item.CustomBucket;
import net.panda.tutorialmod.item.ModItems;
import net.panda.tutorialmod.networking.ClientModMessages;
import net.panda.tutorialmod.screen.FilledCustomBlockScreen;
import net.panda.tutorialmod.screen.ModScreenHandlers;

public class ExampleModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		KeyInputHandler.register();
		ClientModMessages.registerS2CPackets();

		HandledScreens.register(ModScreenHandlers.FILLED_CUSTOM_BLOCK_SCREEN_HANDLER, FilledCustomBlockScreen::new);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((CustomBucket) ModItems.CUSTOM_BUCKET).getColor(tintIndex), ModItems.CUSTOM_BUCKET);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((CustomBucket) ModItems.CUSTOM_BUCKET_2).getColor(tintIndex), ModItems.CUSTOM_BUCKET_2);
	}
}