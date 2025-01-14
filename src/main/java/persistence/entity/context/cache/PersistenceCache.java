package persistence.entity.context.cache;

import java.util.HashMap;
import java.util.Map;

public class PersistenceCache {

    private final Map<EntityKey, Object> cache = new HashMap<>();

    public void add(final EntityKey key, final Object entity) {
        this.cache.put(key, entity);
    }

    public Object get(final EntityKey key) {
        return this.cache.get(key);
    }

    public void remove(final EntityKey key) {
        cache.remove(key);
    }

}
