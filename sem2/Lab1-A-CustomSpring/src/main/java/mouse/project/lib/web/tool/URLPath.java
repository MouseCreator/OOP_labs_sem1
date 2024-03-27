package mouse.project.lib.web.tool;

import java.util.List;

public interface URLPath extends Writable {
    List<URLPathNode> getNodes();
    int length();
}
