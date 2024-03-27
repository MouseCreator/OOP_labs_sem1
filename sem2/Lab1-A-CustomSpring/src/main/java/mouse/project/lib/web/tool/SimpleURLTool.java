package mouse.project.lib.web.tool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleURLTool implements URLTool {
    private List<URLPathNode> urlPathNodes;
    private List<URLParamNode> urlParamNodes;
    private List<URLFragmentNode> urlFragmentNodes;
    private SimpleURLTool() {

    }
    public static URLTool from(String value) {
        String[] split = value.split("\\?", 2);
        String path = split[0];
        Collection<URLPathNode> pathList = createPath(path);
        if (split.length==2) {
            String params = split[1];
            Collection<URLParamNode> paramList = createParams(params);
        }
        return null;
    }


    private static Collection<URLParamNode> createParams(String params) {
        return null;
    }

    private static Collection<URLPathNode> createPath(String path) {
        path = path.replaceAll("/+", "/");
        String[] nodesSplit = path.split("/");
        List<URLPathNode> pathList = new ArrayList<>();
        for (String s : nodesSplit) {
            if (s.isEmpty()) continue;
            pathList.add(new URLPathNode(s));
        }
        return pathList;
    }

    @Override
    public String toPattern() {
        return null;
    }



}
