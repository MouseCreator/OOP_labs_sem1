package mouse.project.lib.web.dispatcher;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Prototype;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.exception.ControllerException;
import mouse.project.lib.web.invoker.ControllerInvoker;
import mouse.project.lib.web.tool.FullURL;
import mouse.project.lib.web.tool.URLPath;
import mouse.project.lib.web.tool.URLPathNode;
import mouse.project.lib.web.tool.URLService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Prototype
public class DispatcherMapImpl implements DispatcherMap {

    private final URLService urlService;
    private final MapNode rootNode;
    @Auto
    public DispatcherMapImpl(URLService urlService) {
        this.urlService = urlService;
        rootNode = new MapNode(urlService);
    }

    public void setInvoker(String url, ControllerInvoker invoker) {
        FullURL fullURL = urlService.create(url);
        setInvoker(fullURL, invoker);
    }

    @Override
    public void setInvoker(FullURL fullURL, ControllerInvoker invoker) {
        URLPath path = fullURL.path();
        MapNode current = rootNode;
        for (URLPathNode node : path.getNodes()) {
            String content = node.content();
            current = current.moveTo(content);
        }
        current.set(invoker, urlService.write(fullURL));
    }

    public ControllerInvoker getInvoker(String url) {
        FullURL fullURL = urlService.create(url);
        URLPath path = fullURL.path();
        MapNode current = rootNode;
        StringBuilder urlBuild = new StringBuilder();
        for (URLPathNode node : path.getNodes()) {
            String content = node.content();
            urlBuild.append(content).append("/");
            Optional<MapNode> mapNode = current.moveSafe(content);
            if (mapNode.isEmpty()) {
                throw new ControllerException("No path defined: " + urlBuild);
            }
            current = mapNode.get();
        }
        Optional<ControllerInvoker> invoker = current.getInvoker();
        if (invoker.isEmpty()) {
            throw new ControllerException("No invoker defined for url: " + url);
        }
        return invoker.get();
    }

    private static class MapNode {
        private final Map<String, MapNode> map;
        private final static String ARGUMENT_KEY = "[]";
        private final URLService service;
        public MapNode(URLService service) {
            this.service = service;
            map = new HashMap<>();
        }

        private ControllerInvoker innerInvoker = null;

        public MapNode moveTo(String str) {
            if (service.isArgument(str)) {
                str = ARGUMENT_KEY;
            }
            return map.computeIfAbsent(str, s -> new MapNode(service));
        }

        public Optional<MapNode> moveSafe(String str) {
            MapNode node = map.get(str);
            if (node == null) {
                node = map.get(ARGUMENT_KEY);
            }
            return Optional.ofNullable(node);
        }
        public void set(ControllerInvoker invoker, String url) {
            if (innerInvoker != null) {
                throw new ControllerException("Already defined listener for given url: " + url);
            }
            innerInvoker = invoker;
        }
        public void free() {
            innerInvoker = null;
        }

        public Optional<ControllerInvoker> getInvoker() {
            return Optional.ofNullable(innerInvoker);
        }
    }

}
