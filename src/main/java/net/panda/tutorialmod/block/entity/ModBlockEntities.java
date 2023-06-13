package net.panda.tutorialmod.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.panda.tutorialmod.block.ModBlocks;

import static net.panda.tutorialmod.Util.id;

public class ModBlockEntities {
    public static BlockEntityType<FilledCustomBlockEntity> FILLED_CUSTOM_BLOCK;

    public static void registerBlockEntities() {
        FILLED_CUSTOM_BLOCK = Registry.register(Registries.BLOCK_ENTITY_TYPE, id("custom_block"),
                FabricBlockEntityTypeBuilder.create(FilledCustomBlockEntity::new, ModBlocks.FILLED_CUSTOM_BLOCK).build(null));
    }
}
