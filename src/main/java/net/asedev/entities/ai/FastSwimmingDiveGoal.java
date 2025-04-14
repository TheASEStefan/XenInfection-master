package net.asedev.entities.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;

/**
 * @Source, Author = <a href="https://github.com/FakeACat/FromAnotherWorld-Architectury/blob/a484e87f1efe58898af1072e1c2ab811dfea5ecb/common/src/main/java/mod/acats/fromanotherworld/entity/goal/ImprovedSwimGoal.java#L8">...</a>
 */
public class FastSwimmingDiveGoal extends FloatGoal
{

    private final Mob mob;
    private final float speed;
    public FastSwimmingDiveGoal(Mob mob, float speed)
    {
        super(mob);
        this.mob = mob;
        this.speed = speed;
    }

    @Override
    public void tick()
    {
        LivingEntity target = this.mob.getTarget();

        if (this.mob.isInLava() || target == null)
        {
            if (this.mob.getRandom().nextFloat() < 0.45F)
            {
                super.tick();
            }
        }

        else
        {
            float maxSpeed = speed * 1.6F;
            if (this.mob.isUnderWater()) this.mob.setDeltaMovement(target.position().add(0, target.getBbHeight() / 2, 0).subtract(this.mob.position()).normalize().scale(mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * speed));
            else this.mob.setDeltaMovement(target.position().add(0, target.getBbHeight() / 2, 0).subtract(this.mob.position()).normalize().scale(mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * maxSpeed));

            this.mob.getNavigation().stop();
            this.mob.lookAt(target, 10, 10);
        }
    }


}
