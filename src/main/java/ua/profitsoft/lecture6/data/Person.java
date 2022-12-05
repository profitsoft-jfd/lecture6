package ua.profitsoft.lecture6.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.profitsoft.lecture6.annotation.Name;

/**
 * @author Anton Leliuk
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Name("person")
public class Person extends HierarchyElement {

    @Name("surname")
    private String surname;

    @Name("name")
    private String name;

    public Person() {}

    private Person(String surname) {
        this.surname = surname;
    }

    public Person(String surname, String name) {
        this(surname);
        this.name = name;
    }

    @Override
    public String getTitle() {
        return surname + " " + name;
    }
}
