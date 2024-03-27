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

    private void writeURLToBuilder(StringBuilder builder, List<URLNode> nodes) {
        int size = nodes.size();
        if (size==0) {
            return;
        }
        builder.append( nodes.get(0).first() );
        for (int i = 0; i < size - 1; i++) {
            builder.append( nodes.get(i).write() );
            builder.append( nodes.get(i).next() );
        }
        builder.append( nodes.get(size-1).write() );
    }





}
