package mouse.project.lib.web.invoker;


import mouse.project.lib.web.request.RequestBody;

public interface BodyProcessor {
    Object getBodyInfo(RequestBody body, BodyDesc desc) ;
}
