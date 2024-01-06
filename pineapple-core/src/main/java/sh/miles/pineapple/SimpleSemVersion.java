package sh.miles.pineapple;

public class SimpleSemVersion {

    private int major;
    private int minor;
    private int patch;

    private boolean alpha;
    private boolean beta;
    private boolean snapshot;
    private boolean hotfix;
    private boolean release;

    public SimpleSemVersion(int major, int minor, int patch, boolean alpha, boolean beta, boolean snapshot, boolean hotfix, boolean release) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.alpha = alpha;
        this.beta = beta;
        this.snapshot = snapshot;
        this.hotfix = hotfix;
        this.release = release;
    }

    public SimpleSemVersion(int major, int minor, int patch) {
        this(major, minor, patch, false, false, false, false, false);
    }

    /**
     * Parses a string representation of a SimpleSemVersion into a SimpleSemVersion object.
     *
     * @param version the string representation of the SimpleSemVersion
     * @return the parsed SimpleSemVersion object
     */
    public static SimpleSemVersion fromString(String version) {
        String[] split = version.split("-");
        String[] numParts = split[0].split("\\.");
        String suffix = split.length > 1 ? split[1] : "";

        if (numParts.length != 3) {
            throw new IllegalArgumentException("String must be in the format major.minor.patch");
        }

        SimpleSemVersion semVer;

        try {
            semVer = new SimpleSemVersion(Integer.parseInt(numParts[0]), Integer.parseInt(numParts[1]),
                    Integer.parseInt(numParts[2]));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("String must be in the format major.minor.patch-SUFFIX (optional suffix)");
        }

        if (!suffix.isEmpty()) {
            suffix.toLowerCase();
            switch (suffix) {
                case "alpha" -> semVer.alpha = true;
                case "beta" -> semVer.beta = true;
                case "snapshot" -> semVer.snapshot = true;
                case "hotfix" -> semVer.hotfix = true;
                case "release" -> semVer.release = true;
                default -> throw new IllegalArgumentException(
                        "Invalid suffix, must be alpha, beta, snapshot, hotfix, or release");
            }
        }

        return semVer;
    }

    /**
     * Check if the current SimpleSemVersion object is newer than the provided SimpleSemVersion object.
     *
     * @param other The SimpleSemVersion object to compare against.
     * @return Returns true if the current version is newer, false otherwise.
     */
    public boolean isNewerThan(SimpleSemVersion other) {
        if (this.major != other.major) {
            return this.major > other.major;
        } else if (this.minor != other.minor) {
            return this.minor > other.minor;
        } else if (this.patch != other.patch) {
            return this.patch > other.patch;
        }

        // ALPHA -> BETA -> SNAPSHOT -> HOTFIX -> RELEASE
        if (this.release && !other.release) {
            return true;
        } else if (this.hotfix && !other.release && !other.hotfix) {
            return true;
        } else if (this.snapshot && !other.release && !other.hotfix && !other.snapshot) {
            return true;
        } else if (this.beta && !other.release && !other.hotfix && !other.snapshot && !other.beta) {
            return true;
        } else if (this.alpha && !other.release && !other.hotfix && !other.snapshot && !other.beta && !other.alpha) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return this.major + "." + this.minor + "." + this.patch + getSuffix();
    }

    private String getSuffix() {
        if (alpha) {
            return "-ALPHA";
        } else if (beta) {
            return "-BETA";
        } else if (snapshot) {
            return "-SNAPSHOT";
        } else if (hotfix) {
            return "-HOTFIX";
        } else if (release) {
            return "-RELEASE";
        }
        return "";
    }
}
