package com.tearhpi.immortal.item.custom.Weapon;

import com.tearhpi.immortal.attribute.WeaponAttributeAttack;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.item.custom.WandItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class Weapon_1_2 extends WandItem {
    public Weapon_1_2(WeaponItemProperties p_41383_, int weapon_level, int weapon_type, WeaponAttributeAttack weaponAttributeAttack) {
        super(p_41383_, weapon_level, weapon_type, weaponAttributeAttack);
    }
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof Player player)) return;
        if (!selected) return;

        //屏蔽客户端
        if (level.isClientSide) return;

        //每 10 tick检测一次
        if (player.tickCount % 10 != 0) return;

        double r = 5.0f;
        List<Player> player_List = player.level().getEntitiesOfClass(Player.class, player.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(player) <= r * r && mob != player);//&& mob != player
        for (Player p : player_List) {
            if (p.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof WandItem wandItem) {
                if (wandItem.weapon_attribute.getattributeInt() == 5) {
                    ImposeEffect.ImposeEffectWithoutAmp(player, _ModEffects.WEAPON1_2_EFFECT.get(), 30);
                }
            }
        }
    }
}
