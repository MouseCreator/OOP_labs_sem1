package mouse.project.lib.web.scan;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import mouse.project.lib.web.invoker.ControllerInvoker;
import mouse.project.lib.web.register.RequestType;

@Data
@NoArgsConstructor
public class Registration {
    private String url;
    private RequestType requestType;
    @EqualsAndHashCode.Exclude
    private ControllerInvoker invoker;

    public Registration(String url, RequestType requestType, ControllerInvoker invoker) {
        this.url = url;
        this.requestType = requestType;
        this.invoker = invoker;
    }
}
