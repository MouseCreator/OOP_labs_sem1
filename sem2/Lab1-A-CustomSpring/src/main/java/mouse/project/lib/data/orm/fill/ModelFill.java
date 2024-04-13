package mouse.project.lib.data.orm.fill;

import mouse.project.lib.data.orm.desc.ModelDescription;

import java.sql.ResultSet;

public interface ModelFill {
    <T> T createAndFill(ResultSet set, ModelDescription<T> description);
}
