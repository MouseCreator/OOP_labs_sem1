package univ.lab.model;

import lombok.Data;
import univ.lab.fill.FillList;

import java.util.List;
@Data
public class Papers {
    @FillList(attribute = "paper")
    private List<Papers> papersList;
}
