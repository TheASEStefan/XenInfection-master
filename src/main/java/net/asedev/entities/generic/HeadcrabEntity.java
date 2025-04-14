package net.asedev.entities.generic;

import net.asedev.config.XenInfectionModConfig;
import net.asedev.entities.ai.CustomMeleeAttackGoal;
import net.asedev.entities.ai.EscapeFromVehicleGoal;
import net.asedev.entities.ai.FastSwimmingDiveGoal;
import net.asedev.registry.RegEntities;
import net.asedev.entities.ai.controls.ClimbingMovementControl;
import net.asedev.registry.RegSounds;
import net.asedev.util.UtilsMath;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;

/**
 * @Author = ASEStefan
 */
public class HeadcrabEntity extends Monster implements GeoEntity
{

    private static final EntityDataAccessor<Boolean> LATCHED = SynchedEntityData.defineId(HeadcrabEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_LEAPING = SynchedEntityData.defineId(HeadcrabEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean playedBiteSound = false;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public HeadcrabEntity(EntityType<? extends Monster> pEntityType, Level pLevel)
    {
        super(pEntityType, pLevel);
        this.moveControl = new ClimbingMovementControl(this);
        this.navigation = new WallClimberNavigation(this, pLevel);
        this.setMaxUpStep(0.5F);
        this.xpReward = 5;
        RegEntities.XEN_SPECIES.add(this);
    }

    @Override
    public void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(LATCHED, false);
        this.entityData.define(IS_LEAPING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt)
    {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("isLatched", this.isLatched());
        nbt.putBoolean("isLeaping", this.isLeaping());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt)
    {
        super.readAdditionalSaveData(nbt);
        this.setLatched(nbt.getBoolean("isLatched"));
        this.setLeaping(nbt.getBoolean("isLeaping"));
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource)
    {
        return false;
    }

    @Override
    public int getAirSupply()
    {
        return super.getMaxAirSupply();
    }

    @Override
    public boolean canDrownInFluidType(FluidType type)
    {
        return false;
    }

    @Override
    public int getAmbientSoundInterval()
    {
        return 45 + random.nextInt(15);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D)
                .add(Attributes.MAX_HEALTH, XenInfectionModConfig.SERVER.headcrab_health.get())
                .add(Attributes.ATTACK_DAMAGE, XenInfectionModConfig.SERVER.headcrab_damage.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ATTACK_SPEED, 0.8D);
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new CustomMeleeAttackGoal(this, 1.2, false)
        {
            @Override
            protected double getAttackReachSqr(LivingEntity entity)
            {
                return 1.5 + entity.getBbWidth() * entity.getBbWidth();
            }
        });

        this.goalSelector.addGoal(5, new FastSwimmingDiveGoal(this, 0.65F));

        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.8)
        {
            @Override
            public boolean canUse()
            {
                return super.canUse() && this.mob.getTarget() == null;
            }

            @Override
            public boolean canContinueToUse()
            {
                return super.canContinueToUse() && this.mob.getTarget() == null;
            }
        });

        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, this::targetPredicate));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(HeadcrabEntity.class));
        this.goalSelector.addGoal(6, new HeadcrabLatchGoal(this));
        this.goalSelector.addGoal(1, new HeadcrabJumpGoal(this, 0.65F));
        this.goalSelector.addGoal(4, new EscapeFromVehicleGoal(this));
    }

    private boolean targetPredicate(LivingEntity liv)
    {
        return !(RegEntities.XEN_SPECIES.contains(liv) || liv instanceof Squid || liv instanceof ArmorStand || liv instanceof AbstractFish || liv instanceof Bat || XenInfectionModConfig.SERVER.blacklist.get().contains(liv.getEncodeId()));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerV)
    {
        controllerV.add(
                new AnimationController<>(this, "controllerV", 7, event ->
                {
                    if (!event.isMoving() && !this.isAggressive() && this.onGround())
                    {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("animation.standard_headcrab.idle"));
                    }

                    if (event.isMoving() && this.onGround())
                    {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("animation.standard_headcrab.walk"));
                    }

                    if (this.isInWater() && event.isMoving())
                    {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation.standard_headcrab.swim"));
                    }

                    if (this.isLeaping() && !this.isLatched())
                    {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation.standard_headcrab.jump"));
                    }

                    if (this.isLatched() || this.isPassenger())
                    {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation.standard_headcrab.latch"));
                    }

                    return PlayState.CONTINUE;
                }));

    }

    public boolean isLatched()
    {
        return this.entityData.get(LATCHED);
    }

    public void setLatched(boolean latched)
    {
        this.entityData.set(LATCHED, latched);
    }

    public boolean isLeaping()
    {
        return this.entityData.get(IS_LEAPING);
    }

    public void setLeaping(boolean leaping)
    {
        this.entityData.set(IS_LEAPING, leaping);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache()
    {
        return cache;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_)
    {
        return RegSounds.HEADCRAB_PAIN.get();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return RegSounds.HEADCRAB_DIE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound()
    {
        return RegSounds.HEADCRAB_IDLE.get();
    }

    @Override
    public void tick()
    {
        super.tick();

        if (!level().isClientSide)
            this.removeEffect(MobEffects.POISON);


        if (this.onGround())
        {
            this.setLatched(false);
        }

        LivingEntity target = this.getTarget();
        if (target != null)
        {
            if (target.hasPassenger(this))
            {
                if (this.random.nextInt(7) == 0)
                {
                    target.hurt(this.damageSources().mobAttack(this), 4.0F);
                    this.heal(0.5F);

                    if (this.isOnFire())
                        this.setSecondsOnFire(5);

                    if (target instanceof Player player)
                        player.causeFoodExhaustion(1.5F);
                }

                if (!this.playedBiteSound)
                {
                    this.level().playSound(null, this.blockPosition(), RegSounds.HEADCRAB_HEADBITE.get(), SoundSource.HOSTILE, this.getSoundVolume() + 0.2F, this.getVoicePitch());
                    this.playedBiteSound = true;
                }
            }
            else
            {
                if (this.playedBiteSound) this.playedBiteSound = false;
            }
        }



    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount)
    {
        if (this.isAttachedToHost() && Math.random() <= 0.75F)
        {
            this.detachFromHost();
        }

        return super.hurt(pSource, pAmount);
    }

    public void detachFromHost()
    {
        this.setLatched(false);
        this.unRide();
    }

    public boolean isAttachedToHost()
    {
        return this.getVehicle() instanceof LivingEntity && this.isLatched();
    }

    public void grabTarget(LivingEntity entity)
    {
        this.setLatched(true);
        this.startRiding(entity, true);
        this.setAggressive(false);
        entity.yBodyRot = this.yBodyRot;
        entity.xxa = 0;
        entity.zza = 0;
        entity.yya = 0;
        entity.yBodyRot = -90F;
        entity.setSpeed(0.0f);
        if (entity instanceof ServerPlayer player && (!player.isCreative() || !player.isSpectator()))
            player.connection.send(new ClientboundSetPassengersPacket(entity));
    }


    public static class HeadcrabJumpGoal extends Goal
    {
        private final HeadcrabEntity mob;
        private LivingEntity target;
        private final float yd;

        public HeadcrabJumpGoal(HeadcrabEntity mob, float v)
        {
            this.mob = mob;
            this.yd = v;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        public boolean canUse()
        {
            this.target = this.mob.getTarget();
            if (this.target == null)
            {
                return false;
            }
            else if (this.mob.isInWater())
            {
                return false;
            }
            else
            {
                double d0 = this.mob.distanceTo(this.target);
                if (d0 >= 2.5D && d0 < 16.0D)
                {
                    if (!this.mob.onGround())
                    {
                        return false;
                    }
                    else
                    {
                        return this.mob.getRandom().nextInt(reducedTickDelay(3)) == 0 && !this.mob.isLatched() && !this.mob.isPassenger();
                    }
                }
                else
                {
                    return false;
                }
            }

        }


        @Override
        public void tick()
        {
            if (this.mob.getTarget() != null)
            {
                double e = target.getX() - this.mob.getX();
                double f = target.getZ() - this.mob.getZ();
                this.mob.setYRot(-((float) Math.atan2(e, f)) * 57.295776F);
                this.mob.yBodyRot = this.mob.getYRot();
                this.mob.setLeaping(true);
            }
        }

        public void start()
        {
            this.mob.setLeaping(true);
            this.mob.level().playSound(null, this.mob.blockPosition(), RegSounds.HEADCRAB_ATTACK.get(), SoundSource.HOSTILE, 1.3F, 1.0F);
            this.mob.setAggressive(true);
            Vec3 vec3 = this.mob.getDeltaMovement();
            Vec3 vec31 = new Vec3(this.target.getX() - this.mob.getX(), this.target.getY() - this.mob.getY(), this.target.getZ() - this.mob.getZ());
            if (vec31.lengthSqr() > 1.0E-7D)
            {
                vec31 = vec31.normalize().scale(2D).add(vec3.scale(1.5D));
            }

            // this.mob.setDeltaMovement(vec31.x + yd * (UtilsMath.PI * 2 / 156), this.yd + ((double) UtilsMath.APPROX_PI / (UtilsMath.toSquare(3) << 1)), vec31.z + yd);
            this.mob.setDeltaMovement(vec31.x + yd * (UtilsMath.PI * 2 / 156), vec31.y + yd * 0.75F, vec31.z + yd);
        }

        @Override
        public void stop()
        {
            Vec3 $$0 = DefaultRandomPos.getPos(this.mob, 5, 4);
            if ($$0 != null)
            {
                this.mob.getNavigation().moveTo($$0.x, $$0.y, $$0.z, this.yd * 3F);
            }
            this.mob.setAggressive(false);
            this.mob.setLeaping(false);
            super.stop();
        }
    }


    public class HeadcrabLatchGoal extends Goal
    {
        private final HeadcrabEntity headcrabEntity;

        public HeadcrabLatchGoal(HeadcrabEntity headcrabEntity)
        {
            this.headcrabEntity = headcrabEntity;
        }

        public boolean isValidTarget()
        {
            Entity attackTarget = this.headcrabEntity.getTarget();
            return attackTarget != null && this.headcrabEntity.distanceTo(attackTarget) < 2.0F;
        }

        @Override
        public boolean canUse()
        {
            return this.headcrabEntity.getTarget() != null && this.headcrabEntity.isAlive() && !this.headcrabEntity.isVehicle() && this.headcrabEntity.getRandom().nextInt(5) == 0 && this.isValidTarget();
        }

        @Override
        public void tick()
        {
            super.tick();
            if (this.headcrabEntity.getTarget() != null && this.isValidTarget())
            {
                grabTarget(this.headcrabEntity.getTarget());
            }
        }

        @Override
        public void stop()
        {
            this.headcrabEntity.setLatched(false);
            super.stop();
        }
    }

}
