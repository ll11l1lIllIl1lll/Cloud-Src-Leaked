/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraftforge.client.event.RenderBlockOverlayEvent
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package cn.CX.Cloud.modules.render;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.TimerUtils;
import cn.CX.Cloud.utils.Utils;
import cn.CX.Cloud.utils.Wrapper;
import cn.CX.Cloud.utils.nBlockPos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Search
extends Module {
    public static List<BlockPos> toRender = new ArrayList<BlockPos>();
    public nBlockPos pos = new nBlockPos();
    public Setting dia = new Setting("Diamond", this, true);
    public Setting gold = new Setting("Gold", this, true);
    public Setting iron = new Setting("Iron", this, true);
    public Setting lapis = new Setting("Lapis", this, true);
    public Setting emerald = new Setting("Emerald", this, true);
    public Setting coal = new Setting("Coal", this, true);
    public Setting redstone = new Setting("Redstone", this, true);
    public Setting bypass = new Setting("Bypass", this, true);
    public Setting depth = new Setting("Depth", this, 2.0, 1.0, 5.0, true);
    public Setting radiusOn = new Setting("RadiusEnabled", this, true);
    public Setting radius = new Setting("Radius", this, 10.0, 5.0, 100.0, true);
    public Setting limitEnabled = new Setting("RenderLimitEnabled", this, true);
    public Setting limit = new Setting("RenderLimit", this, 10.0, 5.0, 100.0, true);
    public Setting refresh_timer = new Setting("RefreshDelay", this, 5.0, 0.0, 50.0, true);
    public Setting alpha = new Setting("Alpha", this, 0.25, 0.0, 1.0, false);
    public Setting width = new Setting("LineWidth", this, 2.5, 1.0, 10.0, false);
    private final Minecraft mc = Minecraft.getMinecraft();
    private final TimerUtils refresh = new TimerUtils();

    @Override
    public void enable() {
        toRender.clear();
        this.refresh.reset();
        this.mc.renderGlobal.loadRenderers();
    }

    public Search() {
        super("Search", 0, Category.Render);
        this.registerSetting(this.dia);
        this.registerSetting(this.gold);
        this.registerSetting(this.iron);
        this.registerSetting(this.lapis);
        this.registerSetting(this.emerald);
        this.registerSetting(this.coal);
        this.registerSetting(this.redstone);
        this.registerSetting(this.bypass);
        this.registerSetting(this.depth);
        this.registerSetting(this.radiusOn);
        this.registerSetting(this.radius);
        this.registerSetting(this.limitEnabled);
        this.registerSetting(this.limit);
        this.registerSetting(this.refresh_timer);
        this.registerSetting(this.alpha);
        this.registerSetting(this.width);
    }

    public boolean test(BlockPos pos1) {
        if (!this.isTarget(pos1)) {
            return false;
        }
        if (this.bypass.isEnabled() && !this.oreTest(pos1, this.depth.getValDouble()).booleanValue()) {
            return false;
        }
        if (this.radiusOn.isEnabled()) {
            return !(this.mc.thePlayer.getDistance((double)pos1.getX(), (double)pos1.getY(), (double)pos1.getZ()) >= this.radius.getValDouble());
        }
        return true;
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (!Utils.currentScreenMinecraft()) {
            return;
        }
        if (this.refresh.isDelayComplete(this.refresh_timer.getValDouble())) {
            WorldClient world = this.mc.theWorld;
            EntityPlayerSP player = this.mc.thePlayer;
            if (world != null && player != null) {
                int sx = (int)player.posX - (int)this.radius.getValDouble();
                int sz = (int)player.posZ - (int)this.radius.getValDouble();
                int endX = (int)player.posX + (int)this.radius.getValDouble();
                int endZ = (int)player.posZ + (int)this.radius.getValDouble();
                for (int x = sx; x <= endX; ++x) {
                    this.pos.setX(x);
                    for (int z = sz; z <= endZ; ++z) {
                        Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
                        if (!chunk.isLoaded()) continue;
                        this.pos.setZ(z);
                        for (int y = 0; y <= 255; ++y) {
                            BlockPos poss;
                            this.pos.setY(y);
                            IBlockState blockState = chunk.getBlockState((BlockPos)this.pos);
                            Block block = blockState.getBlock();
                            if (block == Blocks.air || toRender.contains(poss = new BlockPos(x, y, z)) || !this.test(poss) || (double)toRender.size() > this.limit.getValDouble() && this.limitEnabled.isEnabled()) continue;
                            toRender.add(poss);
                        }
                    }
                }
                List<Object> list = toRender;
                toRender = list = list.stream().filter(this::test).collect(Collectors.toList());
                this.refresh.reset();
            }
        }
    }

    public static float[] getColor(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        if (Blocks.diamond_ore.equals(block)) {
            return new float[]{0.0f, 1.0f, 1.0f};
        }
        if (Blocks.lapis_ore.equals(block)) {
            return new float[]{0.0f, 0.0f, 1.0f};
        }
        if (Blocks.iron_ore.equals(block)) {
            return new float[]{1.0f, 1.0f, 1.0f};
        }
        if (Blocks.gold_ore.equals(block)) {
            return new float[]{1.0f, 1.0f, 0.0f};
        }
        if (Blocks.coal_ore.equals(block)) {
            return new float[]{0.0f, 0.0f, 0.0f};
        }
        if (Blocks.emerald_ore.equals(block)) {
            return new float[]{0.0f, 1.0f, 0.0f};
        }
        if (Blocks.redstone_ore.equals(block) || Blocks.lit_redstone_ore.equals(block)) {
            return new float[]{1.0f, 0.0f, 0.0f};
        }
        return new float[]{0.0f, 0.0f, 0.0f};
    }

    public static void drawOutlinedBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)lineWidth);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        Search.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(1, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        tessellator.draw();
    }

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent event) {
        if (!Utils.currentScreenMinecraft()) {
            return;
        }
        for (BlockPos blockPos : toRender) {
            this.renderBlock(blockPos);
        }
    }

    public void onRender3D(RenderBlockOverlayEvent event) {
        if (!Utils.currentScreenMinecraft()) {
            return;
        }
        if (!(toRender.contains((Object)this.pos) || !this.test(this.pos) || (double)toRender.size() > this.limit.getValDouble() && this.limitEnabled.isEnabled())) {
            toRender.add(this.pos);
        }
    }

    private void renderBlock(BlockPos pos) {
        double x = (double)pos.getX() - Wrapper.getRenderPosX();
        double y = (double)pos.getY() - Wrapper.getRenderPosY();
        double z = (double)pos.getZ() - Wrapper.getRenderPosZ();
        float[] color = Search.getColor(pos);
        Search.drawOutlinedBlockESP(x, y, z, color[0], color[1], color[2], (float)this.alpha.getValDouble(), (float)this.width.getValDouble());
    }

    public boolean isTarget(BlockPos pos) {
        Block block = this.mc.theWorld.getBlockState(pos).getBlock();
        if (Blocks.diamond_ore.equals(block)) {
            return this.dia.isEnabled();
        }
        if (Blocks.lapis_ore.equals(block)) {
            return this.lapis.isEnabled();
        }
        if (Blocks.iron_ore.equals(block)) {
            return this.iron.isEnabled();
        }
        if (Blocks.gold_ore.equals(block)) {
            return this.gold.isEnabled();
        }
        if (Blocks.coal_ore.equals(block)) {
            return this.coal.isEnabled();
        }
        if (Blocks.emerald_ore.equals(block)) {
            return this.emerald.isEnabled();
        }
        if (Blocks.redstone_ore.equals(block) || Blocks.lit_redstone_ore.equals(block)) {
            return this.redstone.isEnabled();
        }
        return false;
    }

    private Boolean oreTest(BlockPos origPos, Double depth) {
        ArrayList<BlockPos> posesNew = new ArrayList<BlockPos>();
        ArrayList<Object> posesLast = new ArrayList<BlockPos>(Collections.singletonList(origPos));
        ArrayList<BlockPos> finalList = new ArrayList<BlockPos>();
        int i = 0;
        while ((double)i < depth) {
            for (BlockPos blockPos2 : posesLast) {
                posesNew.add(blockPos2.up());
                posesNew.add(blockPos2.down());
                posesNew.add(blockPos2.north());
                posesNew.add(blockPos2.south());
                posesNew.add(blockPos2.west());
                posesNew.add(blockPos2.east());
            }
            for (BlockPos blockPos3 : posesNew) {
                if (!posesLast.contains(blockPos3)) continue;
                posesNew.remove(blockPos3);
            }
            posesLast = posesNew;
            finalList.addAll(posesNew);
            posesNew = new ArrayList();
            ++i;
        }
        List<Block> legitBlocks = Arrays.asList(Blocks.water, Blocks.lava, Blocks.flowing_lava, Blocks.air, Blocks.flowing_water, Blocks.fire);
        return finalList.stream().anyMatch(blockPos -> legitBlocks.contains(this.mc.theWorld.getBlockState(blockPos).getBlock()));
    }
}

