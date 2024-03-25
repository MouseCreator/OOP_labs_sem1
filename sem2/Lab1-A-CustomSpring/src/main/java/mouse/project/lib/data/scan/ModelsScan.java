package mouse.project.lib.data.scan;

import jakarta.persistence.Table;

public class ModelsScan {
    public void scan(Class<?> modelClass) {
        String tableName = getTableName(modelClass);

    }

    private String getTableName(Class<?> modelClass) {
        Table annotation = modelClass.getAnnotation(Table.class);
        if (annotation == null) {
            return modelClass.getSimpleName().toLowerCase();
        }
        return annotation.name();
    }
}
