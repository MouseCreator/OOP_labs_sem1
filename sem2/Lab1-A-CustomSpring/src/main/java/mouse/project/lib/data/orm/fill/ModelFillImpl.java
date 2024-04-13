package mouse.project.lib.data.orm.fill;

import mouse.project.lib.data.orm.desc.FieldDescription;
import mouse.project.lib.data.orm.desc.ModelDescription;
import mouse.project.lib.data.orm.exception.ORMException;
import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Service
public class ModelFillImpl implements ModelFill {

    private final FillUtilService fillUtil;
    @Auto
    public ModelFillImpl(FillUtilService fillUtil) {
        this.fillUtil = fillUtil;
    }

    @Override
    public <T> T createAndFill(ResultSet set, ModelDescription<T> description) {
        List<FieldDescription> fields = description.getFields();
        Constructor<T> constructor = description.getConstructor();
        T instance = fillUtil.construct(constructor);
        for (FieldDescription desc : fields) {
            try {
                Object object = set.getObject(desc.columnName(), desc.requiredClass());
                Field field = desc.field();
                fillUtil.assign(instance, object, field);
            } catch (SQLException e) {
                throw new ORMException("Cannot get column " + desc.columnName()
                        + " of class " + desc.requiredClass(), e);
            }
        }
        return instance;
    }
}
