package mouse.project.lib.web.context;

import mouse.project.lib.web.tool.FullURL;

public class ControllerContextImpl implements ControllerContext {

    private final Class<?> root;
    private final FullURL urlTool;
    public ControllerContextImpl(Class<?> root, FullURL urlTool) {
        this.root = root;
        this.urlTool = urlTool;
    }
    @Override
    public FullURL getUrl() {
        return urlTool;
    }

    @Override
    public Class<?> getRoot() {
        return root;
    }
}
