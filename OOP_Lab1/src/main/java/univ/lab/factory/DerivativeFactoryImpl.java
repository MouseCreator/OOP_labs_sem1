package univ.lab.factory;

import univ.lab.model.Derivative;

import java.util.ArrayList;

public class DerivativeFactoryImpl implements DerivativeFactory {
    @Override
    public Derivative getDerivative() {
        Derivative derivative = new Derivative();
        derivative.setInsurances(new ArrayList<>());
        return derivative;
    }
}
