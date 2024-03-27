package mouse.project.lib.web.tool;

import java.util.List;

public class URLFragmentImpl implements URLFragment {
    private final URLFragmentNode node;
    public URLFragmentImpl(URLFragmentNode fragmentNode) {
        node = fragmentNode;
    }

    @Override
    public List<URLFragmentNode> getAsList() {
        return List.of(node);
    }

    @Override
    public URLFragmentNode getRaw() {
        return node;
    }
}
