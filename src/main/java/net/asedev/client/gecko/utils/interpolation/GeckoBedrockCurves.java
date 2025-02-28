package net.asedev.client.gecko.utils.interpolation;

/**
 * @Author = ASEStefan, made with internet help.
 * @Disclaimer = Custom Catmullrom splines used for animation purposes...
 */
public class GeckoBedrockCurves
{
    /**
     * Interpolates using a Catmull-Rom spline smooth curve.
     *
     * @param t  The time parameter, ranging from 0 to 1.
     * @param start The starting value (control point 0).
     * @param end The ending value (control point 1).
     * @return The interpolated value.
     */
    public static double smooth(double start, double end, double t)
    {
        // Clamp t to the range [0, 1]
        t = Math.max(0, Math.min(1, t));
        double min = 0.0;
        double max = 1.0;
        double a = -0.5 * min + 1.5 * start - 1.5 * end + 0.5 * max;
        double b = min - 2.5 * start + 2.0 * end - 0.5 * max;
        double c = -0.5 * min + 0.5 * end;
        double d = start;
        return a * t * t * t + b * t * t + c * t + d;
    }

    /**
     * Interpolates using a Bezier curve.
     *
     * @param t  The time parameter, ranging from 0 to 1.
     * @param start The starting value (control point 0).
     * @param end The ending value (control point 1).
     * @return The interpolated value.
     */
    public static double bezier(double start, double end, double t)
    {
        // Clamp t to the range [0, 1]
        t = Math.max(0, Math.min(1, t));
        double p0 = start; // Start point
        double p1 = (start + end) / 2.0; // Control point
        double p2 = end; // End point
        double oneMinusT = 1.0 - t;
        return oneMinusT * oneMinusT * p0 + 2 * oneMinusT * t * p1 + t * t * p2;
    }

    /**
     * Interpolates using a Step curve.
     *
     * @param t  The time parameter, ranging from 0 to 1.
     * @param start The starting value (control point 0).
     * @param end The ending value (control point 1).
     * @return The interpolated value.
     */
    public static double step(double start, double end, double t)
    {
        // Clamp t to the range [0, 1]
        t = Math.max(0, Math.min(1, t));
        // Step curve: return starting value if t < 0.5, otherwise return ending value
        return t < 0.5 ? start : end;
    }
}
