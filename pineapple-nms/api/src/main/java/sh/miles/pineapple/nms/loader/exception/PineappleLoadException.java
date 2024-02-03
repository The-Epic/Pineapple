package sh.miles.pineapple.nms.loader.exception;

/**
 * A Custom Exception thrown given the PineappleNMS module has trouble loading
 */
public class PineappleLoadException extends IllegalStateException {

    public PineappleLoadException(int major, int minor, int patch) {
        super(":(... The pineapple could not be loaded!!! %d.%d.%d".formatted(major, minor, patch));
    }

}
