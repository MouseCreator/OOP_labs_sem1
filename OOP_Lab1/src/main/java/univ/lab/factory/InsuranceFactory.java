package univ.lab.factory;

import univ.lab.model.CarInsurance;
import univ.lab.model.HouseInsurance;
import univ.lab.model.LifeInsurance;

public interface InsuranceFactory {
    LifeInsurance getLifeInsurance(Integer risk, String owner, String riskFactor);
    CarInsurance getCarInsurance(Integer risk, String owner, String number, Long price);
    HouseInsurance getHouseInsurance(Integer risk, String owner, String address);
}
