package com.tearhpi.immortal.mixin;

import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.networking.ChatboxSyncPacket;
import com.tearhpi.immortal.networking.ModNetworking;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.Input;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//修改玩家运动逻辑以使"混乱"药水效果生效
@Mixin(KeyboardInput.class)
public class KeyBoardInputMixin extends Input {

    @Shadow @Final
    private Options options;
    //ChatBox拦截
    @Inject(method = "tick(ZF)V", at = @At("HEAD"), cancellable = true)
    private void immortal$blockMovement(boolean isSlow, float slowFactor, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (this.options == null) return;
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        if (iImmortalPlayer != null && iImmortalPlayer.getChatbox().Chatbox_id != -1) {
            KeyboardInput self = (KeyboardInput)(Object)this;
            if (this.options.keyJump.isDown() && iImmortalPlayer.getChatbox().Chatbox_countdown <= 0) {
                iImmortalPlayer.getChatbox().Chatbox_num += 1;
                iImmortalPlayer.getChatbox().Chatbox_countdown = 5;
                ModNetworking.CHANNEL.sendToServer(new ChatboxSyncPacket(iImmortalPlayer.getChatbox().Chatbox_id,iImmortalPlayer.getChatbox().Chatbox_num,iImmortalPlayer.getChatbox().Chatbox_countdown));
            }
            self.leftImpulse = 0.0F;
            self.forwardImpulse = 0.0F;
            self.jumping = false;
            self.shiftKeyDown = false;
            // 取消原版 tick：不再从 KeyMapping 计算 impulse
            ci.cancel();
        }
    }
    //混乱药水效果
    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(boolean p_234118_, float p_234119_, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        if (this.options == null) return;
        if (minecraft.player != null && minecraft.player.hasEffect(_ModEffects.CHAOS_EFFECT.get())) {
            this.forwardImpulse = -this.forwardImpulse;
            this.leftImpulse = -this.leftImpulse;
            this.jumping = this.options.keyShift.isDown();
            this.shiftKeyDown = this.options.keyJump.isDown();
        }
    }
}