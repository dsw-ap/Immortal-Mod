package com.tearhpi.immortal.damage;

import com.sun.security.auth.module.LdapLoginModule;
import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.item.custom.Weapon.Weapon_1_3;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;


//玩家攻击主类
public class MainDamage {
    /**
     * 该方法计算造成侧伤害,包含所有玩家的数据
     * 输入参数:玩家;伤害类型;是否输出调试信息;伤害倍率(可忽略,若忽略则使用默认值100%)
     * 输出值:伤害(还需要经过防御端计算)
    */
    //通用类
    public static float getDamage(Player player, DamageSource damagesource,boolean Output,float attack_multiple_base,float critical_chance_adj) {
        if (player != null) {
            IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
            float f = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get());
            //伤害倍率
            f = add_attack(player,f) * attack_multiple_base;
            float output_attack = f;
            //额外倍率
            double tmp_extra_mdamage_get = (float)player.getAttributeValue(ModAttributes.IMMORTAL_EXTRA_MDAMAGE.getHolder().get());
            f *= (float) tmp_extra_mdamage_get;
            //暴击判定
            double tmp_critical_damage_chance = (float)player.getAttributeValue(ModAttributes.IMMORTAL_CRITICAL_CHANCE.getHolder().get());
            double tmp_critical_damage = (float)player.getAttributeValue(ModAttributes.IMMORTAL_CRITICAL_DAMAGE.getHolder().get());
            double random = Math.random();
            int output_isCriticalDamage = 0;
            if (critical_chance_adj == -1) {
                if (random < tmp_critical_damage_chance) {
                    f *= (float) tmp_critical_damage;
                    output_isCriticalDamage = 1;
                }
            } else {
                if (random < critical_chance_adj) {
                    f *= (float) tmp_critical_damage;
                    output_isCriticalDamage = 1;
                }
            }
            //元素伤害计算
            String output_ElementName = "None";
            float FireElementDamageAdd = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.getHolder().get());
            float WaterElementDamageAdd = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_WATER.getHolder().get());
            float AirElementDamageAdd = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.getHolder().get());
            float EarthElementDamageAdd = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_EARTH.getHolder().get());
            float LightElementDamageAdd = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_LIGHT.getHolder().get());
            float DarknessElementDamageAdd = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.getHolder().get());
            Item itemInHand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (damagesource.is(ModDamageSources.IMMORTAL_FIRE) || itemInHand instanceof Weapon_1_3/*特殊被动*/){
                f *= FireElementDamageAdd;
                output_ElementName = "Fire";
            } else if (damagesource.is(ModDamageSources.IMMORTAL_WATER ) || itemInHand instanceof Weapon_1_3/*特殊被动*/){
                f *= WaterElementDamageAdd;
                output_ElementName = "Water";
            } else if (damagesource.is(ModDamageSources.IMMORTAL_AIR)){
                f *= AirElementDamageAdd;
                output_ElementName = "Air";
            } else if (damagesource.is(ModDamageSources.IMMORTAL_EARTH)){
                f *= EarthElementDamageAdd;
                output_ElementName = "Earth";
            } else if (damagesource.is(ModDamageSources.IMMORTAL_LIGHT)){
                f *= LightElementDamageAdd;
                output_ElementName = "Light";
            } else if (damagesource.is(ModDamageSources.IMMORTAL_DARKNESS)){
                f *= DarknessElementDamageAdd;
                output_ElementName = "Darkness";
            }
            if (Output == true) {
                player.sendSystemMessage(Component.translatable("system.damage_output1",output_attack,tmp_extra_mdamage_get));
                player.sendSystemMessage(Component.translatable("system.damage_output2",tmp_critical_damage_chance,output_isCriticalDamage,tmp_critical_damage));
                player.sendSystemMessage(Component.translatable("system.damage_output3",output_ElementName,FireElementDamageAdd,WaterElementDamageAdd,AirElementDamageAdd,EarthElementDamageAdd,LightElementDamageAdd,DarknessElementDamageAdd));
                player.sendSystemMessage(Component.translatable("system.damage_output3.5",attack_multiple_base));
                player.sendSystemMessage(Component.translatable("system.damage_output4",(int) f));
            }
            return f;
        }
        return 0;
    }
    //默认攻击力(倍率1)
    public static float getDamage(Player player, DamageSource damagesource,boolean Output) {
        return getDamage(player,damagesource,Output,1,-1);
    }
    //默认攻击力(倍率1)
    public static float getDamage(Player player, DamageSource damagesource,boolean Output,float attack_multiple_base) {
        return getDamage(player,damagesource,Output,attack_multiple_base,-1);
    }

    //配套
    private static float add_attack(Player player, float damage) {
        //攻击力加算

        //攻击力乘算

        return damage;
    }
}
