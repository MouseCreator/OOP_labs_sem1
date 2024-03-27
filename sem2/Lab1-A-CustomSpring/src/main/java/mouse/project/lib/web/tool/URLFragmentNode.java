package mouse.project.lib.web.tool;

public class URLFragmentNode implements URLNode {
    private final String fragment;

    public URLFragmentNode(String fragment) {
        this.fragment = fragment;
    }

    @Override
    public String first() {
        return "#";
    }

    @Override
    public String write() {
        return fragment;
    }

    @Override
    public String next() {
        throw new UnsupportedOperationException("URL Anchor has no next element");
    }
}
