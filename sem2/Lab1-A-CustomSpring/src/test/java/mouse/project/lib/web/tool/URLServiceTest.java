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
    }
}