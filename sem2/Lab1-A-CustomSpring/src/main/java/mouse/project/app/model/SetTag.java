package mouse.project.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SetTag {

    private User user;

    private StudySet studySet;

    private Tag tag;

    public SetTag(User user, StudySet studySet, Tag tag) {
        this.user = user;
        this.studySet = studySet;
        this.tag = tag;
    }
}
