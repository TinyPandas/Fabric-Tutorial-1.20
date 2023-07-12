package net.panda.tutorialmod.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static net.panda.tutorialmod.TutorialMod.MODID;

public class ModScreenHandlers {

    public static ScreenHandlerType<FilledCustomBlockScreenHandler> FILLED_CUSTOM_BLOCK_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(FilledCustomBlockScreenHandler::new);

    public static void registerAllScreenHandlers() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MODID, "filled_custom_block"), FILLED_CUSTOM_BLOCK_SCREEN_HANDLER);
    }
}
