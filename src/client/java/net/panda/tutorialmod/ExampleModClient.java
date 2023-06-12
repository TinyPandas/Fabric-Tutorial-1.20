package net.panda.tutorialmod;

import net.fabricmc.api.ClientModInitializer;
import net.panda.tutorialmod.event.KeyInputHandler;
import net.panda.tutorialmod.networking.ModMessages;

public class ExampleModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		KeyInputHandler.register();
		ModMessages.registerS2CPackets();
	}
}