package univ.lab.model;

import lombok.Data;
import univ.lab.inject.Inject;

@Data
public class Paper {
    @Inject(attribute="id")
    private Long id;

    @Inject(attribute="title")
    private String title;

    @Inject(attribute="type")
    private String type;

    @Inject(attribute="monthly")
    private Boolean monthly;

    @Inject(attribute="characteristics")
    private Characteristics characteristics;
}
