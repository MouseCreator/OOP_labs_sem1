package mouse.project.app.dto.term;

import lombok.Data;

@Data
public class TermUpdateDTO {
    private Long id;
    private String term;
    private String definition;
    private String hint;
    private String picture_url;
    private Integer order;
}
