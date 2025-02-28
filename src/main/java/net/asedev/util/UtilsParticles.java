package net.asedev.util;

import net.minecraft.util.RandomSource;

/**
 * @Author = ASEStefan
 */
public class UtilsParticles
{
    public static double getDownwardsSmallSpread(RandomSource random)
    {
        return -(random.nextDouble() - 0.5D) * 0.05D;
    }
    public static double getDownwardsMediumSpread(RandomSource random)
    {
        return -(random.nextDouble() - 0.5D) * 0.1D;
    }
    public static double getDownwardsLargeSpread(RandomSource random)
    {
        return -(random.nextDouble() - 0.5D) * 0.15D;
    }
    public static double getUpwardsSmallSpread(RandomSource random)
    {
        return getDownwardsSmallSpread(random) * (-1);
    }
    public static double getUpwardsMediumSpread(RandomSource random)
    {
        return getDownwardsMediumSpread(random) * (-1);
    }
    public static double getUpwardsLargeSpread(RandomSource random)
    {
        return getDownwardsLargeSpread(random) * (-1);
    }

    public static double xSphereSpeed(double t)
    {
        return UtilsMath.sin((float) (t / 10 * UtilsMath.PI));
    }

    public static double ySphereSpeed(double t)
    {
        return UtilsMath.sin((float) ((t % 10) * UtilsMath.PI)) * UtilsMath.cos((float) (t / 10 * UtilsMath.PI));
    }

    public static double zSphereSpeed(double t)
    {
        return UtilsMath.cos((float) ((t % 10) * UtilsMath.PI)) * UtilsMath.cos((float) (t / 10 * UtilsMath.PI));
    }
}
