package mouse.project.lib.web.tool;

public class URLParamNode implements URLNode {
    private String name;
    private String value;

    @Override
    public String first() {
        return "?";
    }

    @Override
    public String write() {
        return name + "=" + value;
    }

    @Override
    public String next() {
        return "&";
    }
}
