package sh.miles.pineapple.nms.api.loader;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.ReflectionUtils;
import sh.miles.pineapple.nms.api.PineappleNMS;

/**
 * Provides Management for NMS Classes
 */
public class NMSManager {

    private static final String PATH = "sh.miles.pineapple.nms.impl.%s.%s";
    private static PineappleNMS handle = getHandle();

    private NMSManager() {
        throw new UnsupportedOperationException("That isn't very fresh of you sneaky boy ;)");
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
     * @since 1.0.0-SNAPSHOT
     */
    @NotNull
    public static PineappleNMS getPineapple() {
        return handle;
    }

    /**
     * Creates a NMS handle
     *
     * @return returns the type of handle
     * @since 1.0.0-SNAPSHOT
     */
    @ApiStatus.Internal
    private static PineappleNMS getHandle() {
        return ReflectionUtils.newInstance(PATH.formatted(MinecraftVersion.CURRENT.getProtocolVersion(), PineappleNMS.class.getSimpleName() + "Impl"), new Object[0]);
    }


}
