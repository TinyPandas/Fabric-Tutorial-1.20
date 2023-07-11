package net.panda.tutorialmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("tutorialmod");
    @Override
    public void onInitialize() {
        LOGGER.info("Loading tutorialmod");
    }
}
