package sh.miles.pineapple.updater;

import org.junit.jupiter.api.Test;
import sh.miles.pineapple.SimpleSemVersion;
import sh.miles.pineapple.ReflectionUtils;

import java.lang.invoke.MethodHandle;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleSemVersionTest {

    private static final MethodHandle MAJOR = ReflectionUtils.getFieldAsGetter(SimpleSemVersion.class, "major");
    private static final MethodHandle MINOR = ReflectionUtils.getFieldAsGetter(SimpleSemVersion.class, "minor");
    private static final MethodHandle PATCH = ReflectionUtils.getFieldAsGetter(SimpleSemVersion.class, "patch");

    private static final MethodHandle ALPHA = ReflectionUtils.getFieldAsGetter(SimpleSemVersion.class, "alpha");
    private static final MethodHandle BETA = ReflectionUtils.getFieldAsGetter(SimpleSemVersion.class, "beta");
    private static final MethodHandle SNAPSHOT = ReflectionUtils.getFieldAsGetter(SimpleSemVersion.class, "snapshot");
    private static final MethodHandle HOTFIX = ReflectionUtils.getFieldAsGetter(SimpleSemVersion.class, "hotfix");
    private static final MethodHandle RELEASE = ReflectionUtils.getFieldAsGetter(SimpleSemVersion.class, "release");


    @Test
    public void test_No_Suffix() {
        SimpleSemVersion version = SimpleSemVersion.fromString("1.2.3");
        assertEquals(1, ReflectionUtils.safeInvoke(MAJOR.bindTo(version)));
        assertEquals(2, ReflectionUtils.safeInvoke(MINOR.bindTo(version)));
        assertEquals(3, ReflectionUtils.safeInvoke(PATCH.bindTo(version)));
    }

    @Test
    public void test_Alpha() {
        SimpleSemVersion version = SimpleSemVersion.fromString("1.2.3-alpha");
        assertTrue(((Boolean) ReflectionUtils.safeInvoke(ALPHA.bindTo(version))));
    }

    @Test
    public void test_Beta() {
        SimpleSemVersion version = SimpleSemVersion.fromString("1.2.3-beta");
        assertTrue(((Boolean) ReflectionUtils.safeInvoke(BETA.bindTo(version))));
    }

    @Test
    public void test_Snapshot() {
        SimpleSemVersion version = SimpleSemVersion.fromString("1.2.3-snapshot");
        assertTrue(((Boolean) ReflectionUtils.safeInvoke(SNAPSHOT.bindTo(version))));
    }

    @Test
    public void test_Hotfix() {
        SimpleSemVersion version = SimpleSemVersion.fromString("1.2.3-hotfix");
        assertTrue(((Boolean) ReflectionUtils.safeInvoke(HOTFIX.bindTo(version))));
    }

    @Test
    public void test_Release() {
        SimpleSemVersion version = SimpleSemVersion.fromString("1.2.3-release");
        assertTrue(((Boolean) ReflectionUtils.safeInvoke(RELEASE.bindTo(version))));
    }

    @Test
    public void test_Newer_Than_AlphaBeta() {
        SimpleSemVersion version = SimpleSemVersion.fromString("1.2.3-beta");
        SimpleSemVersion other = SimpleSemVersion.fromString("1.2.3-alpha");

        assertTrue(version.isNewerThan(other));
    }

    @Test
    public void test_Newer_Than_AlphaSnapshot() {
        SimpleSemVersion version = SimpleSemVersion.fromString("1.2.3-snapshot");
        SimpleSemVersion other = SimpleSemVersion.fromString("1.2.3-alpha");

        assertTrue(version.isNewerThan(other));
    }

    @Test
    public void test_Newer_Than_AlphaRelease() {
        SimpleSemVersion version = SimpleSemVersion.fromString("1.2.3-release");
        SimpleSemVersion other = SimpleSemVersion.fromString("1.2.3-alpha");

        assertTrue(version.isNewerThan(other));
    }

    @Test
    public void test_Newer_Than_BetaSnapshot() {
        SimpleSemVersion version = SimpleSemVersion.fromString("1.2.3-snapshot");
        SimpleSemVersion other = SimpleSemVersion.fromString("1.2.3-beta");

        assertTrue(version.isNewerThan(other));
    }

    @Test
    public void test_Newer_Than_ReleaseHotFix() {
        SimpleSemVersion version = SimpleSemVersion.fromString("1.2.3-hotfix");
        SimpleSemVersion other = SimpleSemVersion.fromString("1.2.3-release");

        assertFalse(version.isNewerThan(other));
    }
}
