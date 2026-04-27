package com.modforge.commandrules.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.modforge.commandrules.CommandRulesMod;

@Mixin(ItemEntity.class)
public class CommandRulesModMixin {
    @Inject(method = "onPlayerCollision", at = @At("HEAD"))
    private void modforge_multiplyOnPickup(PlayerEntity player, CallbackInfo ci) {
        try {
            ItemEntity self = (ItemEntity) (Object) this;
            if (self.getEntityWorld().isClient()) return;
            ItemStack stack = self.getStack();
            if (stack.isEmpty()) return;
            Entity src = self.getOwner();
            boolean fromMob = src instanceof LivingEntity && !(src instanceof PlayerEntity);
            int mult = fromMob
                ? (CommandRulesMod.MOB_DROP_ENABLED ? CommandRulesMod.MOB_DROP_MULTIPLIER : 1)
                : (CommandRulesMod.BLOCK_DROP_ENABLED ? CommandRulesMod.BLOCK_DROP_MULTIPLIER : 1);
            if (mult <= 1) return;
            int newCount = Math.min(stack.getCount() * mult, stack.getMaxCount() * mult);
            stack.setCount(newCount);
        } catch (Throwable ignored) {}
    }
}
