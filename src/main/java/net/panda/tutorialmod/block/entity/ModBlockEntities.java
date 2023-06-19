package net.panda.tutorialmod.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.panda.tutorialmod.block.ModBlocks;
import team.reborn.energy.api.EnergyStorage;

import static net.panda.tutorialmod.Util.id;

public class ModBlockEntities {
    public static BlockEntityType<FilledCustomBlockEntity> FILLED_CUSTOM_BLOCK;

    public static void registerBlockEntities() {
        FILLED_CUSTOM_BLOCK = Registry.register(Registries.BLOCK_ENTITY_TYPE, id("custom_block"),
                FabricBlockEntityTypeBuilder.create(FilledCustomBlockEntity::new, ModBlocks.FILLED_CUSTOM_BLOCK).build(null));

        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, FILLED_CUSTOM_BLOCK);
        FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.fluidStorage, FILLED_CUSTOM_BLOCK);
    }
}
