package mouse.project.lib.web.tool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class URLServiceTest {

    private URLService service;

    @BeforeEach
    void setUp() {
        service = new URLService();
    }

    @Test
    void getPathArgument() {

    }

    @Test
    void write() {
        String base = "some/url?a=1&b=2#fragment";
        String expect = "/some/url?a=1&b=2#fragment";
        String[] validUrls = {
                "/" + base,
                base,
                "//" + base,
                "localhost:8080/" + base,
                "https://host.com/" + base};
        List.of(validUrls).forEach(urlStr -> {

            FullURL fullURL = service.create(urlStr);
            String write = service.write(fullURL);
            assertEquals(expect, write);

        });
    }

    @Test
    void create() {
        String base = "some/url?a=1&b=2#fragment";
        FullURL fullURL = service.create(base);

        URLPath path = fullURL.path();
        assertEquals(2, path.length());

        Object[] pathArray = path.getNodes().stream().map(URLPathNode::content).toArray();
        assertArrayEquals(new String[] {"some","url"}, pathArray);

        Object[] paramNames = fullURL.params().getNodes().stream().map(URLParamNode::name).toArray();
        Object[] paramValues = fullURL.params().getNodes().stream().map(URLParamNode::value).toArray();
        assertArrayEquals(new String[] {"a","b"}, paramNames);
        assertArrayEquals(new String[] {"1","2"}, paramValues);

        URLFragmentNode fragment = fullURL.fragment().getRaw();
        assertEquals("fragment", fragment.write());
    }
}