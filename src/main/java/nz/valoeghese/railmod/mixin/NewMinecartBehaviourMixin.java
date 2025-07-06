package nz.valoeghese.railmod.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

import net.minecraft.world.entity.vehicle.MinecartBehavior;
import net.minecraft.world.entity.vehicle.NewMinecartBehavior;
import net.minecraft.world.level.block.state.BlockState;
import nz.valoeghese.railmod.MultispeedRail;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NewMinecartBehavior.class)
public abstract class NewMinecartBehaviourMixin extends MinecartBehavior {
    protected NewMinecartBehaviourMixin(AbstractMinecart abstractMinecart) {
        super(abstractMinecart);
    }

    @Unique
    private double railmod$currentMaxSpeed = Double.NaN;

    @Inject(at = @At("RETURN"), method="getMaxSpeed", cancellable = true)
    private void onGetMaxSpeed(ServerLevel level, CallbackInfoReturnable<Double> d) {
        double speed = d.getReturnValueD();
        if (Double.isNaN(railmod$currentMaxSpeed)) {
            railmod$currentMaxSpeed = Math.max(speed, this.minecart.getDeltaMovement().horizontalDistance());
        }

        BlockState below = level.getBlockState(this.minecart.blockPosition().below());
        if (below.is(MultispeedRail.HIGH_SPEED_RAIL_BLOCKS)) {
            speed = MultispeedRail.getHighRailSpeed() * (this.minecart.isInWater() ? 0.5 : 1.0) / 20.0;
        } else if (below.is(MultispeedRail.MEDIUM_SPEED_RAIL_BLOCKS)) {
            speed = MultispeedRail.getMediumRailSpeed() * (this.minecart.isInWater() ? 0.5 : 1.0) / 20.0;
        }

        if (speed < railmod$currentMaxSpeed) {
            // convert m/s to blocks/tick = /20.0
            // convert blocks/tick/s to blocks/tick/tick = /20.0
            // Therefore /400.0
            railmod$currentMaxSpeed = Math.max(speed, railmod$currentMaxSpeed - MultispeedRail.getDeceleration()/400.0);
        } else {
            railmod$currentMaxSpeed = speed;
        }

        d.setReturnValue(railmod$currentMaxSpeed);
    }
}
