package net.panda.tutorialmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.panda.tutorialmod.screen.renderer.EnergyInfoArea;
import net.panda.tutorialmod.screen.renderer.FluidStackRenderer;
import net.panda.tutorialmod.util.FluidStack;
import net.panda.tutorialmod.util.MouseUtil;

import java.util.Optional;

import static net.panda.tutorialmod.Util.id;

public class FilledCustomBlockScreen extends HandledScreen<FilledCustomBlockScreenHandler> {

    private static final Identifier TEXTURE = id("textures/gui/filled_custom_block_gui.png");

    private EnergyInfoArea energyInfoArea;
    private FluidStackRenderer fluidStackRenderer;

    public FilledCustomBlockScreen(FilledCustomBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        assignEnergyInfoArea();
        assignFluidStackRenderer();
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyInfoArea(((width - backgroundWidth) / 2) + 156,
                ((height - backgroundHeight) / 2) + 13, handler.blockEntity.energyStorage);
    }

    private void assignFluidStackRenderer() {
        fluidStackRenderer = new FluidStackRenderer(FluidStack.convertDropletsToMb(FluidConstants.BUCKET) * 20,
                true, 15, 61);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        renderEnergyAreaTooltips(context, mouseX, mouseY, x, y);
        renderFluidTooltips(context, mouseX, mouseY, x, y, handler.fluidStack, 55, 15, fluidStackRenderer);
    }

    private void renderFluidTooltips(DrawContext context, int mouseX, int mouseY, int x, int y,
                                     FluidStack fluidStack, int offsetX, int offsetY, FluidStackRenderer fluidStackRenderer) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, offsetX, offsetY, fluidStackRenderer)) {
            context.drawTooltip(textRenderer, fluidStackRenderer.getTooltip(fluidStack, TooltipContext.Default.BASIC),
                    Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        renderProgressArrow(context, x, y);
        energyInfoArea.draw(context);
        fluidStackRenderer.drawFluid(context, handler.fluidStack, x + 55, y + 15, 16, 61,
                FluidStack.convertDropletsToMb(FluidConstants.BUCKET) * 20);
    }

    private void renderEnergyAreaTooltips(DrawContext context, int mouseX, int mouseY, int x, int y) {
        if (isMouseAboveArea(mouseX, mouseY, x, y, 156, 13, 8, 64)) {
            context.drawTooltip(textRenderer, energyInfoArea.getTooltips(), Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    private boolean isMouseAboveArea(int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, FluidStackRenderer renderer) {
        return MouseUtil.isMouseOver(mouseX, mouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }

    private boolean isMouseAboveArea(int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(mouseX, mouseY, x + offsetX, y + offsetY, width, height);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isCrafting()) {
            context.drawTexture(TEXTURE, x + 105, y + 33, 176, 0, 8, handler.getScaledProgress());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
