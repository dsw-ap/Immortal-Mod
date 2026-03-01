package com.tearhpi.immortal.mixin;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.attribute.WeaponAttributeAttack;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.capability.*;
import com.tearhpi.immortal.entity.custom.TrainerDummy;
import com.tearhpi.immortal.item.custom.WeaponItem;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.SettingToClientPacket;
import com.tearhpi.immortal.networking.ShieldSyncPacket;
import com.tearhpi.immortal.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IImmortalPlayer {
    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    public ManaPoint manaPoint = new ManaPoint();
    public Coin Coin = new Coin();
    public Skill Skill = new Skill();
    public SkillAssemble SkillAssemble = new SkillAssemble();
    public SkillCD SkillCD = new SkillCD();
    public SkillInUse SkillInUse = new SkillInUse();
    public Shield shield = new Shield();
    public BuffAmount buffAmount = new BuffAmount();
    public Chatbox chatBox = new Chatbox();
    public Setting setting = new Setting();
    public Achievement achievement = new Achievement();
    public MainTask mainTask = new MainTask();
    public NormalAttackEnergy normalAttackEnergy = new NormalAttackEnergy();
    public float extraDamage = 1.0f;
    public float ChatboxPT = 0.0f;


    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    //玩家属性创建
    private static void injectCustomAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        AttributeSupplier.Builder builder = cir.getReturnValue();
        builder.add(ModAttributes.IMMORTAL_CRITICAL_CHANCE.get())
                .add(ModAttributes.IMMORTAL_CRITICAL_DAMAGE.get())
                .add(ModAttributes.IMMORTAL_IGNORE_DEFENSE.get())
                .add(ModAttributes.IMMORTAL_EXTRA_MDAMAGE.get())
                .add(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.get())
                .add(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_WATER.get())
                .add(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_EARTH.get())
                .add(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.get())
                .add(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_LIGHT.get())
                .add(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.get())
                .add(ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.get())
                .add(ModAttributes.IMMORTAL_PLAYER_EXTRA_PROTECTION.get())
                .add(ModAttributes.IMMORTAL_PLAYER_DEBUFF_RESISTANCE.get())
                .add(ModAttributes.IMMORTAL_MAGIC_POINT_MAX.get())
                .add(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get())
                .add(ModAttributes.IMMORTAL_PLAYER_SKILL_COOLDOWN_IMMUNE.get())
                .add(ModAttributes.IMMORTAL_PLAYER_BUFF_AMOUNT.get());

        cir.setReturnValue(builder);
    }
    //玩家攻击逻辑修改
    protected abstract float getEnchantedDamage(Entity target, float baseDamage, DamageSource source);

    @Shadow protected FoodData foodData;
    @Shadow
    protected abstract boolean isImmobile();
    @Shadow
    public abstract boolean isInvulnerableTo(DamageSource p_36249_);

    @Shadow public abstract boolean isReducedDebugInfo();

    /**
     * @author TearH_Pi
     * @reason 修改玩家攻击逻辑
     */
    @Overwrite
    public void attack(Entity p_36347_) {
        Player player = ((Player)(Object)this);
        if (!net.minecraftforge.common.ForgeHooks.onPlayerAttackTarget(player, p_36347_)) return;
        //冷却判定
        //float f2 = (player.getAttackStrengthScale(0.0F));
        //    //攻击冷却数据保存
        //    if (f2 == 1) {
        //        f2 = 0.99F;
        //    }
        //player.sendSystemMessage(Component.literal("output:" + f2));
        //if (f2 < 1.0F) return;
        //检测敌人是否可被攻击
        if (p_36347_.isAttackable()) {
            if (!p_36347_.skipAttackInteraction(player)) {
                ItemStack itemstack = player.getMainHandItem();
                ModDamageSources modDamageSources = new ModDamageSources(player.level().registryAccess());
                //伤害源获取
                DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_NONE,player);
                if (itemstack.getItem() instanceof WeaponItem weaponitem) {
                    if (weaponitem.weapon_attribute == WeaponAttributeAttack.FIRE) {
                        damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE,player);
                    } else if (weaponitem.weapon_attribute == WeaponAttributeAttack.WATER) {
                        damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_WATER,player);
                    } else if (weaponitem.weapon_attribute == WeaponAttributeAttack.EARTH) {
                        damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_EARTH,player);
                    } else if (weaponitem.weapon_attribute == WeaponAttributeAttack.AIR) {
                        damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_AIR,player);
                    } else if (weaponitem.weapon_attribute == WeaponAttributeAttack.LIGHT) {
                        damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_LIGHT,player);
                    } else if (weaponitem.weapon_attribute == WeaponAttributeAttack.DARKNESS) {
                        damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_DARKNESS,player);
                    }
                }
                //damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_REAL,player);
                //攻击伤害获取
                boolean Output = false;
                if (p_36347_ instanceof TrainerDummy trainerDummy) {
                    Output = true;
                }
                float f = MainDamage.getDamage(player,damagesource,Output,getExtraDamage());
                //冷却数值传递
                //f = (float) Math.floor(f) + f2;
                //重置攻击计时
                player.resetAttackStrengthTicker();
                //攻击可被反弹的投射物(烈焰弹) 无需修改
                /*
                if (p_36347_.getType().is(EntityTypeTags.REDIRECTABLE_PROJECTILE)
                        && p_36347_ instanceof Projectile projectile
                        && projectile.deflect(ProjectileDeflection.AIM_DEFLECT, player, player, true)) {
                    this.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_NODAMAGE, player.getSoundSource());
                    return;
                }

                 */
                //攻击伤害
                if (f > 0.0F) {
                    //boolean flag4 = true;
                    //boolean flag;
                    //玩家疾跑且蓄满力(暴击)
                    /*
                    if (this.isSprinting() && flag4) {
                        this.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_KNOCKBACK, player.getSoundSource(), 1.0F, 1.0F);
                        flag = true;
                    } else {
                        flag = false;
                    }
                     */
                    /*
                    //额外伤害增加
                    f += itemstack.getItem().getAttackDamageBonus(p_36347_, f, damagesource);
                    //暴击条件检定
                    boolean flag1 = flag4
                            && player.fallDistance > 0.0F
                            && !player.onGround()
                            && !player.onClimbable()
                            && !player.isInWater()
                            && !player.hasEffect(MobEffects.BLINDNESS)
                            && !player.isPassenger()
                            && p_36347_ instanceof LivingEntity
                            && !player.isSprinting();
                    var hitResult = net.minecraftforge.common.ForgeHooks.getCriticalHit(player, p_36347_, flag1, flag1 ? 1.5F : 1.0F);
                    flag1 = hitResult != null;
                    //暴击倍乘
                    if (flag1) {
                        f *= hitResult.getDamageModifier();
                    }
                    //横扫之刃判定
                    boolean flag2 = false;
                    double d0 = (double)(player.walkDist - player.walkDistO);
                    if (flag4 && !flag1 && !flag && player.onGround() && d0 < (double)player.getSpeed()) {
                        ItemStack itemstack1 = player.getItemInHand(InteractionHand.MAIN_HAND);
                        if (itemstack.canPerformAction(net.minecraftforge.common.ToolActions.SWORD_SWEEP)) {
                            flag2 = true;
                        }
                    }
                     */
                    //读取实体血量
                    float f6 = 0.0F;
                    if (p_36347_ instanceof LivingEntity livingentity) {
                        f6 = livingentity.getHealth();
                    }
                   //此处已经完成了对伤害相关计算,存在f中
                    Vec3 vec3 = p_36347_.getDeltaMovement();
                    boolean flag3 = p_36347_.hurt(damagesource, f);
                    if (flag3) {
                        float f4 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
                        f4 += (float)EnchantmentHelper.getKnockbackBonus(this);
                        if (f4 > 0.0F) {
                            if (p_36347_ instanceof LivingEntity livingentity1) {
                                livingentity1.knockback(
                                        (double)(f4 * 0.5F),
                                        (double) Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)),
                                        (double)(-Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)))
                                );
                            } else {
                                p_36347_.push(
                                        (double)(-Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)) * f4 * 0.5F),
                                        0.1,
                                        (double)(Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)) * f4 * 0.5F)
                                );
                            }

                            player.setDeltaMovement(player.getDeltaMovement().multiply(0.6, 1.0, 0.6));
                            player.setSprinting(false);
                        }
                        //横扫之刃具体效果
                        /*
                        if (flag2) {
                            float f7 = 1.0F + (float)player.getAttributeValue(Attributes.SWEEPING_DAMAGE_RATIO) * f;

                            for (LivingEntity livingentity2 : player.level().getEntitiesOfClass(LivingEntity.class, player.getItemInHand(InteractionHand.MAIN_HAND).getSweepHitBox(this, p_36347_))) {
                                if (livingentity2 != player
                                        && livingentity2 != p_36347_
                                        && !player.isAlliedTo(livingentity2)
                                        && (!(livingentity2 instanceof ArmorStand) || !((ArmorStand)livingentity2).isMarker())
                                        && player.distanceToSqr(livingentity2) < 9.0) {
                                    float f5 = player.getEnchantedDamage(livingentity2, f7, damagesource) * f2;
                                    livingentity2.knockback(
                                            0.4F,
                                            (double)Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)),
                                            (double)(-Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)))
                                    );
                                    livingentity2.hurt(damagesource, f5);
                                    if (player.level() instanceof ServerLevel serverlevel) {
                                        EnchantmentHelper.doPostAttackEffects(serverlevel, livingentity2, damagesource);
                                    }
                                }
                            }

                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
                            player.sweepAttack();
                        }
                         */
                        //双端同步
                        if (p_36347_ instanceof ServerPlayer && p_36347_.hurtMarked) {
                            ((ServerPlayer)p_36347_).connection.send(new ClientboundSetEntityMotionPacket(p_36347_));
                            p_36347_.hurtMarked = false;
                            p_36347_.setDeltaMovement(vec3);
                        }
                        //暴击特效,暴击音效
                        /*
                        if (flag1) {
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, player.getSoundSource(), 1.0F, 1.0F);
                            player.crit(p_36347_);
                        }
                        */
                        //普通攻击音效/重,轻击
                        /*
                        if (!flag1 && !flag2) {
                        if (flag4) {
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_STRONG, player.getSoundSource(), 1.0F, 1.0F);
                        } else {
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_WEAK, player.getSoundSource(), 1.0F, 1.0F);
                        }
                        }
                         */
                        //攻击音效
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_STRONG, player.getSoundSource(), 1.0F, 1.0F);
                        //附魔攻击特效
                        /*
                        if (f1 > 0.0F) {
                            player.magicCrit(p_36347_);
                        }
                         */
                        //处理父实体(大型实体,龙等)
                        player.setLastHurtMob(p_36347_);
                        Entity entity = p_36347_;
                        //附魔处理与武器耐久
                        if (p_36347_ instanceof LivingEntity) {
                            EnchantmentHelper.doPostHurtEffects((LivingEntity)p_36347_, this);
                        }
                        //大型实体处理
                        EnchantmentHelper.doPostDamageEffects(this, p_36347_);
                        ItemStack itemstack1 = this.getMainHandItem();
                        if (p_36347_ instanceof PartEntity) {
                            entity = ((PartEntity)p_36347_).getParent();
                        }
                        //物品被击碎处理
                        if (!this.level().isClientSide && !itemstack1.isEmpty() && entity instanceof LivingEntity) {
                            ItemStack copy = itemstack1.copy();
                            itemstack1.hurtEnemy((LivingEntity)entity, player);
                            if (itemstack1.isEmpty()) {
                                ForgeEventFactory.onPlayerDestroyItem(player, copy, InteractionHand.MAIN_HAND);
                                this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                            }
                        }
                        //伤害统计与粒子生成
                        if (p_36347_ instanceof LivingEntity) {
                            float f5 = f4 - ((LivingEntity)p_36347_).getHealth();
                            player.awardStat(Stats.DAMAGE_DEALT, Math.round(f5 * 10.0F));
                            int j1 = EnchantmentHelper.getFireAspect(this);
                            if (j1 > 0) {
                                p_36347_.setSecondsOnFire(j1 * 4);
                            }

                            if (this.level() instanceof ServerLevel && f5 > 2.0F) {
                                int k = (int)((double)f5 * (double)0.5F);
                                ((ServerLevel)this.level()).sendParticles(ParticleTypes.DAMAGE_INDICATOR, p_36347_.getX(), p_36347_.getY((double)0.5F), p_36347_.getZ(), k, 0.1, (double)0.0F, 0.1, 0.2);
                            }
                        }
                        //消耗饥饿值
                        player.causeFoodExhaustion(0.1F);
                    } else {
                        //攻击无效音效
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_NODAMAGE, player.getSoundSource(), 1.0F, 1.0F);
                    }
                }
                //刷新计时器
                player.resetAttackStrengthTicker(); // FORGE: Moved from beginning of attack() so that getAttackStrengthScale() returns an accurate value during all attack events
            }
        }
    }
    public void attackWithExtraDamage(Entity p_36347_,float extraDamage) {
        this.setExtraDamage(extraDamage);
        attack(p_36347_);
        this.setExtraDamage(1);
    }

    //玩家方块挖掘修改
    /**
     * @author TearH_Pi
     * @reason 玩家方块挖掘逻辑修改
     */

    @Overwrite
    public boolean blockActionRestricted(Level p_36188_, BlockPos p_36189_, GameType p_36190_) {
        Player player = (Player)(Object)this;
        /*
        player.getCapability(PlayerMagicProvider.PLAYER_MAGIC_CAPABILITY).ifPresent((magic) -> {
            Component message = Component.literal("test:" + magic.getMagic());
            magic.increase(1,10);
            player.sendSystemMessage(message);
        });
         */
        //player.sendSystemMessage(Component.literal("-------------"));
        if (player.getMainHandItem().is(ModTags.Items.ATTACKABLE)) {
            return true;
        } else
        //是冒险模式,输出true,if中表现为false
        if (!p_36190_.isBlockPlacingRestricted()) {
            return false;
        } else if (p_36190_ == GameType.SPECTATOR) {
            return true;
        } else if (player.mayBuild()) {
            return false;
        } else {
            ItemStack itemstack = player.getMainHandItem();
            return itemstack.isEmpty() || !itemstack.hasAdventureModeBreakTagForBlock(p_36188_.registryAccess().registryOrThrow(Registries.BLOCK), new BlockInWorld(p_36188_, p_36189_, false));
        }

    }
    /*
    //tick mixin
    @Inject(method = "tick",at=@At("TAIL"))
    public void tickMixin(CallbackInfo ci) {
        Player player = (Player)(Object)this;
        player.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(mana -> {
            setMana(mana.getMana());
            System.out.println(mana.getMana());
        });
    }
     */
    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionalSaveData_inject(CompoundTag pCompound, CallbackInfo ci) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer)(Object)this;
        iImmortalPlayer.getManaPoint().readAdditionalSaveData(pCompound);
        iImmortalPlayer.getCoin().readAdditionalSaveData(pCompound);
        iImmortalPlayer.getSkill().readAdditionalSaveData(pCompound);
        iImmortalPlayer.getSkillAssemble().readAdditionalSaveData(pCompound);
        iImmortalPlayer.getSkillCD().readAdditionalSaveData(pCompound);
        iImmortalPlayer.getSkillInUse().readAdditionalSaveData(pCompound);
        iImmortalPlayer.getShield().readAdditionalSaveData(pCompound);
        iImmortalPlayer.getBuffAmount().readAdditionalSaveData(pCompound);
        iImmortalPlayer.getChatbox().readAdditionalSaveData(pCompound);
        iImmortalPlayer.getSetting().readAdditionalSaveData(pCompound);
        iImmortalPlayer.getAchievement().readAdditionalSaveData(pCompound);
        iImmortalPlayer.getMainTask().readAdditionalSaveData(pCompound);
        iImmortalPlayer.getNormalAttackEnergy().readAdditionalSaveData(pCompound);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionalSaveData_inject(CompoundTag pCompound, CallbackInfo ci) throws Exception {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer)(Object)this;
        this.manaPoint.addAdditionalSaveData(pCompound,iImmortalPlayer.getManaPoint().get());
        this.Coin.addAdditionalSaveData(pCompound,iImmortalPlayer.getCoin().get());
        this.Skill.addAdditionalSaveData(pCompound,iImmortalPlayer.getSkill().getNumInfo());
        this.SkillAssemble.addAdditionalSaveData(pCompound,iImmortalPlayer.getSkillAssemble().getNumInfo());
        this.SkillCD.addAdditionalSaveData(pCompound,iImmortalPlayer.getSkillCD().getNumInfo());
        this.SkillInUse.addAdditionalSaveData(pCompound,iImmortalPlayer.getSkillInUse().getNumInfo());
        this.shield.addAdditionalSaveData(pCompound,iImmortalPlayer.getShield().get());
        this.buffAmount.addAdditionalSaveData(pCompound,iImmortalPlayer.getBuffAmount().get());
        this.chatBox.addAdditionalSaveData(pCompound,iImmortalPlayer.getChatbox().getID(),iImmortalPlayer.getChatbox().getNum(),iImmortalPlayer.getChatbox().getCountdown());
        this.setting.addAdditionalSaveData(pCompound,iImmortalPlayer.getSetting().getNumInfo());
        this.achievement.addAdditionalSaveData(pCompound,iImmortalPlayer.getAchievement().getNumInfo());
        this.mainTask.addAdditionalSaveData(pCompound,iImmortalPlayer.getMainTask().get());
        this.normalAttackEnergy.addAdditionalSaveData(pCompound,iImmortalPlayer.getNormalAttackEnergy().get());
        //this.manaPoint.addAdditionalSaveData(pCompound,this.manaPoint.get());
        //this.Coin.addAdditionalSaveData(pCompound,this.Coin.get());
    }

    //玩家受伤逻辑修改
    //玩家UI渲染修改

    //其余渲染

    //受伤测试

    @Override
    public Coin getCoin() {
        return this.Coin;
    }
    @Override
    public ManaPoint getManaPoint() {
        return this.manaPoint;
    }
    @Override
    public Skill getSkill(){
        return this.Skill;
    }
    @Override
    public SkillAssemble getSkillAssemble(){
        return this.SkillAssemble;
    }
    @Override
    public SkillCD getSkillCD(){
        try {
            return this.SkillCD;
        } catch (Exception e) {
            System.out.println("Error in SkillCD");
        }
        return null;
    }
    @Override
    public SkillInUse getSkillInUse(){
        return this.SkillInUse;
    }
    @Override
    public void setExtraDamage(float value){
        this.extraDamage = value;
    }
    @Override
    public float getExtraDamage(){
        return this.extraDamage;
    }
    @Override
    public void setShield(Shield value){
        this.shield = value;
    }
    @Override
    public Shield getShield(){
        return this.shield;
    }
    @Override
    public void setBuffAmount(BuffAmount value){
        this.buffAmount = value;
    }
    @Override
    public BuffAmount getBuffAmount(){
        return this.buffAmount;
    }
    @Override
    public void setChatbox(Chatbox chatBox){
        this.chatBox = chatBox;
    }
    @Override
    public Chatbox getChatbox(){
        return this.chatBox;
    }
    @Override
    public void setChatboxPT(float i){
        this.ChatboxPT = i;
    }
    @Override
    public float getChatboxPT(){return this.ChatboxPT;}
    @Override
    public void setSetting(Setting setting){
        this.setting = setting;
    }
    @Override
    public Setting getSetting(){
        return this.setting;
    }
    @Override
    public void setAchievement(Achievement achievement){
        this.achievement = achievement;
    }
    @Override
    public Achievement getAchievement(){
        return this.achievement;
    }
    @Override
    public void setMainTask(int i){
        this.mainTask.set(i);
    }
    @Override
    public MainTask getMainTask(){
        return this.mainTask;
    }
    @Override
    public NormalAttackEnergy getNormalAttackEnergy(){
        return this.normalAttackEnergy;
    }
}
