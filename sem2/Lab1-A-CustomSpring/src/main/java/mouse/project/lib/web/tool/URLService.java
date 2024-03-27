package mouse.project.lib.web.tool;

import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.exception.URLException;

import java.util.List;
import java.util.Optional;
@Service
public class URLService {
    public String getPathArgument(URLPath patternPath, URLPath targetPath, String id) {
        if (patternPath.length() != targetPath.length()) {
            throw new URLException("Pattern and target lengths do not match");
        }
        int len = patternPath.length();
        List<URLPathNode> patternNodes = patternPath.getNodes();
        List<URLPathNode> targetNodes = targetPath.getNodes();
        for (int i = 0; i < len; i++) {
            URLPathNode patternNode = patternNodes.get(i);
            URLPathNode targetNode = targetNodes.get(i);
            Optional<String> argument = getArgument(patternNode);
            if (argument.isEmpty()) {
                continue;
            }
            String s = argument.get();
            if (s.equals(id)) {
                return targetNode.content();
            }
        }
        throw new URLException("Cannot find id " + id + " in pattern " + patternPath.write());
    }

    public String write(List<? extends URLNode> nodes) {
        StringBuilder builder = new StringBuilder();
        writeURLToBuilder(builder, nodes);
        return builder.toString();
    }
    private void writeURLToBuilder(StringBuilder builder, List<? extends URLNode> nodes) {
        int size = nodes.size();
        if (size==0) {
            return;
        }
        int nSize = size - 1;
        builder.append( nodes.get(0).first() );
        for (int i = 0; i < nSize; i++) {
            builder.append( nodes.get(i).write() );
            builder.append( nodes.get(i).next() );
        }
        builder.append( nodes.get(nSize).write() );
    }

    private boolean isArgument(URLPathNode node) {
        String content = node.content();
        return (content.startsWith("[") && content.endsWith("]"));
    }
    private Optional<String> getArgument(URLPathNode node) {
        if (!isArgument(node)) {
            return Optional.empty();
        }
        String content = node.content();
        int len = content.length();
        return Optional.of(content.substring(1, len-1));
    }

    public String write(FullURL fullURL) {
        URLPath path = fullURL.path();
        String pathString = write(path.getNodes());
        URLParams params = fullURL.params();
        String paramString = write(params.getNodes());
        URLFragment fragment = fullURL.fragment();
        String fragmentString = write(fragment.getAsList());
        return pathString + paramString + fragmentString;
    }
    public FullURL create(String url) {

    }
}
