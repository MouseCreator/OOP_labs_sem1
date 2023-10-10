package univ.lab.model;

import lombok.Data;
import univ.lab.fill.Fill;
import univ.lab.fill.Fillable;

@Data
@Fillable(name = "characteristics")
public class Characteristics {
    @Fill(attribute="colored")
    private Boolean isColored;
    @Fill(attribute="volume")
    private Integer volume;
    @Fill(attribute="glossy")
    private Boolean isGlossy;
    @Fill(attribute="subscription_index")
    private Boolean hasSubscriptionIndex;
}
