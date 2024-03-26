package mouse.project.lib.web.invoker;

import mouse.project.lib.web.invoker.desc.BodyDesc;
import mouse.project.lib.web.invoker.desc.ParameterDesc;

public class ArgumentHolderImpl implements ArgumentHolder {
    private final BodyDesc bodyDesc;
    private final ParameterDesc parameterDesc;
    private final boolean isBody;
    public ArgumentHolderImpl(ParameterDesc parameterDesc) {
        this.parameterDesc = parameterDesc;
        this.bodyDesc = null;
        isBody = false;
    }

    public ArgumentHolderImpl(BodyDesc bodyDesc) {
        this.bodyDesc = bodyDesc;
        this.parameterDesc = null;
        isBody = true;
    }

    @Override
    public boolean isBodySource() {
        return isBody;
    }

    @Override
    public boolean isParamSource() {
        return !isBody;
    }

    @Override
    public BodyDesc toBodyDesc() {
        return bodyDesc;
    }

    @Override
    public ParameterDesc toParamDesc() {
        return parameterDesc;
    }
}
