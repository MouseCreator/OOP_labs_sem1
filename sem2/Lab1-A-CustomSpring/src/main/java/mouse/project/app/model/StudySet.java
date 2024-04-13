package mouse.project.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import mouse.project.lib.service.model.LongIterable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class StudySet implements LongIterable {

    private Long id;

    private String name;

    private String pictureUrl = null;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt = null;

    private List<User> users = new ArrayList<>();

    private List<Term> terms = new ArrayList<>();
    public StudySet(Long id) {
        this.id = id;
    }
}
