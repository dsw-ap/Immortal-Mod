package com.tearhpi.immortal.mixin;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom.Weapons.Weapon1_3_Entity;
import com.tearhpi.immortal.entity.custom.skills.Skill34_Entity;
import com.tearhpi.immortal.entity.custom.TrainerDummy;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.entity.custom.zSpecialWandBullet.WandBullet1_1;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.item.custom.Weapon.*;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.ShieldSyncPacket;
import com.tearhpi.immortal.networking.SkillInUseSyncToClientPacket;
import com.tearhpi.immortal.networking.skill._WandNormalAttackSyncPacket;
import com.tearhpi.immortal.skill.behaviour.active.fire.Skill14;
import com.tearhpi.immortal.skill.behaviour.active.fire.Skill25;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import com.tearhpi.immortal.util.statics.StaticParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.*;

import static com.tearhpi.immortal.event.damagenumbershow.DamageNumberSpawner.*;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract double getAttributeValue(Attribute p_251296_);

    @Shadow protected abstract boolean trapdoorUsableAsLadder(BlockPos p_21177_, BlockState p_21178_);

    @Shadow public abstract boolean attackable();

    @Shadow
    private static byte entityEventForEquipmentBreak(EquipmentSlot p_21267_) {
        return 0;
    }

    //生物属性创建
    @Inject(method = "createLivingAttributes", at = @At("RETURN"), cancellable = true)
    private static void injectCustomAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        AttributeSupplier.Builder builder = cir.getReturnValue();

        builder.add(ModAttributes.IMMORTAL_MAX_HEALTH.get())
                .add(ModAttributes.IMMORTAL_ATTACK_DAMAGE.get())
                .add(ModAttributes.IMMORTAL_DEFENSE.get())
                .add(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_FIRE.get())
                .add(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_WATER.get())
                .add(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_EARTH.get())
                .add(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_AIR.get())
                .add(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_LIGHT.get())
                .add(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_DARKNESS.get())
                .add(ModAttributes.IMMORTAL_DODGE.get());
        /*
        helper(ModAttributes.IMMORTAL_MAX_HEALTH,builder);
        helper(ModAttributes.IMMORTAL_ATTACK_DAMAGE,builder);
        helper(ModAttributes.IMMORTAL_DEFENSE,builder);
        helper(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_FIRE,builder);
        helper(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_WATER,builder);
        helper(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_EARTH,builder);
        helper(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_AIR,builder);
        helper(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_LIGHT,builder);
        helper(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_DARKNESS,builder);
         */
        cir.setReturnValue(builder);
    }
    /*
    @Unique
    private static void helper(RegistryObject<Attribute> roa, AttributeSupplier.Builder builder) {
        if (builder.hasAttribute(roa.getHolder().get())) {
            builder.add(roa.getHolder().get());
        }
    }
     */
    //生物最大血量修改
    @Redirect(
            method = "getMaxHealth",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D"
            )
    )
    private double immortal$redirectMaxHealth(LivingEntity instance, Attribute ignored) {
        // 1.20.1 下，这里必须是 Attribute；不要 Holder
        Attribute immortalMax = ModAttributes.IMMORTAL_MAX_HEALTH.get(); // RegistryObject<Attribute> -> Attribute
        return instance.getAttributeValue(immortalMax);
    }
    //生物受伤逻辑修改[非玩家]
    /**
     * @author TearH_Pi
     * @reason 修改生物受伤逻辑
     */
    @Overwrite
    public float getDamageAfterArmorAbsorb(DamageSource p_21162_, float p_21163_) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        //使用新版伤害系统
        boolean UseNewDefendSystem = true;
        //若玩家打玩家,不造成任何伤害
        if (livingEntity instanceof Player && p_21162_.getEntity() instanceof Player) {
            return 0.0f;
        }
        //该方法由受到伤害的敌人执行
        //敌人侧[防御相关计算+最终加伤]
        //防御力
        double defense_get = livingEntity.getAttributeValue(ModAttributes.IMMORTAL_DEFENSE.getHolder().get());
        //defense_get = UseNewDefendSystem ? (float) (defense_get / (defense_get+1555)) : defense_get;
        //闪避
        double dodge = livingEntity.getAttributeValue(ModAttributes.IMMORTAL_DODGE.getHolder().get());
        //玩家侧
        Entity attacker = p_21162_.getEntity();
        if (attacker instanceof Player player) {
            /**玩家攻击敌人*/
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            //数值获取:伤害以及冷却
            //float cooldown = p_21163_ - (float) Math.floor(p_21163_);
                //满蓄力还原
            //    float cooldown_get = (float) (cooldown - 0.99);
            //    if (Math.abs(cooldown_get) <= 0.01) cooldown = 1;
            float cooldown = 1;
            IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) attacker;
            //攻击数值还原
            p_21163_ = (float) Math.floor(p_21163_);
            double final_damage_add = player.getAttributeValue(ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.getHolder().get()); //最终加伤
            double ignore_defense = player.getAttributeValue(ModAttributes.IMMORTAL_IGNORE_DEFENSE.getHolder().get()); //无视防御
            //受伤侧特殊效果
            //灼烧 特殊效果
            if (livingEntity.hasEffect(_ModEffects.FIRING_EFFECT.get())) {
                if (p_21162_.is(ModDamageSources.IMMORTAL_FIRE)){
                    p_21163_ *= 1.15f;
                }
            }
            //Skill12 特殊效果
            if (player.hasEffect(_ModEffects.SKILL12_EFFECT.get())) {
                int x = player.getEffect(_ModEffects.SKILL12_EFFECT.get()).getAmplifier()+1;
                //5%灼烧 x秒
                Random random = new Random();
                int rand1 = random.nextInt(1,100);
                //System.out.println("S1"+rand1);
                if (rand1 <= 5) {
                    //System.out.println("True");
                    ImposeEffect.ApplyEffectLayer(livingEntity,_ModEffects.FIRING_EFFECT.get(),x*20);
                }
                //0.5%湮灭 2x秒
                int rand2 = random.nextInt(1,1000);
                //System.out.println("S2"+rand2);
                if (rand2 <= 5) {
                    //System.out.println("True");
                    ImposeEffect.ApplyEffectLayer(livingEntity,_ModEffects.ANNIHILATE_EFFECT.get(),2*x*20);
                }
            }
            //Skill14 特殊效果
            if (iImmortalPlayer.getSkill().Skill_14_Level > 0 && p_21162_.is(ModDamageSources.IMMORTAL_FIRE)) {
                int val = iImmortalPlayer.getSkill().Skill_14_Level;
                Random random = new Random();
                int rand1 = random.nextInt(1,100);
                if (rand1 <= val) {
                    Skill14.Do((ServerPlayer) player,livingEntity);
                }
            }
            //Skill16 特殊效果
            if (iImmortalPlayer.getSkill().Skill_16_Level > 0){
                if (p_21162_.is(ModDamageSources.IMMORTAL_WATER)){
                    if (livingEntity instanceof _ImmortalMob immortalMob){
                        immortalMob.addWaterAmount(5);
                        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof Weapon_2_1) {
                            immortalMob.addWaterAmount(1);
                        }
                    }
                }
            }
            //Skill18 特殊效果
            if (livingEntity.hasEffect(_ModEffects.SKILL18_ADD_DMG_EFFECT.get()) && livingEntity instanceof _ImmortalMob immortalMob) {
                if (p_21162_.is(ModDamageSources.IMMORTAL_WATER)){
                    ModDamageSources modDamageSources = new ModDamageSources(player.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_REAL,player);
                    livingEntity.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(livingEntity), (float) immortalMob.getWaterAmount() /300));
                    //随机施加侵蚀
                    Random rand = new Random();
                    int val = rand.nextInt(1,20);
                    if (val == 1) {
                        ImposeEffect.ApplyEffectLayer(livingEntity,_ModEffects.EROSION_EFFECT.get(),60);
                    }
                }
            }
            if (livingEntity.hasEffect(_ModEffects.SKILL18_ADD_DMG_EFFECT_Spec.get()) && livingEntity instanceof _ImmortalMob immortalMob) {
                if (p_21162_.is(ModDamageSources.IMMORTAL_WATER)){
                    ModDamageSources modDamageSources = new ModDamageSources(player.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_REAL,player);
                    livingEntity.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(livingEntity), (float) immortalMob.getWaterAmount() / 200));
                    //随机施加侵蚀
                    Random rand = new Random();
                    int val = rand.nextInt(1,20);
                    if (val <= 3) {
                        ImposeEffect.ApplyEffectLayer(livingEntity,_ModEffects.EROSION_EFFECT.get(),60);
                    }
                }
            }
            //Skill19 特殊效果
            if (livingEntity.hasEffect(_ModEffects.SKILL19_ADD_WATER_AMOUNT.get()) && livingEntity instanceof _ImmortalMob immortalMob) {
                if (p_21162_.is(ModDamageSources.IMMORTAL_WATER)){
                    immortalMob.addWaterAmount(3);
                }
            }
            //Skill23 特殊效果
            if (livingEntity instanceof _ImmortalMob immortalMob) {
                if (immortalMob.hasSkill23()){
                    p_21163_ *= 1.1F;
                }
            }
            //Skill25 特殊效果
            if (iImmortalPlayer.getSkill().Skill_25_Level > 0 && p_21162_.is(ModDamageSources.IMMORTAL_WATER)) {
                int val = iImmortalPlayer.getSkill().Skill_25_Level;
                Random random = new Random();
                int rand1 = random.nextInt(1,100);
                if (rand1 <= val) {
                    Skill25.Do((ServerPlayer) player,livingEntity);
                }
            }
            //Skill34 特殊效果
            if (player.hasEffect(_ModEffects.SKILL34_EFFECT.get()) && p_21162_.is(ModDamageSources.IMMORTAL_AIR)) {
                Random rand = new Random();
                Vec3 pos = livingEntity.position().add(new Vec3(rand.nextFloat(-2,2), 2, rand.nextFloat(-2,2)));
                Vec3 motion = livingEntity.position().add(new Vec3(0.0,1.0,0.0)).subtract(pos).normalize().scale(0.5);
                ServerPlayer sp = (ServerPlayer) player;
                ServerLevel level = (ServerLevel) sp.level();
                Skill34_Entity e = Skill34_Entity.spawn((ServerLevel) sp.level(), sp, pos, motion, 2);
                BoundEntityManager_SkillAttack.bind((ServerPlayer) player, e);
                level.addFreshEntity(e);
            }
            //Skill36 特殊效果
            if (iImmortalPlayer.getSkill().Skill_36_Level > 0 && p_21162_.is(ModDamageSources.IMMORTAL_AIR)) {
                int val = iImmortalPlayer.getSkill().Skill_36_Level;
                Random random = new Random();
                int rand1 = random.nextInt(1,100);
                if (rand1 <= val) {
                    ImposeEffect.ImposeLayer(livingEntity, MobEffects.LEVITATION,60,0);
                }
                if (livingEntity.hasEffect(MobEffects.LEVITATION)) {
                    p_21163_ *= 1.35f;
                }
            }
            //Skill46 特殊效果
            if (livingEntity instanceof _ImmortalMob immortalMob && immortalMob.getskill46Time() > 0){
                immortalMob.setskill46Level(immortalMob.getskill46Level() - 1);
                if (immortalMob.getskill46Level() == 0){
                    immortalMob.setskill46Time(1);
                }
            }
            //Skill60 特殊效果
            if (iImmortalPlayer.getSkill().Skill_60_Level > 0 && p_21162_.is(ModDamageSources.IMMORTAL_DARKNESS)) {
                int val = 10;
                Random random = new Random();
                int rand1 = random.nextInt(1,100);
                if (rand1 <= val) {
                    if (item instanceof Weapon_2_4) {
                        ImposeEffect.ApplyEffectLayer(livingEntity, _ModEffects.RAWCURSE_EFFECT.get(), 40);
                    } else {
                        ImposeEffect.ApplyEffectLayer(livingEntity, _ModEffects.RAWCURSE_EFFECT.get(), 20);
                    }
                }
            }
            //Skill67 特殊效果
            if (iImmortalPlayer.getSkill().Skill_67_Level > 0 && p_21162_.is(ModDamageSources.IMMORTAL_DARKNESS)) {
                int val = iImmortalPlayer.getSkill().Skill_67_Level;
                int Criteria1 = 3 + val;
                Random random = new Random();
                if (random.nextInt(1,100) <= Criteria1) {
                    ImposeEffect.ApplyEffectLayer(livingEntity, _ModEffects.NIGHTMARE_EFFECT.get(), 200);
                }
                int Criteria2 = 8 + val;
                if (random.nextInt(1,100) <= Criteria2) {
                    Collection<MobEffectInstance> effects = livingEntity.getActiveEffects();
                    List<MobEffectInstance> toUpdate = new ArrayList<>();
                    for (MobEffectInstance inst : effects) {
                        MobEffect effect = inst.getEffect();
                        if (effect.getCategory() != MobEffectCategory.HARMFUL) continue;
                        ResourceLocation id = livingEntity.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.MOB_EFFECT).getKey(effect);
                        if (id == null) continue;
                        int dur = inst.getDuration();
                        MobEffectInstance newInst = new MobEffectInstance(effect, dur * 2, inst.getAmplifier(), inst.isAmbient(), inst.isVisible(), inst.showIcon());
                        toUpdate.add(newInst);
                    }
                    Collections.shuffle(toUpdate, new Random());
                    if (!toUpdate.isEmpty()) {
                        livingEntity.addEffect(toUpdate.get(0));
                    }
                }
            }
            //Weapon1_2 特殊效果
            if (item instanceof Weapon_1_2) {
                int size = 0;
                Collection<MobEffectInstance> effects = livingEntity.getActiveEffects();
                for (MobEffectInstance inst : effects) {
                    if (inst.getEffect().getCategory() == MobEffectCategory.HARMFUL) size += 1;
                }
                p_21163_ *= (1.0f+size*0.5f);
            }
            //数值显示
            if (livingEntity instanceof TrainerDummy trainerDummy) {
                player.sendSystemMessage(Component.translatable("system.damage_output5",p_21163_,defense_get,ignore_defense,final_damage_add));
            }
            //真实伤害
            if (p_21162_.is(ModDamageSources.IMMORTAL_REAL)){
                float return_value = (float) ((p_21163_+final_damage_add) * cooldown);
                SpawnRealDamageNumber(livingEntity,return_value,player);
                return return_value;
            }
            //湮灭 特殊效果
            if (livingEntity.hasEffect(_ModEffects.ANNIHILATE_EFFECT.get())) {
                //湮灭等级获取
                MobEffectInstance mobEffectInstance = livingEntity.getEffect(_ModEffects.ANNIHILATE_EFFECT.get());
                if (mobEffectInstance != null) {
                    int v_ = mobEffectInstance.getAmplifier();
                    float return_value = (float) ((float) ((p_21163_+final_damage_add) * cooldown)*((v_ + 1)*0.1 + 1));
                    SpawnRealDamageNumber(livingEntity,return_value,player);
                    return return_value;
                }
            }

            //非真实伤害
            //1.防御计算
                //特殊计算:侵蚀效果
                if (livingEntity.hasEffect(_ModEffects.EROSION_EFFECT.get())) {
                    MobEffectInstance effectInstance = livingEntity.getEffect(_ModEffects.EROSION_EFFECT.get());
                    if (effectInstance != null) {
                        int val = Math.min(effectInstance.getAmplifier() + 1,20);
                        //乘区转化
                        double val_ = 1-0.05*val;
                        //防御计算
                        defense_get *= val_;
                    }
                }
            defense_get = Math.max(defense_get - ignore_defense,0);
            //防御转化
            defense_get = defense_get / (defense_get + 1555);
            //2.防御检定
            if (UseNewDefendSystem) {
                //使用新版防御系统(乘除防御)
                p_21163_ *= (float) (1-defense_get);
            } else {
                //使用旧版防御系统(加减防御)
                if (defense_get >= p_21163_) {
                    //防御力大于受伤 造成保底伤害2%
                    p_21163_ *= 0.02F;
                } else {
                    p_21163_ -= (float) defense_get;
                }
            }
            //3.元素防御
            float FireElementDef = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_FIRE.getHolder().get());
            float WaterElementDef = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_WATER.getHolder().get());
            float AirElementDef = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_AIR.getHolder().get());
            float EarthElementDef = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_EARTH.getHolder().get());
            float LightElementDef = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_LIGHT.getHolder().get());
            float DarknessElementDef = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_DARKNESS.getHolder().get());
            if (p_21162_.is(ModDamageSources.IMMORTAL_FIRE)){
                if (livingEntity.hasEffect(_ModEffects.SKILL15_EFFECT_REMOVE_DEF.get())){
                    FireElementDef *= 0.7F;
                }
                p_21163_*=(1-FireElementDef);
            } else if (p_21162_.is(ModDamageSources.IMMORTAL_WATER)){
                if (item instanceof Weapon_2_1 w) {
                    if (Weapon_2_1.getCd(itemStack) <= 0) {
                        _WandNormalAttackSyncPacket.SummonNormalAttack((ServerLevel) player.level(),(ServerPlayer ) player);
                        Weapon_2_1.setCd(itemStack,200);
                    }
                }
                p_21163_*=(1-WaterElementDef);
            } else if (p_21162_.is(ModDamageSources.IMMORTAL_AIR)){
                //1-4特效
                if (item instanceof Weapon_1_4) {
                    if (Weapon_1_4.getCd(itemStack) <= 0) {
                        Weapon1_3_Entity.spawn((ServerLevel) player.level(), (ServerPlayer) player, livingEntity.position(), new Vec3(0.0f, 0.0f, 0.0f), 3.0f);
                        Weapon_1_4.setCd(itemStack,140);
                    }
                }
                //2-3特效
                if (item instanceof Weapon_2_3) {
                    p_21163_*= 1.1f;
                }
                p_21163_*=(1-AirElementDef);
            } else if (p_21162_.is(ModDamageSources.IMMORTAL_EARTH)){
                p_21163_*=(1-EarthElementDef);
            } else if (p_21162_.is(ModDamageSources.IMMORTAL_LIGHT)){
                p_21163_*=(1-LightElementDef);
            } else if (p_21162_.is(ModDamageSources.IMMORTAL_DARKNESS)){
                p_21163_*=(1-DarknessElementDef);
                //Skill66 特殊效果
                if (livingEntity.hasEffect(_ModEffects.SKILL66_DEBUFF.get())) {
                    p_21163_ *= 1.5f;
                }
                //Skill67 特殊效果
                if (iImmortalPlayer.getSkill().Skill_67_Level > 0) {
                    int val = iImmortalPlayer.getSkill().Skill_67_Level;
                    //用暗系伤害攻击身上有3种及以上debuff的敌人时,其受到伤害额外+12%
                    int size = 0;
                    Collection<MobEffectInstance> effects = livingEntity.getActiveEffects();
                    for (MobEffectInstance inst : effects) {
                        if (inst.getEffect().getCategory() == MobEffectCategory.HARMFUL) {
                            size += 1;
                        }
                    }
                    if (size >= 3) {
                        p_21163_ *= 1.12f;
                    }
                }
                //Skill69 特殊效果
                if (livingEntity instanceof _ImmortalMob immortalMob) {
                    if (immortalMob.getskill69Time() > 0) {
                        int size = 0;
                        Collection<MobEffectInstance> effects = livingEntity.getActiveEffects();
                        for (MobEffectInstance inst : effects) {
                            if (inst.getEffect().getCategory() == MobEffectCategory.HARMFUL) {
                                size += 1;
                            }
                        }
                        p_21163_ *= (float) (1+0.1*size);
                        immortalMob.setskill69_Amount((float) (immortalMob.getskill69_Amount() + p_21163_ * 0.15));
                    }
                }
                //2-4特效
                if (item instanceof Weapon_2_4) {
                    int size =0;
                    Collection<MobEffectInstance> effects = livingEntity.getActiveEffects();
                    for (MobEffectInstance inst : effects) {
                        if (inst.getEffect().getCategory() == MobEffectCategory.HARMFUL) {
                            size += 1;
                            break;
                        }
                    }
                    if (Weapon_2_4.getCd(itemStack) <= 0 && size >= 1) {
                        ImposeEffect.ImposeEffectWithoutAmp(player,_ModEffects.WEAPON2_4_EFFECT.get(), 200);
                        Weapon_2_4.setCd(itemStack,400);
                    }
                }
            }
            //4.最终伤害增加
            p_21163_ += (float) final_damage_add;
            //冷却
            p_21163_ *= cooldown;
            if (livingEntity instanceof TrainerDummy trainerDummy) {
                player.sendSystemMessage(Component.translatable("system.damage_output6",cooldown,p_21163_));
            }
            //闪避判定
            Random random = new Random();
            float dodge_randomNum = random.nextFloat();
            if (dodge_randomNum < dodge) {
                SpawnDodge(livingEntity,p_21163_);
                return 0;
            }
            SpawnDamageNumber(livingEntity,p_21163_,player);


            //Skill66 特殊效果(传导)
            if (livingEntity.hasEffect(_ModEffects.SKILL66_DEBUFF.get()) && p_21162_.is(ModDamageSources.IMMORTAL_DARKNESS)) {
                double r = 5.0;
                List<_ImmortalMob> mobs = livingEntity.level().getEntitiesOfClass(_ImmortalMob.class, livingEntity.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.hasEffect(_ModEffects.SKILL66_DEBUFF.get()) && !mob.equals(livingEntity));
                if (!mobs.isEmpty()) {
                    Collections.shuffle(mobs,new Random());
                    _ImmortalMob mob = mobs.get(0);
                    ModDamageSources modDamageSources = new ModDamageSources(livingEntity.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_NONE,p_21162_.getEntity());
                    mob.hurt(damagesource, p_21163_/2);
                    StaticParticle.spawnParticleLine((ServerLevel) livingEntity.level(),livingEntity.position().add(0.0,1.0,0.0),mob.position().add(0.0,1.0,0.0), ParticleTypes.SMOKE,20);
                }
            }
            //噩梦 - 传导
            if (livingEntity.hasEffect(_ModEffects.NIGHTMARE_EFFECT.get()) && p_21162_.is(ModDamageSources.IMMORTAL_DARKNESS)) {
                MobEffectInstance mobEffectInstance = livingEntity.getEffect(_ModEffects.NIGHTMARE_EFFECT.get());
                int amp = mobEffectInstance.getAmplifier()+1;
                double r = amp * 2;
                List<_ImmortalMob> mobs = livingEntity.level().getEntitiesOfClass(_ImmortalMob.class, livingEntity.getBoundingBox().inflate(r), mob -> mob.isAlive() && !mob.equals(livingEntity));
                if (!mobs.isEmpty()) {
                    Collections.shuffle(mobs,new Random());
                    _ImmortalMob mob = mobs.get(0);
                    ModDamageSources modDamageSources = new ModDamageSources(livingEntity.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_NONE,p_21162_.getEntity());
                    mob.hurt(damagesource, (float) (amp*0.15));
                    StaticParticle.spawnParticleLine((ServerLevel) livingEntity.level(),livingEntity.position().add(0.0,1.0,0.0),mob.position().add(0.0,1.0,0.0), ParticleTypes.SMOKE,20);
                }
            }
            return p_21163_;
        } else if (livingEntity instanceof Player player) {
            /**敌人攻击玩家*/
            //Skill47 特殊效果
            IImmortalPlayer iImmortalPlayer = (IImmortalPlayer)player;
            if (iImmortalPlayer.getSkill().Skill_47_Level > 0) {
                int val = iImmortalPlayer.getSkill().Skill_47_Level;
                Random random = new Random();
                int rand1 = random.nextInt(1,100);
                if (rand1 <= val) {
                    ImposeEffect.ImposeLayer(livingEntity, _ModEffects.SKILL47_EFFECT.get(), 100, val-1);
                }
            }
            //真实伤害
            if (p_21162_.is(ModDamageSources.IMMORTAL_REAL)){
                //不灭圣盾 无敌检测
                float t1 = Skill45Detect(player,p_21163_,p_21162_);
                p_21163_ = t1;
                return p_21163_;
            }
            //非真实伤害
            //闪避判定
            Random random = new Random();
            float dodge_randomNum = random.nextFloat();
            if (dodge_randomNum < dodge) {
                player.sendSystemMessage(Component.translatable("system.dodge_output"));
                return 0.0f;
            }
            //1.防御计算
            defense_get = defense_get / (defense_get + 1555);
            //2.防御检定
            if (UseNewDefendSystem) {
                //使用新版防御系统(乘除防御)
                p_21163_ *= (float) (1-defense_get);
            } else {
                //使用旧版防御系统(加减防御)
                if (defense_get >= p_21163_) {
                    //防御力大于受伤 造成保底伤害2%
                    p_21163_ *= 0.02F;
                } else {
                    p_21163_ -= (float) defense_get;
                }
            }
            //player.sendSystemMessage(Component.literal("TEST2+"+p_21163_));
            //3.元素防御
            float FireElementDef = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_FIRE.getHolder().get());
            float WaterElementDef = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_WATER.getHolder().get());
            float AirElementDef = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_AIR.getHolder().get());
            float EarthElementDef = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_EARTH.getHolder().get());
            float LightElementDef = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_LIGHT.getHolder().get());
            float DarknessElementDef = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_DARKNESS.getHolder().get());
            if (p_21162_.is(ModDamageSources.IMMORTAL_FIRE)){
                p_21163_*=(1-FireElementDef);
            } else if (p_21162_.is(ModDamageSources.IMMORTAL_WATER)){
                p_21163_*=(1-WaterElementDef);
            } else if (p_21162_.is(ModDamageSources.IMMORTAL_AIR)){
                p_21163_*=(1-AirElementDef);
            } else if (p_21162_.is(ModDamageSources.IMMORTAL_EARTH)){
                p_21163_*=(1-EarthElementDef);
            } else if (p_21162_.is(ModDamageSources.IMMORTAL_LIGHT)){
                p_21163_*=(1-LightElementDef);
            } else if (p_21162_.is(ModDamageSources.IMMORTAL_DARKNESS)){
                p_21163_*=(1-DarknessElementDef);
            }
            //4.额外减伤
            //player.sendSystemMessage(Component.literal("TEST3+"+p_21163_));
            double val = player.getAttributeValue(ModAttributes.IMMORTAL_PLAYER_EXTRA_PROTECTION.getHolder().get());
            double use = 1 - val;
            p_21163_ *= (float) use;
            //p_21163_ +=
            //不灭圣盾 无敌检测
            //player.sendSystemMessage(Component.literal("TEST4+"+p_21163_));
            float t1 = Skill45Detect(player,p_21163_,p_21162_);
            p_21163_ = t1;
            //player.sendSystemMessage(Component.literal("TEST5+"+p_21163_));
            //受伤增加 特殊乘区
            if (true) {
                ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                if (item instanceof Weapon_2_3) {
                    p_21163_*= 1.1f;
                }
            }
            return p_21163_;
        }
        //无来源伤害
        if (attacker == null) {
            //livingEntity.sendSystemMessage(Component.literal("TEST3"));
            //敌人受到无来源伤害
            if (livingEntity instanceof _ImmortalMob mob) {
                //无来源真实伤害
                if (true){
                    //p_21162_.is(ModDamageSources.IMMORTAL_REAL)) {
                    SpawnDamageNumber(livingEntity, p_21163_,null);
                    return p_21163_;
                }
            } else if (livingEntity instanceof Player player) {
                //不灭圣盾 无敌检测
                float t1 = Skill45Detect(player,p_21163_,p_21162_);
                p_21163_ = t1;
                return p_21163_;
            }
        }
        return 0.0f;
    }
    @Unique
    private void SpawnDamageNumber(LivingEntity victim, float actualDamage,@Nullable Player player) {
        ServerLevel serverLevel = (ServerLevel) victim.level();
        double x = victim.getX();
        double y = victim.getY() + victim.getBbHeight() * 0.5; // 头部高度
        double z = victim.getZ();
        double offsetX = (Math.random() - 0.5) * 2;
        double offsetY = (Math.random() - 0.5) * 1;
        double offsetZ = (Math.random() - 0.5) * 2;
        if (player != null && victim instanceof _ImmortalMob mob_) {
            spawnDamageNumberWithPlayer(serverLevel,x + offsetX,y + offsetY,z + offsetZ,actualDamage,player,mob_);
        } else {
            if (victim instanceof _ImmortalMob mob_) {
                spawnDamageNumberWithString(serverLevel,x + offsetX,y + offsetY,z + offsetZ,actualDamage,true,mob_);
            } else {
                spawnDamageNumberWithString(serverLevel,x + offsetX,y + offsetY,z + offsetZ,actualDamage,false,null);
            }
        }
    }
    @Unique
    private void SpawnRealDamageNumber(LivingEntity victim, float actualDamage,@Nullable Player player) {
        ServerLevel serverLevel = (ServerLevel) victim.level();
        double x = victim.getX();
        double y = victim.getY() + victim.getBbHeight() * 0.5; // 头部高度
        double z = victim.getZ();
        double offsetX = (Math.random() - 0.5) * 2;
        double offsetY = (Math.random() - 0.5) * 1;
        double offsetZ = (Math.random() - 0.5) * 2;
        if (player != null && victim instanceof _ImmortalMob mob_) {
            spawnRealDamageNumberWithPlayer(serverLevel,x + offsetX,y + offsetY,z + offsetZ,actualDamage,player,mob_);
        } else {
            if (victim instanceof _ImmortalMob mob_) {
                spawnRealDamageNumberWithString(serverLevel,x + offsetX,y + offsetY,z + offsetZ,actualDamage,true,mob_);
            } else {
                spawnRealDamageNumberWithString(serverLevel,x + offsetX,y + offsetY,z + offsetZ,actualDamage,false,null);
            }
        }
    }
    @Unique
    private void SpawnDodge(LivingEntity victim, float actualDamage) {
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
        spawnDodge(serverLevel,x + offsetX,y + offsetY,z + offsetZ);
    }
    /**
     * @author TearH_Pi
     * @reason 修改生物受伤逻辑
     */
    @Overwrite
    protected float getDamageAfterMagicAbsorb(DamageSource p_21193_, float p_21194_) {
        return p_21194_;
    }

    //删除受伤冷却
    @Redirect(
            method = "hurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/damagesource/DamageSource;is(Lnet/minecraft/tags/TagKey;)Z"
            )
    )
    private boolean immortal$forceBypass(DamageSource source, TagKey<DamageType> tag) {
        if (tag == DamageTypeTags.BYPASSES_COOLDOWN) {
            return true; // 强制“此伤害类型可绕过受击无敌帧”
        }
        return source.is(tag); // 其它 tag 保持原逻辑
    }

    //屏障
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void immortal$shieldHurt(DamageSource p_21016_, float p_21017_, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity instanceof Player player) {
            IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
            int shieldLayers = iImmortalPlayer.getShield().get();
            if (shieldLayers > 0) {
                shieldLayers -= 1;
                ModNetworking.CHANNEL.sendToServer(new ShieldSyncPacket(shieldLayers));

                player.playNotifySound(SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                player.sendSystemMessage(Component.translatable("system.shield_broken"));
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
    //不灭圣盾检测
    @Unique
    public float Skill45Detect(Player player, float p_21163_, DamageSource DamageSources_) {
        //不灭圣盾 无敌检测
        //System.out.println("TEST4:"+p_21163_);
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        if (iImmortalPlayer.getSkillInUse().skill45Lasting > 0 && p_21163_ >= player.getHealth()) {
            iImmortalPlayer.getSkillInUse().skill45Lasting = 1;
            CompoundTag confirmed = iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo());
            ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),new SkillInUseSyncToClientPacket(confirmed));
            player.sendSystemMessage(Component.translatable("skill_45.mind"));
            player.playNotifySound(SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
            return 0.0f;
        }
        //队友分担伤害检测
        if (player.hasEffect(_ModEffects.SKILL45_EFFECT_OTHERS.get())) {
            float r = 15.0f;
            float finalR = r;
            List<Player> mobs = player.level().getEntitiesOfClass(Player.class, player.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR && mob != player && mob.hasEffect(_ModEffects.SKILL45_EFFECT_SELF.get()));
            //player.sendSystemMessage(Component.literal("TEST1"));
            if (!mobs.isEmpty()) {
                Player p = mobs.get(0);
                //p.sendSystemMessage(Component.literal("TEST2"));
                ModDamageSources modDamageSources = new ModDamageSources(p.level().registryAccess());
                DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_REAL,DamageSources_.getEntity());
                //p.sendSystemMessage(Component.literal("TEST+"+p_21163_/2));
                p.hurt(damagesource, p_21163_/2);
                return p_21163_/2;
            }
        }
        return p_21163_;
    }
}
