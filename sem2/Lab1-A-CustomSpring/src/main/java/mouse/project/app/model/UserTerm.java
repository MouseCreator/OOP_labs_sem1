package mouse.project.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserTerm {
    private User user;
    private Term term;
    private String progress;

    public UserTerm(User user, Term term, String progress) {
        this.user = user;
        this.term = term;
        this.progress = progress;
    }
}
