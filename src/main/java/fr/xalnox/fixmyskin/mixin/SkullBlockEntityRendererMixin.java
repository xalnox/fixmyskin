package fr.xalnox.fixmyskin.mixin;

import fr.xalnox.fixmyskin.utils.SkinAPIUtils;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.client.render.entity.model.SkullEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.io.IOException;

@Mixin(SkullBlockEntityRenderer.class)
public class SkullBlockEntityRendererMixin {


    @Shadow private SkullEntityModel model;
    @Shadow  private SkullEntityModel field_5226;

    @ModifyVariable(method = "method_4363", at = @At(value = "STORE"), ordinal = 0)
    public SkullEntityModel onModelSelection(SkullEntityModel value, float f, float g, float h, int i, float j, int k) {
        // Change the model at the assignment
        return k == 3 || k == 2 ? this.field_5226 : this.model;
    }



    @ModifyVariable(method = "method_4363", at = @At("STORE"), ordinal = 1)
    public String setSkin(String value) {
        try {
            String[] newValue = value.split("/|\\.png");
            return SkinAPIUtils.getSkinCapeURL(
                            SkinAPIUtils.getUUIDFromUsername(
                                    newValue[newValue.length - 1]))
                    .get(0);
        } catch (IOException e) {
            return value;
        }
    }
}
