package persistence.model;

import java.lang.reflect.Field;

public class EntityField extends AbstractEntityField {

    protected EntityField(final String fieldName, final String columnName, final Class<?> entityClass, final Field field) {
        super(fieldName, columnName, entityClass, field);
    }
}
