package net.panda.tutorialmod.recipe;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static net.panda.tutorialmod.Util.id;

public class ModRecipes {

    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, id(CustomRecipe.Serializer.ID), CustomRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, id(CustomRecipe.Type.ID), CustomRecipe.Type.INSTANCE);
    }
}
