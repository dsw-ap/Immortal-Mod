package com.tearhpi.immortal.entity.custom.zSpecialWandBullet;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom.WandNormalAttackBullet;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.NormalAttackEnergySyncToClientPacket;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.sound.ModSounds;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import java.util.*;

public class WandBullet1_1 extends WandNormalAttackBullet {
    private final Set<UUID> hit = new HashSet<>();

    public WandBullet1_1(EntityType<? extends WandNormalAttackBullet> type, Level level) {
        super(type, level);
    }
    // 便捷创建
    public static WandNormalAttackBullet spawn(ServerLevel level, ServerPlayer owner, int weaponAttributeAttack, Vec3 pos, Vec3 motion) {
        WandBullet1_1 e = new WandBullet1_1(ModEntityTypes.WAND_BULLET_1_1.get(), level);
        owner.playNotifySound(ModSounds.NORMAL_ATTACK.get(), SoundSource.PLAYERS,1.0f,1.0f);
        e.setWeaponAttribute(weaponAttributeAttack);
        e.setPos(pos);
        e.setDeltaMovement(motion);
        e.ownerId = owner.getUUID();
        level.addFreshEntity(e);
        return e;
    }
    @Override
    public void tick() {
        //客户端 新增粒子
        if (this.level().isClientSide) {
            int attr = this.getWeaponAttribute();
            if (attr == 1) {
                level().addParticle(ModParticles.WandNormalAttack_Fire.get(), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
                level().addParticle(ModParticles.WandNormalAttack_Fire.get(), this.getX()+this.getDeltaMovement().x/2, this.getY()+this.getDeltaMovement().y/2, this.getZ()+this.getDeltaMovement().z/2, 0.0D, 0.0D, 0.0D);
            }
            return;
        }
        //服务端 运行逻辑
        // 移动
        Vec3 v = this.getDeltaMovement();
        this.setPos(this.getX() + v.x, this.getY() + v.y, this.getZ() + v.z);
        // 碰墙消失
        if (isInsideSolid()) {
            //this.discard();
            return;
        }
        // 查找附近 1 格 Mob
        AABB box = this.getBoundingBox().inflate(0.2);
        List<_ImmortalMob> mobs = ((ServerLevel)this.level()).getEntitiesOfClass(_ImmortalMob.class, box,
                m -> m.isAlive() && !m.isRemoved() && !hit.contains(m.getUUID()));
        if (!mobs.isEmpty()) {
            ServerPlayer owner = getOwner();
            if (owner != null) {
                // 任选最近的
                _ImmortalMob target = null;
                double best = Double.MAX_VALUE;
                for (_ImmortalMob m : mobs) {
                    double d = m.distanceToSqr(this);
                    if (d < best) { best = d; target = m; }
                }
                if (target != null && hit.add(target.getUUID())) {
                    // 造成伤害
                    ModDamageSources modDamageSources = new ModDamageSources(owner.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE, owner);
                    target.hurt(damagesource, MainDamage.getDamage(owner, damagesource, OutPutDamageInfo.getOutPutDamageInfo(target), 1.2f-hit.size()*0.2f));
                    if (hit.size() == 1) {
                        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer)owner;
                        iImmortalPlayer.getNormalAttackEnergy().add(6);
                        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> owner),new NormalAttackEnergySyncToClientPacket(iImmortalPlayer.getNormalAttackEnergy().get()));
                        //特效
                        SpecialEffect(owner,target);
                    }
                }
            }
            if (hit.size() >= 5) {
                this.discard();
            }
            return;
        }

        // 寿命
        if (++life >= LIFE_TICKS) {
            this.discard();
        }
    }
}
