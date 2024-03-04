package univ.lab.model;

import lombok.Data;
import univ.lab.fill.FillList;
import univ.lab.fill.Fillable;

import java.util.List;
@Data
@Fillable(name = "papers")
public class Papers {
    @FillList(attribute = "paper")
    private List<Paper> papersList;
}
