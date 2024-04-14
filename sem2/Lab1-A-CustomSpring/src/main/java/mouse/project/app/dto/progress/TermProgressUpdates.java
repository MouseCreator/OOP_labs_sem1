package mouse.project.app.dto.progress;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class TermProgressUpdates {
    private Long userId;
    private List<TermProgressPair> updatesList;

    public TermProgressUpdates() {
        userId = null;
        updatesList = new ArrayList<>();;
    }
}
