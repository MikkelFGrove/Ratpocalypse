package dk.lima.common.data;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class GameInputs {
    private static Map<EGameInputs, Boolean> keys;
    private static Map<EGameInputs, Boolean> pkeys;

    public GameInputs() {
        keys = Collections.synchronizedMap(new EnumMap<>(EGameInputs.class));
        pkeys = Collections.synchronizedMap(new EnumMap<>(EGameInputs.class));
    }

    public void update() {
        pkeys.putAll(keys);
    }

    public void setInput(EGameInputs key, boolean b) {
        keys.put(key, b);
    }

    public boolean isDown(EGameInputs key) {
        return keys.getOrDefault(key, false);
    }

    public boolean isPressed(EGameInputs key) {
        return keys.getOrDefault(key, false) & pkeys.getOrDefault(key, false);
    }
}
