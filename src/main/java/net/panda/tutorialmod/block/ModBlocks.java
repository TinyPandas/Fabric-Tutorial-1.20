package net.panda.tutorialmod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.panda.tutorialmod.ExampleMod;
import net.panda.tutorialmod.block.custom.CustomLampBlock;
import net.panda.tutorialmod.block.custom.FilledCustomBlock;
import net.panda.tutorialmod.block.custom.VirusBlock;
import net.panda.tutorialmod.item.ModItemGroup;

import static net.panda.tutorialmod.Util.id;

public class ModBlocks {

    public static final Block CUSTOM_BLOCK = registerBlock("custom_block",
            new Block(FabricBlockSettings.create().strength(4.0f).requiresTool()),
            ModItemGroup.registryKey());

    public static final Block FILLED_CUSTOM_BLOCK = registerBlock("filled_custom_block",
            new FilledCustomBlock(FabricBlockSettings.create().strength(4.0f).requiresTool()),
            ModItemGroup.registryKey());

    public static final Block VIRUS_BLOCK = registerBlock("virus_block",
            new VirusBlock(FabricBlockSettings.create().strength(4.0f).requiresTool().dropsNothing()),
            ModItemGroup.registryKey());

    public static final Block CUSTOM_LAMP_BLOCK = registerBlock("custom_lamp_block",
            new CustomLampBlock(),
            ModItemGroup.registryKey());

    private static Block registerBlock(String name, Block block, RegistryKey<ItemGroup> tab) {
        registerBlockItem(name, block, tab);
        return Registry.register(Registries.BLOCK, id(name), block);
    }

    private static Item registerBlockItem(String name, Block block, RegistryKey<ItemGroup> tab) {
        BlockItem blockItem = new BlockItem(block, new FabricItemSettings());
        ItemGroupEvents.modifyEntriesEvent(tab).register(content -> content.add(new ItemStack(blockItem)));
        return Registry.register(Registries.ITEM, id(name), blockItem);
    }

    public static void registerModBlocks() {
        ExampleMod.LOGGER.debug("Registering ModBlocks for " + ExampleMod.MODID);
    }
}
