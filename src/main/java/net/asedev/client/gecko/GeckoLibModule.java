package net.asedev.client.gecko;

import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;

/**
 * @Author = ASEStefan
 */
public class GeckoLibModule
{

    /** Creates and loads an animation file. */
    public static AnimationFile createAnimationFile(String animatableName)
    {
        return new AnimationFile(animatableName);
    }

}
