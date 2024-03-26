package mouse.project.lib.web.context;

import mouse.project.lib.web.tool.URLTool;

public class ControllerContextImpl implements ControllerContext {

    private final Class<?> root;
    private final URLTool urlTool;
    public ControllerContextImpl(Class<?> root, URLTool urlTool) {
        this.root = root;
        this.urlTool = urlTool;
    }
    @Override
    public URLTool getUrl() {
        return urlTool;
    }

    @Override
    public Class<?> getRoot() {
        return root;
    }
}
