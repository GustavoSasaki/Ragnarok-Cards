package ragnarok_cards.Items;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import static ragnarok_cards.RagnarokCards.MOD_ID;


public class BagScreen extends ContainerScreen<BagContainer> {

    private ResourceLocation GUI;

    public BagScreen(BagContainer container, PlayerInventory playerInventory, ITextComponent name) {
        super(container, playerInventory, name);
        GUI = new ResourceLocation(MOD_ID, "textures/gui/bag_gui.png");
        xSize = 176;
        ySize = 150;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        this.font.drawString(matrixStack, this.title.getString(), 7,6,0x404040);
    }

    @Override
    public void render(MatrixStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground(matrixStack);
        this.renderHoveredTooltip(matrixStack, p_render_1_, p_render_2_);
        super.render(matrixStack,p_render_1_, p_render_2_, p_render_3_);
    }


    @SuppressWarnings("deprecation")
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f ,1.0f);
        this.getMinecraft().textureManager.bindTexture(GUI);
        drawTexturedQuad(guiLeft, guiTop, xSize, ySize, 0, 0, 1, 1, 0);
    }

    private void drawTexturedQuad(int x, int y, int width, int height, float tx, float ty, float tw, float th, float z) {
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();

        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos((double)x + 0, (double) y + height, (double) z).tex(tx,ty + th).endVertex();
        buffer.pos((double) x + width,(double) y + height, (double) z).tex(tx + tw,ty + th).endVertex();
        buffer.pos((double) x + width, (double) y + 0, (double) z).tex(tx + tw,ty).endVertex();
        buffer.pos  ((double) x + 0, (double) y + 0, (double) z).tex(tx,ty).endVertex();

        tess.draw();
    }
}
