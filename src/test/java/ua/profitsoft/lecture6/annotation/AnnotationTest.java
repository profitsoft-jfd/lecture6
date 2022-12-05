package ua.profitsoft.lecture6.annotation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.profitsoft.lecture6.data.Person;

import java.lang.reflect.Field;

/**
 * @author Anton Leliuk
 */
public class AnnotationTest {

    @Test
    public void annotations() throws Exception {
        Name className = Person.class.getAnnotation(Name.class);
        Assertions.assertNotNull(className);
        Assertions.assertEquals("person", className.value());
        Field surnameField = Person.class.getDeclaredField("surname");
        Name surnameName = surnameField.getAnnotation(Name.class);
        Assertions.assertEquals("surname", surnameName.value());
        Field nameField = Person.class.getDeclaredField("name");
        Name nameAnnotation = nameField.getAnnotation(Name.class);
        Assertions.assertEquals("name", nameAnnotation.value());
    }
}
