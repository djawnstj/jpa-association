package persistence.entity.loader;

import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import persistence.ReflectionUtils;
import persistence.entity.CollectionEntityRowMapper;
import persistence.entity.SingleEntityRowMapper;
import persistence.model.CollectionPersistentClass;
import persistence.model.CollectionPersistentClassBinder;
import persistence.model.EntityJoinField;
import persistence.model.PersistentClass;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CollectionEntityLoader {
    private final JdbcTemplate jdbcTemplate;

    public CollectionEntityLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <T> List<T> queryWithEagerColumn(final Class<T> clazz, final EntityJoinField entityJoinField, final CollectionPersistentClassBinder collectionPersistentClassBinder, final String selectQuery) {
        final CollectionPersistentClass collectionPersistentClass = collectionPersistentClassBinder.getCollectionPersistentClass(ReflectionUtils.mapToGenericClassName(entityJoinField.getField()));

        final RowMapper<T> rowMapper = new CollectionEntityRowMapper<>(clazz, collectionPersistentClass);

        return jdbcTemplate.query(selectQuery, rowMapper)
                .stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toUnmodifiableList());
    }

    public <T> List<T> queryWithLazyColumn(final PersistentClass<T> persistentClass, final String selectQuery) {
        final RowMapper<T> rowMapper = new SingleEntityRowMapper<>(persistentClass);

        return jdbcTemplate.query(selectQuery, rowMapper)
                .stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }
}
