package mouse.project.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import mouse.project.lib.service.model.LongIterable;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor

public class Term implements LongIterable {

    private Long id;
    private String term;
    private String definition;
    private String hint;
    private String picture_url;
    private Integer order;
    private LocalDateTime deletedAt;
    public Term(Long id) {
        this.id = id;
    }
}
