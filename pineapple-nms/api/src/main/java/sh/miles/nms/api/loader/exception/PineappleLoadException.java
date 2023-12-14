package sh.miles.nms.api.loader.exception;

public class PineappleLoadException extends IllegalStateException {

    public PineappleLoadException(int major, int minor, int patch) {
        super(":(... The pineapple could not be loaded!!! %d.%d.%d".formatted(major, minor, patch));
    }

}
