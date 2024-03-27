package mouse.project.lib.web.tool;

public record URLPath(String content) implements URLNode {
    @Override
    public String first() {
        return "/";
    }

    @Override
    public String write() {
        return content;
    }

    @Override
    public String next() {
        return "/";
    }
}
