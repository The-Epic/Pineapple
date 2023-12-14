package sh.miles.nms.api.loader;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import sh.miles.nms.api.loader.exception.PineappleLoadException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A class that represents a minecraft version
 *
 * @since 1.0.0
 */
public class MinecraftVersion {

    private static final Map<String, MinecraftVersion> protocolMap;

    public static final MinecraftVersion CURRENT;

    static {
        final Map<String, MinecraftVersion> temp = new HashMap<>();
        temp.put("1.20.1", new MinecraftVersion("1.20.1", "v1_20_R1"));
        temp.put("1.20.2", new MinecraftVersion("1.20.2", "v1_20_R2"));
        temp.put("1.20.4", new MinecraftVersion("1.20.4", "v1_20_R3"));
        protocolMap = ImmutableMap.copyOf(temp);

        String[] split = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
        final int major = Integer.parseInt(split[0]);
        final int minor = Integer.parseInt(split[1]);
        boolean hasPatch = split.length == 3;
        final int patch = hasPatch ? Integer.parseInt(split[2]) : 0;
        CURRENT = protocolMap.get("%d.%d.%d".formatted(major, minor, patch));
        if (CURRENT == null) {
            throw new PineappleLoadException(major, minor, patch);
        }
    }

    private final int major;
    private final int minor;
    private final int patch;
    private final String protocolVersion;

    private MinecraftVersion(@NotNull final String version, @NotNull final String protocolVersion) {
        String[] split = version.split("\\.");
        this.major = Integer.parseInt(split[0]);
        this.minor = Integer.parseInt(split[1]);
        this.patch = split.length == 3 ? Integer.parseInt(split[2]) : 0;
        this.protocolVersion = protocolVersion;
    }

    private MinecraftVersion(final int major, final int minor, final int patch, final String protocolVersion) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.protocolVersion = protocolVersion;
    }

    /**
     * Gets major number
     *
     * @return the major number
     */
    public int getMajor() {
        return major;
    }

    /**
     * gets minor number
     *
     * @return the minor number
     */
    public int getMinor() {
        return minor;
    }

    /**
     * gets patch number or 0 if none
     *
     * @return patch number or 0
     */
    public int getPatch() {
        return patch;
    }

    /**
     * Gets the relocation version of bukkit
     *
     * @return the relocation version of bukkit
     */
    @NotNull
    public String getProtocolVersion() {
        return protocolVersion;
    }

    /**
     * Gets the standard name of the version e.g. 1.19.4
     *
     * @return the name of the version
     */
    @NotNull
    public String getName() {
        return "%d.%d%s".formatted(major, minor, patch == 0 ? "" : "." + patch);
    }

    /**
     * Gets the internal name used in Pineapple
     *
     * @return the internal name in format vMajor_Minor_Patch
     */
    @NotNull
    public String getInternalName() {
        return "v%d_%d%s".formatted(major, minor, patch == 0 ? "" : "_" + patch);
    }

    @NotNull
    public int[] toArray() {
        return patch == 0 ? new int[]{major, minor} : new int[]{major, minor, patch};
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MinecraftVersion that)) return false;
        return major == that.major && minor == that.minor && patch == that.patch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch);
    }

    @NotNull
    public static MinecraftVersion fromArray(int[] majorMinorPatch, String protocolVersion) throws IllegalArgumentException {
        Preconditions.checkArgument(majorMinorPatch.length > 1, "This method must be provided an array with 2 or 3 entries in the format. Major,Minor,Patch or Major,Minor");
        if (majorMinorPatch.length == 2) {
            return new MinecraftVersion(majorMinorPatch[0], majorMinorPatch[1], 0, protocolVersion);
        }
        return new MinecraftVersion(majorMinorPatch[0], majorMinorPatch[1], majorMinorPatch[2], protocolVersion);
    }
}
