package mouse.project.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import mouse.project.lib.service.model.LongIterable;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Tag implements LongIterable {

    private Long id;

    private String name;

    private String colorHex;

    private User owner;

    private LocalDateTime deletedAt;
    public Tag(Long id) {
        this.id = id;
    }
}
