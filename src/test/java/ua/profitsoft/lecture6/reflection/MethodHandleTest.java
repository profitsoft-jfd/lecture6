package ua.profitsoft.lecture6.reflection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.profitsoft.lecture6.data.Person;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author Anton Leliuk
 */
public class MethodHandleTest {

    @Test
    public void methodHandle() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.publicLookup();
        MethodType constructorType = MethodType.methodType(void.class, String.class, String.class);
        MethodHandle constructorHandle = lookup.findConstructor(Person.class, constructorType);
        Person person = (Person) constructorHandle.invokeExact("Adminchenko", "Admin");
        Assertions.assertEquals("Adminchenko Admin", person.getTitle());
        MethodHandle getTitleHandle = lookup.findVirtual(Person.class, "getTitle", MethodType.methodType(String.class));
        Assertions.assertEquals("Adminchenko Admin", getTitleHandle.invoke(person));
    }
}
