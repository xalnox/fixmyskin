package fr.xalnox.fixmyskin.mixin;

import fr.xalnox.fixmyskin.interfaces.SlimPlayerHolder;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements SlimPlayerHolder {
    @Unique private boolean isSlim = false;

    @Override
    public boolean skinFixer$isSlim() {
        return isSlim;
    }

    @Override
    public void skinFixer$setSlim(boolean slim) {
        this.isSlim = slim;
    }
}
