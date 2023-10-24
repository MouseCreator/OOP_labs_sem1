package univ.lab.model;

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
}
