package mouse.project.lib.web.dispatcher;

import mouse.project.lib.web.exception.ControllerException;
import mouse.project.lib.web.invoker.ControllerInvoker;
import mouse.project.lib.web.request.RequestBody;
import mouse.project.lib.web.request.RequestURL;
import mouse.project.lib.web.tool.FullURL;
import mouse.project.lib.web.tool.URLService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DispatcherMapImplTest {
    private DispatcherMap dispatcherMap;
    @BeforeEach
    void setUp() {
        dispatcherMap = new DispatcherMapImpl(new URLService());
    }

    private final RequestURL emptyRequest = new RequestURL() {
        @Override
        public FullURL getURL() {
            return null;
        }

        @Override
        public RequestBody getBody() {
            return null;
        }
    };
    @Test
    void simpleInvoker() {
        ControllerInvoker invoker = r -> "String";
        dispatcherMap.setInvoker("path/to/invoker", invoker);
        ControllerInvoker resultInvoker = dispatcherMap.getInvoker("path/to/invoker");
        assertEquals("String", resultInvoker.invoke(emptyRequest));
    }

    @Test
    void parametrizedInvoker() {
        ControllerInvoker invoker = r -> "String";
        dispatcherMap.setInvoker("path/to/invoker/[id]/get", invoker);
        ControllerInvoker resultInvoker = dispatcherMap.getInvoker("path/to/invoker/1/get");
        assertEquals("String", resultInvoker.invoke(emptyRequest));
    }

    @Test
    void testNoDuplicates() {
        ControllerInvoker invoker = r -> "String";
        dispatcherMap.setInvoker("", invoker);
        assertThrows(ControllerException.class, () -> dispatcherMap.setInvoker("", invoker));
    }

    @Test
    void testNotDefined() {
        assertThrows(ControllerException.class, () -> dispatcherMap.getInvoker(""));
        ControllerInvoker invoker = r -> "String";
        dispatcherMap.setInvoker("/path/to", invoker);
        assertThrows(ControllerException.class, () -> dispatcherMap.getInvoker("path/else"));
    }
}