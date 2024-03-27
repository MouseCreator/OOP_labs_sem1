package mouse.project.lib.web.tool;

public class URLParamNode implements URLNode {

    public URLParamNode(String name, String value) {
        this.name = name;
        this.value = value;
    }

    private final String name;
    private final String value;

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
