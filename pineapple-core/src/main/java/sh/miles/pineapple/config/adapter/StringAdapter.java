package sh.miles.pineapple.config.adapter;

public class StringAdapter implements GenericStringAdapter<String> {

    @Override
    public Class<String> getRuntimeType() {
        return String.class;
    }

    @Override
    public String toString(String value) {
        return value;
    }

    @Override
    public String fromString(String value) {
        return value;
    }
}
