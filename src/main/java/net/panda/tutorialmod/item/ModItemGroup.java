package net.panda.tutorialmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;

import static net.panda.tutorialmod.Util.id;
import static net.panda.tutorialmod.Util.translationKey;

public class ModItemGroup {
    public static final ItemGroup CUSTOM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.CUSTOM_ITEM))
            .displayName(Text.translatable(translationKey("itemGroup", "custom_group")))
            .build();

    public static RegistryKey<ItemGroup> registryKey() {
        return RegistryKey.of(Registries.ITEM_GROUP.getKey(), id("custom_group"));
    }

    public static void registerModItemGroup() {
        Registry.register(Registries.ITEM_GROUP, id("custom_group"), CUSTOM_GROUP);
    }
}
