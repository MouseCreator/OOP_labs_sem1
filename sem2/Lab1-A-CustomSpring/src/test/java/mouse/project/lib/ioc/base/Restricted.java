package mouse.project.lib.ioc.base;

import mouse.project.lib.annotation.Service;
import mouse.project.lib.annotation.UseRestriction;

@UseRestriction(usedBy = "Release")
@Service
public class Restricted {
    public int getInteger() {
        return 1;
    }
}
