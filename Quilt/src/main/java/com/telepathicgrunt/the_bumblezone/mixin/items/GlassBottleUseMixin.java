package com.telepathicgrunt.the_bumblezone.mixin.items;

import com.telepathicgrunt.the_bumblezone.items.GlassBottleBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(BottleItem.class)
public class GlassBottleUseMixin {
    //using glass bottle to get honey could anger bees
    @Inject(method = "use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BottleItem;turnBottleIntoItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;", ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILSOFT,
            cancellable = true)
    private void thebumblezone_bottleFluidInteract(Level world, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, List<AreaEffectCloud> list, ItemStack itemStack, HitResult hitResult, BlockPos blockPos) {
        if (GlassBottleBehavior.useBottleOnSugarWater(world, user, hand, blockPos))
            cir.setReturnValue(InteractionResultHolder.success(user.getItemInHand(hand)));
    }

    //using glass bottle to get honey could anger bees
    @Inject(method = "use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;", ordinal = 0, shift = At.Shift.BEFORE),
            locals = LocalCapture.CAPTURE_FAILSOFT,
            cancellable = true)
    private void thebumblezone_bottleFluidInteract2(Level world, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, List<AreaEffectCloud> list, ItemStack itemStack, HitResult hitResult, BlockPos blockPos) {
        if (GlassBottleBehavior.useBottleOnHoneyFluid(world, user, hand, blockPos))
            cir.setReturnValue(InteractionResultHolder.success(user.getItemInHand(hand)));
        if (GlassBottleBehavior.useBottleOnRoyalJellyFluid(world, user, hand, blockPos))
            cir.setReturnValue(InteractionResultHolder.success(user.getItemInHand(hand)));
    }
}