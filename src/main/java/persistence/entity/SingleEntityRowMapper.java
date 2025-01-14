package persistence.entity;

import persistence.model.PersistentClass;

import java.sql.ResultSet;

public class SingleEntityRowMapper<T> implements EntityRowMapper<T> {
    private final PersistentClass<T> persistentClass;

    public SingleEntityRowMapper(final PersistentClass<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Override
    public T mapRow(final ResultSet resultSet) {
        return mapToEntity(persistentClass, resultSet);
    }

    private T mapToEntity(final PersistentClass<T> persistentClass, final ResultSet resultSet) {
        final T instance = this.persistentClass.createInstance();
        setEntityFieldsValue(this.persistentClass, instance, persistentClass.getTableName(), resultSet);

        return instance;
    }
}
