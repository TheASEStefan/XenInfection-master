package net.asedev.entities.special;

import net.asedev.client.gecko.AnimatedTextureEntity;
import net.asedev.entities.generic.HeadcrabEntity;
import net.asedev.registry.RegEntities;
import net.asedev.registry.RegSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class XenPortal extends Entity implements GeoEntity, AnimatedTextureEntity
{
    private static final EntityDataAccessor<Integer> PORTAL_FRAME = SynchedEntityData.defineId(XenPortal.class, EntityDataSerializers.INT);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int frame = 0;

    public XenPortal(EntityType<?> type, Level world)
    {
        super(type, world);
    }

    public XenPortal(Level world, Vec3 position)
    {
        super(RegEntities.XEN_PORTAL.get(), world);
        this.setPos(position.x, position.y, position.z);
    }

    @Override
    protected void defineSynchedData()
    {
        this.entityData.define(PORTAL_FRAME, 1);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound)
    {
        this.setPortalFrame(compound.getInt("portalFrame"));
        this.tickCount = compound.getInt("ticks_existed");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound)
    {
        compound.putInt("portalFrame", this.getPortalFrame());
        compound.putInt("ticks_existed", this.tickCount);
    }

    public int getPortalFrame()
    {
        return this.entityData.get(PORTAL_FRAME);
    }

    public void setPortalFrame(int frame)
    {
        this.entityData.set(PORTAL_FRAME, frame);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public float getFrameTime()
    {
        return 3.0F;
    }

    @Override
    public void tick()
    {
        super.tick();


        if (frame == ((int) this.getFrameTime()))
        {
            this.frame = 0;
            if (this.getPortalFrame() >= 4)
            {
                this.setPortalFrame(1);
            }
            else
            {
                this.setPortalFrame(this.getPortalFrame() + 1);
            }
        }
        ++this.frame;

        if (!this.level().isClientSide())
        {
            if (this.random.nextInt(150) == 0)
            {
                this.spawnHeadcrab();
                this.close();
            }
        }
    }

    private void spawnHeadcrab()
    {
        HeadcrabEntity headcrabEntity = new HeadcrabEntity(RegEntities.HEADCRAB.get(), this.level());
        headcrabEntity.moveTo(this.getX(), this.getY(), this.getZ());
        this.level().addFreshEntity(headcrabEntity);
        headcrabEntity.push(random.nextGaussian() * 0.15F, 0.15F, random.nextGaussian() * 0.15F);
    }

    private void close()
    {
        this.discard();
        this.level().playSound(null, this.blockPosition(), RegSounds.BEAMSTART.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers)
    {
        controllers.add(
                new AnimationController<>(this, "portalController", 5, event ->
                        event.setAndContinue(RawAnimation.begin().thenPlay("animation.xen_portal.default"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache()
    {
        return cache;
    }
}
