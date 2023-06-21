package net.panda.tutorialmod.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import reborncore.common.powerSystem.RcEnergyItem;
import reborncore.common.powerSystem.RcEnergyTier;
import reborncore.common.util.ItemUtils;
import techreborn.items.BatteryItem;

public class CustomBattery extends Item implements RcEnergyItem {

    private final int maxEnergy;
    private final RcEnergyTier tier;

    public CustomBattery(Settings settings) {
        super(settings);
        maxEnergy = 1000;
        tier = RcEnergyTier.LOW;
    }

    @Override
    public TypedActionResult<ItemStack> use(final World world, final PlayerEntity player, final Hand hand) {
        final ItemStack stack = player.getStackInHand(hand);
        if (player.isSneaking()) {
            ItemUtils.switchActive(stack, 1, player);
            return new TypedActionResult<>(ActionResult.SUCCESS, stack);
        }
        return new TypedActionResult<>(ActionResult.PASS, stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        ItemUtils.checkActive(stack, 1, entity);
        if (world.isClient) {
            return;
        }
        if (!ItemUtils.isActive(stack)){
            return;
        }
        if (entity instanceof PlayerEntity) {
            ItemUtils.distributePowerToInventory((PlayerEntity) entity, stack, tier.getMaxOutput(), (testStack) -> !(testStack.getItem() instanceof BatteryItem));
        }
    }

    @Override
    public long getEnergyCapacity() {
        return maxEnergy;
    }

    @Override
    public RcEnergyTier getTier() {
        return tier;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round((getStoredEnergy(stack) * 100f / getEnergyCapacity() * 13) / 100);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0xFF8006;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }
}
