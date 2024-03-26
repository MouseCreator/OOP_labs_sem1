package mouse.project.lib.web.tool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleURLTool implements URLTool {
    private SimpleURLTool() {

    }
    public static URLTool from(String value) {
        String[] split = value.split("\\?", 2);
        String path = split[0];
        Collection<URLPath> pathList = createPath(path);
        if (split.length==2) {
            String params = split[1];
            Collection<URLParam> paramList = createParams(params);
        }
        return null;
    }

    private static Collection<URLParam> createParams(String params) {
        return null;
    }

    private static Collection<URLPath> createPath(String path) {
        path = path.replaceAll("/+", "/");
        String[] nodesSplit = path.split("/");
        List<URLPath> pathList = new ArrayList<>();
        for (String s : nodesSplit) {
            if (s.isEmpty()) continue;
            pathList.add(new URLPath(s));
        }
        return pathList;
    }

    @Override
    public String toPattern() {
        return null;
    }
    private interface URLNode {
        String content();
        String next();
    }

    private static record URLPath(String content) {
    }

    private static class URLParam {
        private String name;
        private String value;
    }
}
