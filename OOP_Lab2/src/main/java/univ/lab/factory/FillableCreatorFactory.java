package univ.lab.factory;

import univ.lab.fill.FillableCreator;
import univ.lab.model.Characteristics;
import univ.lab.model.Paper;
import univ.lab.model.Papers;

public class FillableCreatorFactory {
    public FillableCreator get() {
        FillableCreator fillableCreator = new FillableCreator();
        fillableCreator.add(Papers.class);
        fillableCreator.add(Paper.class);
        fillableCreator.add(Characteristics.class);
        return fillableCreator;
    }
}
