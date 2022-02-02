/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.MathHelper
 *  org.lwjgl.opengl.GL11
 */
package cn.CX.Cloud.utils;

import cn.CX.Cloud.utils.HUDUtils;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ParticleGenerator {
    private int count;
    private int width;
    private int height;
    private ArrayList<Particle> particles = new ArrayList();
    private Random random = new Random();
    int state = 0;
    int a = 255;
    int r = 255;
    int g = 0;
    int b = 0;

    public ParticleGenerator(int count, int width, int height) {
        this.count = count;
        this.width = width;
        this.height = height;
        for (int i = 0; i < count; ++i) {
            this.particles.add(new Particle(this.random.nextInt(width), this.random.nextInt(height)));
        }
    }

    public void drawParticles(int mouseX, int mouseY) {
        for (Particle p : this.particles) {
            if (p.reset) {
                p.resetPosSize();
                p.reset = false;
            }
            p.draw(mouseX, mouseY);
        }
    }

    public class Particle {
        private int x;
        private int y;
        private int k;
        private float size;
        private boolean reset;
        private Random random = new Random();

        public Particle(int x, int y) {
            this.x = x;
            this.y = y;
            this.size = this.genRandom(1.0f, 3.0f);
        }

        public void draw(int mouseX, int mouseY) {
            if (this.size <= 0.0f) {
                this.reset = true;
            }
            this.size -= 0.05f;
            ++this.k;
            int xx = (int)(MathHelper.cos((float)(0.1f * (float)(this.x + this.k))) * 10.0f);
            int yy = (int)(MathHelper.cos((float)(0.1f * (float)(this.y + this.k))) * 10.0f);
            HUDUtils.drawBorderedCircle(this.x + xx, this.y + yy, this.size, 0, 0x20FFFFFF);
            float distance = (float)HUDUtils.distance(this.x + xx, this.y + yy, mouseX, mouseY);
            if (distance < 50.0f) {
                float alpha1 = Math.min(1.0f, Math.min(1.0f, 1.0f - distance / 50.0f));
                GL11.glEnable((int)2848);
                GL11.glDisable((int)2929);
                GL11.glColor4f((float)255.0f, (float)255.0f, (float)255.0f, (float)255.0f);
                GL11.glDisable((int)3553);
                GL11.glDepthMask((boolean)false);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glEnable((int)3042);
                GL11.glLineWidth((float)0.1f);
                GL11.glBegin((int)1);
                GL11.glVertex2f((float)(this.x + xx), (float)(this.y + yy));
                GL11.glVertex2f((float)mouseX, (float)mouseY);
                GL11.glEnd();
            }
        }

        public void resetPosSize() {
            this.x = this.random.nextInt(ParticleGenerator.this.width);
            this.y = this.random.nextInt(ParticleGenerator.this.height);
            this.size = this.genRandom(1.0f, 3.0f);
        }

        public float genRandom(float min, float max) {
            return (float)((double)min + Math.random() * (double)(max - min + 1.0f));
        }
    }
}

