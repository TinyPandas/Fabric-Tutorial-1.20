package net.panda.tutorialmod.block.entity;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.panda.tutorialmod.block.custom.FilledCustomBlock;
import net.panda.tutorialmod.item.ModItems;
import net.panda.tutorialmod.networking.ModMessages;
import net.panda.tutorialmod.recipe.CustomRecipe;
import net.panda.tutorialmod.screen.FilledCustomBlockScreenHandler;
import net.panda.tutorialmod.util.FluidStack;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;
import techreborn.init.ModFluids;

import java.util.Optional;

public class FilledCustomBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(30000, 32, 32) {
        @Override
        protected void onFinalCommit() {
            markDirty();

            if (!world.isClient()) {
                sendEnergyPacket();
            }
        }
    };

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return FluidStack.convertDropletsToMb(FluidConstants.BUCKET) * 20; //20k mb
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            if (!world.isClient()) {
                sendFluidPacket();
            }
        }
    };

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

    public FilledCustomBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FILLED_CUSTOM_BLOCK, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0: return FilledCustomBlockEntity.this.progress;
                    case 1: return FilledCustomBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: FilledCustomBlockEntity.this.progress = value; break;
                    case 1: FilledCustomBlockEntity.this.maxProgress = value; break;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public void setEnergyLevel(long energyLevel) {
        this.energyStorage.amount = energyLevel;
    }

    public void setFluidLevel(FluidVariant fluidVariant, long fluidLevel) {
        this.fluidStorage.variant = fluidVariant;
        this.fluidStorage.amount = fluidLevel;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Custom Block");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        sendEnergyPacket();
        sendFluidPacket();

        return new FilledCustomBlockScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("filled_custom_block.progress", progress);
        nbt.putLong("filled_custom_block.energy", energyStorage.amount);
        nbt.put("filled_custom_block.variant", fluidStorage.variant.toNbt());
        nbt.putLong("filled_custom_block.fluid", fluidStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("filled_custom_block.progress");
        energyStorage.amount = nbt.getLong("filled_custom_block.energy");
        fluidStorage.variant = FluidVariant.fromNbt((NbtCompound) nbt.get("filled_custom_block.variant"));
        fluidStorage.amount = nbt.getLong("filled_custom_block.fluid");
    }

    private void resetProgress() {
        this.progress = 0;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        if (side == null) {
            return false;
        }
        if (this.getWorld() == null) {
            return false;
        }
        if (side == Direction.UP || side == Direction.DOWN) {
            return false;
        }

        Direction localDir = this.getWorld().getBlockState(this.pos).get(FilledCustomBlock.FACING);

        // Top insert 1
        // Right insert 1
        // Left insert 0
        Direction oppositeSide = side.getOpposite();
        Direction yClockwise = side.rotateYClockwise();
        Direction yCounter = side.rotateYCounterclockwise();

        return switch(localDir) {
            default -> oppositeSide == Direction.NORTH && slot == 1 || oppositeSide == Direction.EAST && slot == 1 || oppositeSide == Direction.WEST && slot == 0;
            case EAST -> yClockwise == Direction.NORTH && slot == 1 || yClockwise == Direction.EAST && slot == 1 || yClockwise == Direction.WEST && slot == 0;
            case SOUTH -> side == Direction.NORTH && slot == 1 || side == Direction.EAST && slot == 1 || side == Direction.WEST && slot == 0;
            case WEST -> yCounter == Direction.NORTH && slot == 1 || yCounter == Direction.EAST && slot == 1 || yCounter == Direction.WEST && slot == 0;
        };
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        if (this.getWorld() == null) {
            return false;
        }
        if (side == Direction.UP) {
            return false;
        }
        if (side == Direction.DOWN) {
            return slot == 2;
        }

        Direction localDir = this.getWorld().getBlockState(this.pos).get(FilledCustomBlock.FACING);

        //bottom extract 2
        //right extract 2
        Direction oppositeSide = side.getOpposite();
        Direction yClockwise = side.rotateYClockwise();
        Direction yCounter = side.rotateYCounterclockwise();

        return switch(localDir) {
            default -> oppositeSide == Direction.SOUTH && slot == 2 || oppositeSide == Direction.EAST && slot == 2;
            case EAST -> yClockwise == Direction.SOUTH && slot == 2 || yClockwise == Direction.EAST && slot == 2;
            case SOUTH -> side == Direction.SOUTH && slot == 2 || side == Direction.EAST && slot == 2;
            case WEST -> yCounter == Direction.SOUTH && slot == 2 || yCounter == Direction.EAST && slot == 2;
        };
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, FilledCustomBlockEntity entity) {
        if (world.isClient()) return;

        if (hasRecipe(entity) && hasEnoughEnergy(entity) && hasEnoughFluid(entity)) {
            entity.progress++;
            extractEnergy(entity);
            markDirty(world, blockPos, blockState);
            if (entity.progress >= entity.maxProgress) {
                craftItem(entity);
                extractFluid(entity);
            }
        } else {
            entity.resetProgress();
            markDirty(world, blockPos, blockState);
        }

        if (hasFluidSourceInSlot(entity)) {
            transferFluidToFluidStorage(entity);
        }
    }

    private static void transferFluidToFluidStorage(FilledCustomBlockEntity entity) {
        try (Transaction transaction = Transaction.openOuter()) {
            entity.fluidStorage.insert(FluidVariant.of(ModFluids.WOLFRAMIUM.getFluid()), FluidStack.convertDropletsToMb(FluidConstants.BUCKET), transaction);
            transaction.commit();
            entity.setStack(0, new ItemStack(Items.BUCKET));
        }
    }

    private static boolean hasFluidSourceInSlot(FilledCustomBlockEntity entity) {
        return entity.getStack(0).getItem() == ModFluids.WOLFRAMIUM.getBucket();
    }


    private static void extractEnergy(FilledCustomBlockEntity entity) {
        try (Transaction transaction = Transaction.openOuter()) {
            entity.energyStorage.extract(32, transaction);
            transaction.commit();
        }
    }

    private static void extractFluid(FilledCustomBlockEntity entity) {
        try (Transaction transaction = Transaction.openOuter()) {
            entity.fluidStorage.extract(FluidVariant.of(ModFluids.WOLFRAMIUM.getFluid()), 500, transaction);
            transaction.commit();
        }
    }

    private static boolean hasEnoughEnergy(FilledCustomBlockEntity entity) {
        return entity.energyStorage.amount >= 32;
    }

    private static boolean hasEnoughFluid(FilledCustomBlockEntity entity) {
        return entity.fluidStorage.amount >= 500; // mB amount
    }
    private static boolean hasEnergyItem(FilledCustomBlockEntity entity) {
        return entity.getStack(0).getItem() == ModItems.FILLED_CUSTOM_ITEM;
    }

    private static void craftItem(FilledCustomBlockEntity entity) {
        SimpleInventory simpleInventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            simpleInventory.setStack(i, entity.getStack(i));
        }

        Optional<CustomRecipe> recipe = entity.getWorld().getRecipeManager()
                .getFirstMatch(CustomRecipe.Type.INSTANCE, simpleInventory, entity.getWorld());

        if (hasRecipe(entity) && recipe.isPresent()) {
            CustomRecipe recipeResult = recipe.get();
            ItemStack recipeOutput = recipeResult.getOutput(entity.getWorld().getRegistryManager());
            ItemStack newOutput = new ItemStack(recipeOutput.getItem(), entity.getStack(2).getCount() + recipeOutput.getCount());

            entity.removeStack(1, 1);
            entity.setStack(2, newOutput);
            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(FilledCustomBlockEntity entity) {
        SimpleInventory simpleInventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            simpleInventory.setStack(i, entity.getStack(i));
        }

        Optional<CustomRecipe> match = entity.getWorld().getRecipeManager()
                .getFirstMatch(CustomRecipe.Type.INSTANCE, simpleInventory, entity.getWorld());

        return match.isPresent() && canInsertAmountIntoOutputSlot(simpleInventory) &&
                canInsertItemIntoOutputSlot(simpleInventory, match.get().getOutput(entity.getWorld().getRegistryManager()).getItem());
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory simpleInventory, Item output) {
        return simpleInventory.getStack(2).getItem() == output || simpleInventory.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory simpleInventory) {
        return simpleInventory.getStack(2).getMaxCount() > simpleInventory.getStack(2).getCount();
    }

    private void sendEnergyPacket() {
        PacketByteBuf data = PacketByteBufs.create();
        data.writeLong(energyStorage.amount);
        data.writeBlockPos(getPos());

        for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
            ServerPlayNetworking.send(player, ModMessages.ENERGY_SYNC, data);
        }
    }

    private void sendFluidPacket() {
        PacketByteBuf data = PacketByteBufs.create();
        fluidStorage.variant.toPacket(data);
        data.writeLong(fluidStorage.amount);
        data.writeBlockPos(getPos());

        for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
            ServerPlayNetworking.send(player, ModMessages.FLUID_SYNC, data);
        }
    }
}
