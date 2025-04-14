package net.asedev.client.model;

import net.asedev.entities.generic.HeadcrabEntity;
import net.asedev.entities.special.XenPortal;
import net.asedev.xenmod.Embedded;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HeadcrabModel extends GeoModel<HeadcrabEntity>
{

    @Override
    public ResourceLocation getModelResource(HeadcrabEntity object)
    {
        return new ResourceLocation(Embedded.MOD_ID, "geo/headcrab.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HeadcrabEntity object)
    {
        return new ResourceLocation(Embedded.MOD_ID, "textures/entity/headcrab.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HeadcrabEntity animatable)
    {
        return new ResourceLocation(Embedded.MOD_ID, "animations/headcrab.animation.json");
    }

}
