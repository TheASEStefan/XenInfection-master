package net.asedev.client.model;

import net.asedev.entities.special.XenPortal;
import net.asedev.xenmod.Embedded;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class XenPortalModel extends GeoModel<XenPortal>
{

    @Override
    public ResourceLocation getModelResource(XenPortal object)
    {
        return new ResourceLocation(Embedded.MOD_ID, "geo/xen_portal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(XenPortal object)
    {
        int frame = object.getPortalFrame();
        if (frame == 0)
            frame = 1;

        return new ResourceLocation(Embedded.MOD_ID, "textures/entity/xen_portal_frames/frame_" + frame + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(XenPortal animatable)
    {
        return new ResourceLocation(Embedded.MOD_ID, "animations/xen_portal.animation.json");
    }

}
