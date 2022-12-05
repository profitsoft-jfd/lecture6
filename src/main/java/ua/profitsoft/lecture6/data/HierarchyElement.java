package ua.profitsoft.lecture6.data;

import lombok.Data;
import ua.profitsoft.lecture6.annotation.Author;
import ua.profitsoft.lecture6.annotation.Name;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Anton Leliuk
 */
@Author("Anton Leliuk")
@Data
public abstract class HierarchyElement implements TitleAware {

    @Name("create_date")
    private LocalDateTime createDate;

    public HierarchyElement() {
        initCreateDate();
    }

    private void initCreateDate() {
        this.createDate = LocalDateTime.now();
    }
}
