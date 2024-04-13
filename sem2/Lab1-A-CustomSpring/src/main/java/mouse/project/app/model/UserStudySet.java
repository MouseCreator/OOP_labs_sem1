package mouse.project.app.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserStudySet {
    private User user;
    private StudySet studySet;
    private String type;
    public UserStudySet(User user, StudySet set, String type) {
        this.user = user;
        this.studySet = set;
        this.type = type;
    }
}
