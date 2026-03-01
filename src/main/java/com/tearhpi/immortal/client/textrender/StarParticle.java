package com.tearhpi.immortal.client.textrender;

import java.util.Random;

public class StarParticle {
    public float x, y; // 当前坐标
    public float vx, vy; // 速度
    public float alpha; // 当前透明度
    public float size;  // 缩放倍数
    public int lifetime, age;
    public float alpha_multiplier = (float) (((new Random().nextFloat())*0.5+0.5)*0.8);

    public StarParticle(float startX, float startY, float vx, float vy, float size, int lifetime) {
        this.x = startX;
        this.y = startY;
        this.vx = vx;
        this.vy = vy;
        this.size = size;
        this.lifetime = lifetime;
        this.age = 0;
        this.alpha = 0f;
    }

    public void tick() {
        x += vx;
        y += vy;
        age++;


        // 淡入淡出控制
        float lifeRatio = age / (float) lifetime;
        alpha = (float) ((float) Math.sin(Math.PI * lifeRatio)*alpha_multiplier); // 先淡入再淡出（0 → 1 → 0）
    }

    public boolean isDead() {
        return age >= lifetime;
    }
}
