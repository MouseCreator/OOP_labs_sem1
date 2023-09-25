package univ.lab.factory;

import univ.lab.model.CarInsurance;
import univ.lab.model.HouseInsurance;
import univ.lab.model.Insurance;
import univ.lab.model.LifeInsurance;

public class InsuranceFactoryImpl implements InsuranceFactory {
    @Override
    public LifeInsurance getLifeInsurance(Integer risk, String owner, String riskFactor) {
        LifeInsurance lifeInsurance = new LifeInsurance();
        init(lifeInsurance, risk, owner);

        if (riskFactor != null) {
            lifeInsurance.setRiskFactor(riskFactor);
        } else {
            throw new IllegalArgumentException("Argument Risk factor cannot be null");
        }

        return lifeInsurance;
    }

    @Override
    public CarInsurance getCarInsurance(Integer risk, String owner, String number, Long price) {
        CarInsurance carInsurance = new CarInsurance();
        init(carInsurance, risk, owner);

        if (number != null) {
            carInsurance.setCarNumber(number);
        } else {
            throw new IllegalArgumentException("Argument Number factor cannot be null");
        }

        if (price != null) {
            carInsurance.setCarPriceDollars(price);
        } else {
            throw new IllegalArgumentException("Argument Number factor cannot be null");
        }

        return carInsurance;
    }

    @Override
    public HouseInsurance getHouseInsurance(Integer risk, String owner, String address) {
        HouseInsurance houseInsurance = new HouseInsurance();
        init(houseInsurance, risk, owner);

        if (address != null) {
            houseInsurance.setHouseAddress(address);
        } else {
            throw new IllegalArgumentException("Argument Number factor cannot be null");
        }
        return houseInsurance;
    }

    private void init(Insurance insurance, Integer risk, String owner) {
        if (risk != null) {
            insurance.setRisk(risk);
        } else {
            throw new IllegalArgumentException("Argument Risk cannot be null");
        }
        if (owner != null) {
            insurance.setOwnerName(owner);
        } else {
            throw new IllegalArgumentException("Argument Owner cannot be null");
        }
    }
}
