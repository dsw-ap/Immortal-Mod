package com.tearhpi.immortal.event.damagenumbershow;

import com.tearhpi.immortal.client.textrender.RainbowColorClient;
import com.tearhpi.immortal.entity.DamageNumber.TextDisplayWithBindEntity;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.List;
import java.util.stream.Stream;

import static com.tearhpi.immortal.client.textrender.RainbowColor.safeRainbowOrPlain;

public class DamageNumberSpawner {
    //普通伤害数字-玩家
    public static void spawnDamageNumberWithPlayer(ServerLevel level, double x, double y, double z, float damage, Player player, _ImmortalMob mob) {
        IImmortalPlayer immortalPlayer = (IImmortalPlayer) player;
        spawnDamageNumberWithString(level,x,y,z,damage,immortalPlayer.getSetting().Setting2 == 1,mob);
    }
    //普通伤害数字-默认
    public static void spawnDamageNumberWithString(ServerLevel level, double x, double y, double z, float damage,boolean bool, _ImmortalMob mob) {
        spawnDamageNumber(level,x,y,z,damage,bool,mob, null,1);
    }
    //真实伤害数字-玩家
    public static void spawnRealDamageNumberWithPlayer(ServerLevel level, double x,double y,double z, float damage, Player player, _ImmortalMob mob) {
        IImmortalPlayer immortalPlayer = (IImmortalPlayer) player;
        spawnRealDamageNumberWithString(level,x,y,z,damage,immortalPlayer.getSetting().Setting2 == 1,mob);
    }
    //真实伤害数字-默认
    public static void spawnRealDamageNumberWithString(ServerLevel level, double x,double y,double z, float damage,boolean bool, _ImmortalMob mob) {
        spawnDamageNumber(level,x,y,z,damage,bool,mob,null,2);
    }
    public static void spawnDodge(ServerLevel level, double x,double y,double z) {
        spawnDamageNumber(level,x,y,z,0,false,null,
                Component.Serializer.toJson(Component.literal("MISS").setStyle(Style.EMPTY.withColor(0x55FFFF).withBold(true))),0);
    }
    //源(不直接调用)
    public static void spawnDamageNumber(ServerLevel level, double x,double y,double z, float damage,boolean bool,_ImmortalMob mob,String string,int Mode) {
        String string_use = string;
        TextDisplayWithBindEntity textDisplay = new TextDisplayWithBindEntity(EntityType.TEXT_DISPLAY, level,mob);
        if (bool) {
            float r = 5.0f;
            List<TextDisplayWithBindEntity> mobs = mob.level().getEntitiesOfClass(TextDisplayWithBindEntity.class, mob.getBoundingBox().inflate(r), mob_ -> mob_.isAlive() && mob_.distanceToSqr(mob) <= r * r && mob_.getOwnerUUID() == mob.getUUID());
            for (TextDisplayWithBindEntity _mob_ : mobs) {
                int value = Integer.parseInt(_mob_.getText_().getString());
                damage = ((int) damage) + value;
                _mob_.discard();
            }
        }
        //Mode0:文本模式-不改写
        //Mode1:普通伤害模式
        //Mode2:真实伤害模式
        if (Mode == 0) {
            string_use = string;
        } else if (Mode == 1) {
            string_use = Component.Serializer.toJson(Component.literal(String.valueOf((int) damage)).setStyle(Style.EMPTY.withColor(0xFF5555).withBold(true)));
        } else if (Mode == 2) {
            string_use =  Component.Serializer.toJson(RainbowColorClient.drawComponent(Component.literal(String.valueOf((int) damage)).setStyle(Style.EMPTY.withBold(true))));
        }
        // 设置自定义 NBT（如颜色、背景、透明度等）
        CompoundTag tag = new CompoundTag();
        tag.putString("id", "minecraft:text_display");
        tag.putString("billboard", "center");
        tag.putString("text", string_use);
        //tag.putFloat("shadow_radius", 0.0f);
        tag.putInt("color", 0xFFFFFF); // 白色
        tag.putBoolean("see_through", true);
        //tag.putBoolean("default_background", false);
        tag.putFloat("scale", 1.0f);
        tag.putInt("background", 0);
        tag.putBoolean("shadow", false);
        tag.putInt("teleport_duration", 1);

        ListTag translation = new ListTag();
        translation.add(FloatTag.valueOf(0.0f));
        translation.add(FloatTag.valueOf(0.0f));
        translation.add(FloatTag.valueOf(0.0f));

        ListTag scaleTag = new ListTag();
        scaleTag.add(FloatTag.valueOf(2.0f));
        scaleTag.add(FloatTag.valueOf(2.0f));
        scaleTag.add(FloatTag.valueOf(2.0f));

        ListTag leftRotation = new ListTag();
        leftRotation.add(FloatTag.valueOf(0.0f));
        leftRotation.add(FloatTag.valueOf(0.0f));
        leftRotation.add(FloatTag.valueOf(0.0f));
        leftRotation.add(FloatTag.valueOf(1.0f)); // 单位四元数

        ListTag rightRotation = new ListTag();
        rightRotation.add(FloatTag.valueOf(0.0f));
        rightRotation.add(FloatTag.valueOf(0.0f));
        rightRotation.add(FloatTag.valueOf(0.0f));
        rightRotation.add(FloatTag.valueOf(1.0f)); // 单位四元数

        CompoundTag transformation = new CompoundTag();
        transformation.put("translation", translation);
        transformation.put("scale", scaleTag);
        transformation.put("left_rotation", leftRotation);
        transformation.put("right_rotation", rightRotation);

        tag.put("transformation", transformation);

        textDisplay.load(tag);
        textDisplay.setPos(x, y, z);
        textDisplay.addTag("number_show");
        textDisplay.getPersistentData().putInt("number_age", 0);

        // 添加到世界中
        level.addFreshEntity(textDisplay);
    }
}
