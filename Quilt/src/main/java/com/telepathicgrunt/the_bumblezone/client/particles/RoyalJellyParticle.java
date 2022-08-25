package com.telepathicgrunt.the_bumblezone.client.particles;

import com.telepathicgrunt.the_bumblezone.modinit.BzTags;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class RoyalJellyParticle extends SuspendedParticle {
    private RoyalJellyParticle(ClientLevel clientWorld, SpriteSet spriteProvider, double xPos, double yPos, double zPos) {
        super(clientWorld, spriteProvider, xPos, yPos, zPos);
        this.rCol = 0.25F;
        this.gCol = 0.0F;
        this.bCol = 0.45F;
        this.quadSize *= this.random.nextFloat() * 8.5F + 0.5F;
        this.age = (int)(440 * (Math.random() * 0.55D + 0.45D));
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age-- <= 0) {
            this.remove();
        }
        else {
            BlockState blockState = this.level.getBlockState(new BlockPos(this.x, this.y, this.z));
            FluidState fluidState = blockState.getFluidState();
            if (fluidState.is(BzTags.ROYAL_JELLY_FLUID)) {
                if(fluidState.isSource()) {
                    this.move(this.xd, this.yd + 0.001D, this.zd);
                }
                else {
                    this.move(this.xd, this.yd - 0.001D, this.zd);
                }
            }
            else if (blockState.isAir()) {
                this.move(this.xd, this.yd - 0.35D, this.zd);
            }
            else {
                this.remove();
            }
        }
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Factory(SpriteSet sprite) {
            this.sprites = sprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel clientWorld, double xPos, double yPos, double zPos, double xSpeed, double ySpeed, double zSpeed) {
            RoyalJellyParticle royalJellyParticle = new RoyalJellyParticle(clientWorld, sprites, xPos, yPos, zPos);
            royalJellyParticle.pickSprite(this.sprites);
            return royalJellyParticle;
        }
    }
}