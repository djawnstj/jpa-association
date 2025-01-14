package persistence.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.EntityMetaDataTestSupport;
import persistence.PersonV3FixtureFactory;
import persistence.sql.ddl.PersonV1;
import persistence.sql.ddl.PersonV3;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PersistentClassTest extends EntityMetaDataTestSupport {

    @DisplayName("메타 데이터에 있는 필드들을 이용해 entity 객체에서 필드 값들을 추출한다")
    @Test
    public void extractValues() throws Exception {
        // given
        final PersonV3 person = PersonV3FixtureFactory.generatePersonV3Stub();
        final PersistentClass<?> persistentClass = PersistentClassMapping.getPersistentClass(person.getClass().getName());

        // when
        final Map<String, Object> values = persistentClass.extractValues(person);

        // then
        assertThat(values)
                .extracting("id", "name", "age", "email")
                .contains(person.getId(), person.getName(), person.getAge(), person.getEmail());
    }

    @DisplayName("메타 데이터로 entity 객체의 필드 값들을 추출할 때, 알맞은 클래스 타입이 아니라면 예외를 반환한다")
    @Test
    public void extractValuesNotEqualClassType() throws Exception {
        // given
        final PersonV3 person = PersonV3FixtureFactory.generatePersonV3Stub();
        final PersistentClass<?> persistentClass = PersistentClassMapping.getPersistentClass(PersonV1.class.getName());

        // when then
        assertThatThrownBy(() -> persistentClass.extractValues(person))
                .isInstanceOf(MetaDataModelMappingException.class)
                .hasMessage("not equal class type - meta data type: " + PersonV1.class.getName() + ", parameter type: " + person.getClass().getName());
    }

}
