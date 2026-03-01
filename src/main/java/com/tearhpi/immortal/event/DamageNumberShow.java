package com.tearhpi.immortal.event;

import com.tearhpi.immortal.Immortal;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Map;
import java.util.WeakHashMap;

import static com.tearhpi.immortal.event.damagenumbershow.DamageNumberSpawner.spawnDamageNumber;

@Mod.EventBusSubscriber(modid = Immortal.MODID)
public class DamageNumberShow {
    /*
    private static final Map<LivingEntity, Long> lastHealthMap = new WeakHashMap<>();

    // 记录旧血量（在攻击发生时）
    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        //System.out.println(event.getEntity().getHealth());
        if (!event.getEntity().level().isClientSide) {
            lastHealthMap.put(event.getEntity(), (long) event.getEntity().getHealth());
        }
    }

    // 在下一 tick 检查血量差值
    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        //System.out.println(event.getEntity().getHealth());
        LivingEntity entity = event.getEntity();
        if (!entity.level().isClientSide && lastHealthMap.containsKey(entity)) {
            long oldHealth = lastHealthMap.remove(entity);
            long newHealth = (long) entity.getHealth();

            // 忽略死亡或治疗等情况
            if (newHealth < oldHealth) {
                long actualDamage = oldHealth - newHealth;

                // 在此显示伤害数字
                LivingEntity victim = event.getEntity();
                if (victim instanceof Player) {return;}
                if (victim.level().isClientSide()) return;
                ServerLevel serverLevel = (ServerLevel) victim.level();
                double x = victim.getX();
                double y = victim.getY() + victim.getBbHeight() * 0.5; // 头部高度
                double z = victim.getZ();
                // 随机偏移位置
                double offsetX = (Math.random() - 0.5) * 2;
                double offsetY = (Math.random() - 0.5) * 1;
                double offsetZ = (Math.random() - 0.5) * 2;
                // 通知客户端播放粒子
                //System.out.println(actualDamage);
                spawnDamageNumber(serverLevel,x + offsetX,y + offsetY,z + offsetZ,actualDamage);
            }
        }
    }
     */

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        for (ServerLevel level : server.getAllLevels()) {
            for (Display.TextDisplay display : level.getEntities(EntityType.TEXT_DISPLAY, e -> e.getTags().contains("number_show"))) {
                CompoundTag data = display.getPersistentData();

                // 1. 漂浮效果：每 tick 上移一点（如 0.03）
                display.setPos(display.getX(), display.getY() + 0.08, display.getZ());

                // 2. 生命周期统计
                int age = data.getInt("number_age");
                age++;
                data.putInt("number_age", age);

                // 3. 达到 10 tick 删除
                if (age >= 20) {
                    display.discard(); // 移除实体
                }
            }
        }
    }
}
