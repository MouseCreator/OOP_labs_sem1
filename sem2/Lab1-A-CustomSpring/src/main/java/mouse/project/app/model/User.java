package mouse.project.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import mouse.project.lib.service.model.LongIterable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class User implements LongIterable {
    private Long id;
    private String name;
    private LocalDateTime deletedAt = null;
    private String profilePictureUrl;
    private List<StudySet> studySets = new ArrayList<>();
    public User(Long id) {
        this.id = id;
    }
}
