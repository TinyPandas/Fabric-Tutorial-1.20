package net.panda.tutorialmod.screen;

import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {

    public static ScreenHandlerType<FilledCustomBlockScreenHandler> FILLED_CUSTOM_BLOCK_SCREEN_HANDLER;

    public static void registerAllScreenHandlers() {
        FILLED_CUSTOM_BLOCK_SCREEN_HANDLER = new ScreenHandlerType<>(FilledCustomBlockScreenHandler::new, FeatureSet.empty());
    }
}
