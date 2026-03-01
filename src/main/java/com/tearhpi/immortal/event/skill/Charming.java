package com.tearhpi.immortal.event.skill;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.SkillAssembleInfoSyncToClientPacket;
import com.tearhpi.immortal.networking.SkillInUseSyncToClientPacket;
import com.tearhpi.immortal.networking.SkillInfoSyncToClientPacket;
import com.tearhpi.immortal.skill.behaviour.active.fire.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = Immortal.MODID,bus=Mod.EventBusSubscriber.Bus.FORGE)
public class Charming {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) return;
        if (!(event.player instanceof ServerPlayer sp)) return;
        //服务端操作
        //减少玩家charming值(最低-1)，charming值归零时放技能
        Player player = event.player;
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        int charmingvalue = iImmortalPlayer.getSkillInUse().IsInUseSkill;
        if (charmingvalue == -1) return;
        else {
            iImmortalPlayer.getSkillInUse().IsInUseSkill -= 1;
            int skillvalue = iImmortalPlayer.getSkillInUse().IsInUseSkillNumber;
            if (charmingvalue == 0) {
                //释放对应技能的技能体
                if (skillvalue == 6) {
                    Skill6.Do((ServerPlayer) player);
                } else if (skillvalue == 7) {
                    Skill7.Do((ServerPlayer) player);
                } else if (skillvalue == 8) {
                    Skill8.Do((ServerPlayer) player);
                } else if (skillvalue == 10) {
                    Skill10.Do((ServerPlayer) player);
                } else if (skillvalue == 11) {
                    Skill11.Do((ServerPlayer) player);
                } else if (skillvalue == 13) {
                    Skill13.Do((ServerPlayer) player);
                } else if (skillvalue == 15) {
                    Skill15.Do((ServerPlayer) player);
                } else if (skillvalue == 17) {
                    Skill17.Do((ServerPlayer) player);
                } else if (skillvalue == 19) {
                    Skill19.Do((ServerPlayer) player);
                } else if (skillvalue == 20) {
                    Skill20.Do((ServerPlayer) player);
                } else if (skillvalue == 22) {
                    Skill22.Do((ServerPlayer) player);
                } else if (skillvalue == 23) {
                    Skill23.Do((ServerPlayer) player);
                } else if (skillvalue == 24) {
                    Skill24.Do((ServerPlayer) player);
                } else if (skillvalue == 26) {
                    Skill26.Do((ServerPlayer) player);
                } else if (skillvalue == 28) {
                    Skill28.Do((ServerPlayer) player);
                } else if (skillvalue == 29) {
                    Skill29.Do((ServerPlayer) player);
                } else if (skillvalue == 30) {
                    Skill30.Do((ServerPlayer) player);
                } else if (skillvalue == 31) {
                    Skill31.Do((ServerPlayer) player);
                } else if (skillvalue == 32) {
                    Skill32.Do((ServerPlayer) player);
                } else if (skillvalue == 33) {
                    Skill33.Do((ServerPlayer) player);
                } else if (skillvalue == 34) {
                    Skill34.Do((ServerPlayer) player);
                } else if (skillvalue == 35) {
                    Skill35.Do((ServerPlayer) player);
                } else if (skillvalue == 37) {
                    Skill37.Do((ServerPlayer) player);
                } else if (skillvalue == 39) {
                    Skill39.Do((ServerPlayer) player);
                } else if (skillvalue == 40) {
                    Skill40.Do((ServerPlayer) player);
                } else if (skillvalue == 41) {
                    Skill41.Do((ServerPlayer) player);
                } else if (skillvalue == 43) {
                    Skill43.Do((ServerPlayer) player);
                } else if (skillvalue == 44) {
                    Skill44.Do((ServerPlayer) player);
                } else if (skillvalue == 45) {
                    Skill45.Do((ServerPlayer) player);
                } else if (skillvalue == 46) {
                    Skill46.Do((ServerPlayer) player);
                } else if (skillvalue == 48) {
                    Skill48.Do((ServerPlayer) player);
                } else if (skillvalue == 50) {
                    Skill50.Do((ServerPlayer) player);
                } else if (skillvalue == 51) {
                    Skill51.Do((ServerPlayer) player);
                } else if (skillvalue == 52) {
                    Skill52.Do((ServerPlayer) player);
                } else if (skillvalue == 53) {
                    Skill53.Do((ServerPlayer) player);
                } else if (skillvalue == 54) {
                    Skill54.Do((ServerPlayer) player);
                } else if (skillvalue == 55) {
                    Skill55.Do((ServerPlayer) player);
                } else if (skillvalue == 56) {
                    Skill56.Do((ServerPlayer) player);
                } else if (skillvalue == 57) {
                    Skill57.Do((ServerPlayer) player);
                } else if (skillvalue == 59) {
                    Skill59.Do((ServerPlayer) player);
                } else if (skillvalue == 61) {
                    Skill61.Do((ServerPlayer) player);
                } else if (skillvalue == 62) {
                    Skill62.Do((ServerPlayer) player);
                } else if (skillvalue == 63) {
                    Skill63.Do((ServerPlayer) player);
                } else if (skillvalue == 64) {
                    Skill64.Do((ServerPlayer) player);
                } else if (skillvalue == 65) {
                    Skill65.Do((ServerPlayer) player);
                } else if (skillvalue == 66) {
                    Skill66.Do((ServerPlayer) player);
                } else if (skillvalue == 69) {
                    Skill69.Do((ServerPlayer) player);
                } else if (skillvalue == 70) {
                    Skill70.Do((ServerPlayer) player);
                }
            }
            if (skillvalue == 42 && charmingvalue == 38) {
                Skill42.Do((ServerPlayer) player);
            }
            //蓄力动画
            if (skillvalue > 0){
                int sv = skillvalue / 10;
                if (sv == 0) {
                    if (skillvalue == 6) {
                        if (charmingvalue == 9) {
                            Skill6.Prep((ServerPlayer) player);
                        } else if (charmingvalue == 2) {
                            Skill6.SoundBefore((ServerPlayer) player);
                        }
                    } else if (skillvalue == 7) {
                        if (charmingvalue == 19) {
                            Skill7.Prep((ServerPlayer) player);
                        }
                    } else if (skillvalue == 8) {
                        if (charmingvalue == 59) {
                            Skill8.Prep((ServerPlayer) player);
                        }
                    }
                } else if (sv == 1) {
                    if (skillvalue == 11) {
                        if (charmingvalue == 79) {
                            Skill11.Prep((ServerPlayer) player);
                        }
                    }
                } else if (sv == 2) {

                } else if (sv == 3) {

                } else if (sv == 4) {

                } else if (sv == 5) {

                } else if (sv == 6) {

                } else if (sv == 7) {

                }
            }
            CompoundTag confirmed = iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo());
            ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),new SkillInUseSyncToClientPacket(confirmed));
        }
    }
}