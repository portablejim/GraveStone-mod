package gravestone.core.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gravestone.config.GraveStoneConfig;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import org.lwjgl.opengl.GL11;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GSRenderEventHandler {


    public static float fogDensityPerTick = 0;
    private static float fogDensity = 0;

    public static final float MAX_DENSITY = 0.08F;
    public static final float DENSITY_PER_GRAVE = 0.001F;
    public static final float DENSITY_PER_TICK = 0.00005F;

    public static void addFog() {
        fogDensityPerTick += DENSITY_PER_GRAVE;
        if (fogDensityPerTick > MAX_DENSITY) {
            fogDensityPerTick = MAX_DENSITY;
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void fogEvent(EntityViewRenderEvent.RenderFogEvent event) {
        if (GraveStoneConfig.isFogEnabled) {
            if (fogDensity < fogDensityPerTick) {
                fogDensity += DENSITY_PER_TICK;
            } else if (fogDensity > fogDensityPerTick) {
                fogDensity -= DENSITY_PER_TICK;
            }
            if (fogDensity < DENSITY_PER_TICK) {
                fogDensity = 0;
            }

            if (fogDensity > 0) {
                GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
                GL11.glFogf(GL11.GL_FOG_DENSITY, fogDensity);
            }
        }
    }
}
