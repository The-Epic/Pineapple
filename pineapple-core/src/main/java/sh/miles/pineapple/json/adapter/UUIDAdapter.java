package sh.miles.pineapple.json.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import org.jetbrains.annotations.ApiStatus;
import sh.miles.pineapple.json.JsonAdapter;

import java.lang.reflect.Type;
import java.util.UUID;

/**
 * UUID Adapter implementation for JsonAdapters
 * <p>
 * Note if you need to reflect this class you are doing something wrong.
 *
 * @since 1.0.0
 */
@ApiStatus.Internal
class UUIDAdapter implements JsonAdapter<UUID> {

    @Override
    public JsonElement serialize(final UUID src, final Type typeOfSrc, final JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    @Override
    public UUID deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        return UUID.fromString(json.getAsString());
    }

    @Override
    public Class<UUID> getAdapterType() {
        return UUID.class;
    }
}
