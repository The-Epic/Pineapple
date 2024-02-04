package sh.miles.pineapple.nms.loader;

import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.ReflectionUtils;
import sh.miles.pineapple.nms.api.PineappleNMS;

/**
 * Provides Management for NMS Classes
 */
public final class NMSLoader {

    public static final NMSLoader INSTANCE = new NMSLoader();
    private static final String PATH = "sh.miles.pineapple.nms.impl.%s.%s";

    private PineappleNMS handler;
    private byte active = (byte) 0;

    private NMSLoader() {
    }

    /**
     * Activates PineappleNMS and supplies a loader to the NMSLoader
     *
     * @since 1.0.0
     */
    public void activate() {
        this.handler = ReflectionUtils.newInstance(PATH.formatted(MinecraftVersion.CURRENT.getProtocolVersion(), PineappleNMS.class.getSimpleName() + "Impl"), new Object[0]);
        this.active = (byte) 1;
    }

    /**
     * Disables PineappleNMS and suppliers a backup loader to the NMSLoader
     *
     * @param fallbackHandler the fallback handler
     */
    public void fallback(@NotNull final PineappleNMS fallbackHandler) {
        this.handler = fallbackHandler;
        this.active = (byte) 2;
    }

    public void disable() {
        this.handler = null;
        this.active = (byte) 0;
    }

    /**
     * Gets the Pineapple
     * <p>
     * The pineapple(Ananas comosus) is a tropical plant with an edible fruit; it is the most economically significant
     * plant in the family Bromeliaceae.
     * <p>
     * The pineapple is indigenous to South America, where it has been cultivated for many centuries. The introduction
     * of the pineapple to Europe in the 17th century made it a significant cultural icon of luxury. Since the 1820s,
     * pineapple has been commercially grown in greenhouses and many tropical plantations.
     *
     * @return the pineapple
     * @since 1.0.0
     */
    public PineappleNMS getPineapple() {
        return this.handler;
    }

    /**
     * Gets whether or not the NMSLoader is active.
     * <p>
     * value of 0 indicates the NMSLoader is disabled
     * value of 1 indicates the NMSLoader is enabled
     * value of 2 indicates the NMSLoader is enabled, but working off of fallback
     *
     * @return the byte
     */
    public byte isActive() {
        return this.active;
    }

}
