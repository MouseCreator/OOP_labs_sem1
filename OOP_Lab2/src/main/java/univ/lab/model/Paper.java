package univ.lab.model;

import lombok.Data;
import univ.lab.inject.Fill;

@Data
public class Paper {
    @Fill(attribute="id")
    private Long id;

    @Fill(attribute="title")
    private String title;

    @Fill(attribute="type")
    private String type;

    @Fill(attribute="monthly")
    private Boolean monthly;

    @Fill(attribute="characteristics")
    private Characteristics characteristics;
}
