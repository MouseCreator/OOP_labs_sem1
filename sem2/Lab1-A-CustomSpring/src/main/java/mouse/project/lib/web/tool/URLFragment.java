package mouse.project.lib.web.tool;

public class URLFragment implements URLNode {
    private String fragment;

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
