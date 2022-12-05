package ua.profitsoft.lecture6.reflection;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import ua.profitsoft.lecture6.data.HierarchyElement;
import ua.profitsoft.lecture6.data.Person;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Anton Leliuk
 */
public class ReflectionTest {

    @Test
    public void getPersonField() throws Exception {
        Person p = new Person("Adminchenko", "Admin");
        Assertions.assertEquals("Adminchenko Admin", p.getTitle());
        Field nameField = p.getClass().getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(p, "Admin2");
        Assertions.assertEquals("Admin2", nameField.get(p));
        Assertions.assertEquals("Adminchenko Admin2", p.getTitle());
    }

    @Test
    public void createPersonWithConstructor() throws Exception {
        Constructor<Person> c = Person.class.getConstructor(String.class, String.class);
        Person person = c.newInstance("Adminchenko", "Admin");
        Assertions.assertEquals("Adminchenko", person.getSurname());
        Assertions.assertEquals("Admin", person.getName());
        Assertions.assertEquals("Adminchenko Admin", person.getTitle());
    }

    @Test
    public void createPersonPrivateConstructor() throws Exception {
        Constructor<Person> c = Person.class.getDeclaredConstructor(String.class);
        c.setAccessible(true);
        Person p = c.newInstance("Adminchenko");
        Field name = Person.class.getDeclaredField("name");
        name.setAccessible(true);
        name.set(p, "Admin");
        Assertions.assertEquals("Adminchenko Admin", p.getTitle());
    }

    @Test
    public void getPrivateMethod() {
        try {
            HierarchyElement.class.getMethod("initCreateDate");
            Assertions.fail("getMethod couldn't find private method");
        } catch (NoSuchMethodException ignored) {}
    }

    @Test
    public void getPrivateDeclaredMethod() {
        try {
            Method initCreateDateMethod = HierarchyElement.class.getDeclaredMethod("initCreateDate");
            Assertions.assertNotNull(initCreateDateMethod);
        } catch (NoSuchMethodException e) {
            Assertions.fail("getDeclareMethod couldn't find private method");
        }
    }

    @Test
    public void reInitCreationDate() {
        Clock fixed = Clock.fixed(Instant.parse("2022-12-06T18:00:00Z"), ZoneId.of("UTC"));
        Instant instant = Instant.now(fixed);
        Person p;
        try(MockedStatic<Instant> instantMock = Mockito.mockStatic(Instant.class);
            MockedStatic<Clock> clockMock = Mockito.mockStatic(Clock.class)) {
            clockMock.when(Clock::systemDefaultZone).thenReturn(fixed);
            instantMock.when(Instant::now).thenReturn(instant);
            p = new Person("Adminchenko", "Admin");
            Assertions.assertEquals(LocalDateTime.of(2022, 12, 6, 18, 0), p.getCreateDate());

        }

        // 5 minutes later...
        fixed = Clock.fixed(Instant.parse("2022-12-06T18:05:00Z"), ZoneId.of("UTC"));
        instant = Instant.now(fixed);
        try (MockedStatic<Instant> instantMock = Mockito.mockStatic(Instant.class);
             MockedStatic<Clock> clockMock = Mockito.mockStatic(Clock.class)) {
            clockMock.when(Clock::systemDefaultZone).thenReturn(fixed);
            instantMock.when(Instant::now).thenReturn(instant);
            Method initCreateDateMethod = p.getClass().getSuperclass().getDeclaredMethod("initCreateDate");
            Assertions.assertNotNull(initCreateDateMethod);
            initCreateDateMethod.setAccessible(true); // open access to the private method
            initCreateDateMethod.invoke(p); // update createDate
            Assertions.assertEquals(LocalDateTime.of(2022, 12, 6, 18, 5), p.getCreateDate());
        } catch (Exception e) {
            Assertions.fail("Something went wrong", e);
        }
    }

    @Test
    public void propertyUtilsSet() throws Exception {
        Person p = new Person();
        PropertyUtils.setProperty(p, "surname", "Adminchenko");
        PropertyUtils.setProperty(p, "name", "Admin");
        Assertions.assertEquals("Adminchenko Admin", PropertyUtils.getProperty(p, "title"));
    }
}
