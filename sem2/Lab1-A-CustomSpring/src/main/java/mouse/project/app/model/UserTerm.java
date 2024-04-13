package mouse.project.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserTerm {
    private User user;
    private Term term;
    private String progress;
}
