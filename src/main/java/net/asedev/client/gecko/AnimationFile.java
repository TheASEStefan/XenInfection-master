package net.asedev.client.gecko;


import net.asedev.xenmod.Embedded;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.GeckoLibException;

import javax.json.Json;
import javax.json.JsonReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author = ASEStefan
 * Helps with creating organized animation files.
 */
@Deprecated
public class AnimationFile
{
    private final String objName;
    private final Map<String, String> animationQuery;

    public AnimationFile(String objectName)
    {
        this.objName = objectName;
        this.animationQuery = new HashMap<>();
    }

    private String bake(String animationName)
    {
        String string = "animation." + this.objName + "." + animationName;
        return string;
    }

    public AnimationFile createAnimation(String animationName)
    {
        String bakedAnimationName = this.bake(animationName);
        this.animationQuery.put(animationName, bakedAnimationName);
        return this;
    }

    @Nullable
    public String getAnimation(String animationName)
    {
        if (this.animationQuery.isEmpty())
            return null;

        if (!this.animationQuery.containsKey(animationName))
        {
            String bakedAnimationName = this.bake(animationName);
            ResourceLocation resourceLocation = new ResourceLocation(Embedded.MOD_ID, bakedAnimationName);
            throw new GeckoLibException(resourceLocation, "is Missing!");
        }
        else
        {
            return this.animationQuery.get(animationName);
        }
    }
}
