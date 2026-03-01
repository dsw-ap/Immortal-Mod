package com.tearhpi.immortal.entity.custom;

import com.tearhpi.immortal.attribute.WeaponAttributeAttack;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.item.custom.WandItem;
import com.tearhpi.immortal.item.custom.Weapon.Weapon_1_1;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.NormalAttackEnergySyncToClientPacket;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class WandNormalAttackBullet extends Entity {
    public static final int LIFE_TICKS = 10; // 1s
    public int life;
    @Nullable
    public UUID ownerId; // 记录施放者
    public static final EntityDataAccessor<Integer> DATA_WEAPON_ATTR =
            SynchedEntityData.defineId(WandNormalAttackBullet.class, EntityDataSerializers.INT);

    public WandNormalAttackBullet(EntityType<? extends WandNormalAttackBullet> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static WandNormalAttackBullet spawn(ServerLevel level, ServerPlayer owner,int weaponAttributeAttack, Vec3 pos, Vec3 motion) {
        WandNormalAttackBullet e = new WandNormalAttackBullet(ModEntityTypes.WAND_NORMAL_ATTACK_BULLET.get(), level);
        owner.playNotifySound(ModSounds.NORMAL_ATTACK.get(), SoundSource.PLAYERS,1.0f,1.0f);
        e.setWeaponAttribute(weaponAttributeAttack);
        e.setPos(pos);
        e.setDeltaMovement(motion);
        e.ownerId = owner.getUUID();
        level.addFreshEntity(e);
        return e;
    }


    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.life = tag.getInt("life");
        if (tag.hasUUID("owner")) this.ownerId = tag.getUUID("owner");
        this.setWeaponAttribute(tag.getInt("wp_attribute"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("life", this.life);
        tag.putInt("wp_attribute",this.getWeaponAttribute());
        if (this.ownerId != null) tag.putUUID("owner", this.ownerId);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_WEAPON_ATTR,0);
    }


    @Override
    public void tick() {
        super.tick();
        //客户端 新增粒子
        if (this.level().isClientSide) {
            int attr = this.getWeaponAttribute();
            if (attr == 1) {
                level().addParticle(ModParticles.WandNormalAttack_Fire.get(), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
                level().addParticle(ModParticles.WandNormalAttack_Fire.get(), this.getX()+this.getDeltaMovement().x/2, this.getY()+this.getDeltaMovement().y/2, this.getZ()+this.getDeltaMovement().z/2, 0.0D, 0.0D, 0.0D);
            } else if (attr == 2) {
                level().addParticle(ModParticles.WandNormalAttack_Water.get(), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
                level().addParticle(ModParticles.WandNormalAttack_Water.get(), this.getX()+this.getDeltaMovement().x/2, this.getY()+this.getDeltaMovement().y/2, this.getZ()+this.getDeltaMovement().z/2, 0.0D, 0.0D, 0.0D);
            } else if (attr == 4) {
                level().addParticle(ModParticles.WandNormalAttack_Earth.get(), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
                level().addParticle(ModParticles.WandNormalAttack_Earth.get(), this.getX()+this.getDeltaMovement().x/2, this.getY()+this.getDeltaMovement().y/2, this.getZ()+this.getDeltaMovement().z/2, 0.0D, 0.0D, 0.0D);
            } else if (attr == 3) {
                level().addParticle(ModParticles.WandNormalAttack_Air.get(), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
                level().addParticle(ModParticles.WandNormalAttack_Air.get(), this.getX()+this.getDeltaMovement().x/2, this.getY()+this.getDeltaMovement().y/2, this.getZ()+this.getDeltaMovement().z/2, 0.0D, 0.0D, 0.0D);
            } else if (attr == 5) {
                level().addParticle(ModParticles.WandNormalAttack_Light.get(), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
                level().addParticle(ModParticles.WandNormalAttack_Light.get(), this.getX()+this.getDeltaMovement().x/2, this.getY()+this.getDeltaMovement().y/2, this.getZ()+this.getDeltaMovement().z/2, 0.0D, 0.0D, 0.0D);
            } else if (attr == 6) {
                level().addParticle(ModParticles.WandNormalAttack_Darkness.get(), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
                level().addParticle(ModParticles.WandNormalAttack_Darkness.get(), this.getX()+this.getDeltaMovement().x/2, this.getY()+this.getDeltaMovement().y/2, this.getZ()+this.getDeltaMovement().z/2, 0.0D, 0.0D, 0.0D);
            } else {
                level().addParticle(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
                level().addParticle(ParticleTypes.CRIT, this.getX()+this.getDeltaMovement().x/2, this.getY()+this.getDeltaMovement().y/2, this.getZ()+this.getDeltaMovement().z/2, 0.0D, 0.0D, 0.0D);
            }
            return;
        }
        //服务端 运行逻辑
        // 移动
        Vec3 v = this.getDeltaMovement();
        this.setPos(this.getX() + v.x, this.getY() + v.y, this.getZ() + v.z);
        // 碰墙消失
        if (isInsideSolid()) {
            this.discard();
            return;
        }
        // 查找附近 1 格 Mob
        AABB box = this.getBoundingBox().inflate(0.2);
        List<_ImmortalMob> mobs = ((ServerLevel)this.level()).getEntitiesOfClass(_ImmortalMob.class, box,
                m -> m.isAlive() && !m.isRemoved());
        if (!mobs.isEmpty()) {
            ServerPlayer owner = getOwner();
            if (owner != null) {
                // 任选最近的
                Mob target = null;
                double best = Double.MAX_VALUE;
                for (Mob m : mobs) {
                    double d = m.distanceToSqr(this);
                    if (d < best) { best = d; target = m; }
                }
                if (target != null) {
                    // 造成伤害
                    owner.attack(target);
                    IImmortalPlayer iImmortalPlayer = (IImmortalPlayer)owner;
                    iImmortalPlayer.getNormalAttackEnergy().add(6);
                    ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> owner),new NormalAttackEnergySyncToClientPacket(iImmortalPlayer.getNormalAttackEnergy().get()));
                    //特效
                    SpecialEffect(owner,target);
                }
            }
            this.discard();
            return;
        }

        // 寿命
        if (++life >= LIFE_TICKS) {
            this.discard();
        }
    }

    @Nullable
    public ServerPlayer getOwner() {
        if (this.ownerId == null) return null;
        if (!(this.level() instanceof ServerLevel sl)) return null;
        return sl.getServer().getPlayerList().getPlayer(this.ownerId);
    }

    public boolean isInsideSolid() {
        BlockPos bp = BlockPos.containing(this.position());
        return this.level().getBlockState(bp).isSolidRender(this.level(), bp);
    }
    public int getWeaponAttribute() {
        return this.entityData.get(DATA_WEAPON_ATTR);
    }
    public void setWeaponAttribute(int v) {
        this.entityData.set(DATA_WEAPON_ATTR, v);
    }
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double max = 256.0;
        return distance < max * max;
    }
    public void SpecialEffect(Player player, Mob mob) {
        if (mob instanceof _ImmortalMob immortalMob) {
            Random rand = new Random();
            IImmortalPlayer iImmortalPlayer = (IImmortalPlayer)player;
            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof Weapon_1_1) {
                int val = rand.nextInt(1,100);
                //if (val <= 30) ImposeEffect.ImposeLayer(immortalMob, _ModEffects.FIRING_EFFECT.get(), 100,0);
            }
        }
    }
}
