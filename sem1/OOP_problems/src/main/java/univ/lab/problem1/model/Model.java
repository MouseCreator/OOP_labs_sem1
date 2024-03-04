package univ.lab.problem1.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
@Data
public class Model implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String description;

    public Model(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public Model() {
        this.id = 0;
        this.name = "";
        this.description = "";
    }
}
