package ua.profitsoft.lecture6.data;

import ua.profitsoft.lecture6.annotation.Name;

/**
 * @author Anton Leliuk
 */
public interface TitleAware {

    @Name("title")
    String getTitle();
}
