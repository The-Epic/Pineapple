package sh.miles.pineapple;

public class PineappleLib {
    private static final PineappleLib INSTANCE = new PineappleLib();

    // TODO add spigot plugin here

    private PineappleLib() {
    }



    public static PineappleLib getInstance() {
        return INSTANCE;
    }
}
