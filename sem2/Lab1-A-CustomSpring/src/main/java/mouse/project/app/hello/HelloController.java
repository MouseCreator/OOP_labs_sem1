package mouse.project.app.hello;

import mouse.project.lib.ioc.annotation.Controller;
import mouse.project.lib.web.annotation.Get;
import mouse.project.lib.web.annotation.RequestPrefix;
import mouse.project.lib.web.annotation.URL;

@Controller
@RequestPrefix("/")
public class HelloController {

    @Get
    @URL("/hello")
    public String hello() {
        return "Hello, world!";
    }
}
