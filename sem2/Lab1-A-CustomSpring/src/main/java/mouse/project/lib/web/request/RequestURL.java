package mouse.project.lib.web.request;


import mouse.project.lib.web.tool.FullURL;

public interface RequestURL {
    FullURL getURL();
    RequestBody getBody();
}
