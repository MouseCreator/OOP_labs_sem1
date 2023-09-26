package univ.lab.controller;

import univ.lab.dao.CrudDaoManagerImpl;
import univ.lab.dao.DerivativeDaoImpl;
import univ.lab.dao.InsuranceDaoImpl;
import univ.lab.factory.DerivativeFactory;
import univ.lab.factory.DerivativeFactoryImpl;
import univ.lab.factory.InsuranceFactory;
import univ.lab.factory.InsuranceFactoryImpl;
import univ.lab.model.*;
import univ.lab.service.DerivativeService;
import univ.lab.service.DerivativeServiceImpl;
import univ.lab.service.InsuranceService;
import univ.lab.service.InsuranceServiceImpl;
import univ.lab.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;

public class DatabaseController {
    private final DerivativeService derivativeService =
            new DerivativeServiceImpl(new DerivativeDaoImpl(HibernateUtil.getSessionFactory(),
                    new CrudDaoManagerImpl<>(HibernateUtil.getSessionFactory(), Derivative.class)));
    private final DerivativeFactory derivativeFactory = new DerivativeFactoryImpl();
    private final InsuranceService insuranceService = new InsuranceServiceImpl(
            new InsuranceDaoImpl(
                    new CrudDaoManagerImpl<>(HibernateUtil.getSessionFactory(), Insurance.class)));
    private final InsuranceFactory insuranceFactory = new InsuranceFactoryImpl();
    public void start() {
        injectData();
        doMainLoop();
    }

    private void doMainLoop() {
        while (true) {
            try {
                String command = getUserCommand();
                command = unifyCommand(command);
                switch (command) {
                    case "exit" -> {
                        return;
                    }
                    case "add_insurance", "ai" -> addInsurance();
                    case "add_derivative", "ad" -> addDerivative();
                    case "show_insurances", "si" -> showInsurances();
                    case "show_derivatives", "sd" -> showDerivatives();
                    case "delete_insurance", "di" -> deleteInsurance();
                    case "delete_derivative", "dd" -> deleteDerivative();
                    case "put", "p" -> putInsuranceInDerivative();
                    case "remove", "r" -> removeInsuranceFromDerivative();
                    case "sum", "s" -> calculateSum();
                    case "sort", "st" -> getAndSort();
                    case "get", "g" -> getByParameters();
                    default -> System.out.println("Unknown command");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getByParameters() {
        Long derivative = requestLong("Derivative ID: ");
        List<Predicate<Insurance>> properties = new ArrayList<>();

        String requestLowerRisk = requestString("Risk (lower): ");
        if (!requestLowerRisk.equals("any")) {
            long rLow = Long.parseLong(requestLowerRisk);
            Predicate<Insurance> predicate = insurance -> insurance.getRisk() > rLow;
            properties.add(predicate);
        }

        String requestUpperRisk = requestString("Risk (higher): ");
        if (!requestUpperRisk.equals("any")) {
            long rHigh = Long.parseLong(requestUpperRisk);
            Predicate<Insurance> predicate = insurance -> insurance.getRisk() < rHigh;
            properties.add(predicate);
        }

        String requestLowerPrice = requestString("Price (Lower): ");
        if (!requestLowerPrice.equals("any")) {
            long priceLower = Long.parseLong(requestLowerPrice);
            Predicate<Insurance> predicate = insurance -> insurance.getPrice() > priceLower;
            properties.add(predicate);
        }

        String requestUpperPrice = requestString("Price (Higher): ");
        if (!requestUpperPrice.equals("any")) {
            long priceLower = Long.parseLong(requestUpperPrice);
            Predicate<Insurance> predicate = insurance -> insurance.getPrice() < priceLower;
            properties.add(predicate);
        }

        String requestName = requestString("Owner name: ");
        if (!requestUpperPrice.equals("any")) {
            Predicate<Insurance> predicate = insurance -> insurance.getOwnerName().equals(requestName);
            properties.add(predicate);
        }

        List<Insurance> insurancesByParameters = derivativeService.getInsurancesByParameters(derivative, properties);
        System.out.println(insurancesByParameters);
    }

    private void getAndSort() {
        Long derivative = requestLong("Derivative ID: ");
        List<Insurance> insurancesSorted = derivativeService.getInsurancesSorted(derivative);
        System.out.println(insurancesSorted);
    }

    private void calculateSum() {
        Long derivative = requestLong("Derivative ID: ");
        Long sum = derivativeService.calculatePrice(derivative);
        System.out.println("Sum: " + sum);
    }

    private void removeInsuranceFromDerivative() {
        Long insuranceId = requestLong("Insurance ID: ");
        Optional<Insurance> insurance = insuranceService.find(insuranceId);
        if (insurance.isEmpty()) {
            System.out.println("No such ID!");
            return;
        }
        Long derivative = requestLong("Derivative ID: ");
        derivativeService.removeInsuranceFromDerivative(derivative, insurance.get());
    }

    private void putInsuranceInDerivative() {
        Long insuranceId = requestLong("Insurance ID: ");
        Optional<Insurance> insurance = insuranceService.find(insuranceId);
        if (insurance.isEmpty()) {
            System.out.println("No such ID!");
            return;
        }
        Long derivative = requestLong("Derivative ID: ");
        derivativeService.addInsuranceToDerivative(derivative, insurance.get());
    }


    private void deleteDerivative() {
        Long id = requestLong("ID: ");
        try {
            derivativeService.delete(id);
        } catch (Exception e) {
            System.out.println("Cannot delete derivative with id " + id);
        }
    }

    private void deleteInsurance() {
        Long id = requestLong("ID: ");
        try {
            derivativeService.delete(id);
        } catch (Exception e) {
            System.out.println("Cannot delete insurance with id " + id);
        }
    }

    private void showDerivatives() {
        System.out.println(derivativeService.findAll());
    }

    private void showInsurances() {
        System.out.println(insuranceService.findAll());
    }

    private void addDerivative() {
        Derivative derivative = derivativeFactory.getDerivative();
        derivativeService.save(derivative);
    }

    private void addInsurance() {
        System.out.println("Adding insurance:");
        String type = requestString("Type: ");
        switch (type) {
            case "car" -> addCarInsurance();
            case "life" -> addLifeInsurance();
            case "house" -> addHouseInsurance();
            default -> System.out.println("Unknown type");
        }
    }

    private void addHouseInsurance() {
        Integer risk = requestInteger("Risk: ");
        String owner = requestString("Owner: ");
        Long price = requestLong("Price: ");
        String address = requestString("Address: ");
        HouseInsurance houseInsurance = insuranceFactory.getHouseInsurance(risk, owner, price, address);
        insuranceService.save(houseInsurance);
    }

    private void addLifeInsurance() {
        Integer risk = requestInteger("Risk: ");
        String owner = requestString("Owner: ");
        Long price = requestLong("Price: ");
        String riskFactor = requestString("Risk factor: ");
        LifeInsurance lifeInsurance = insuranceFactory.getLifeInsurance(risk, owner, price, riskFactor);
        insuranceService.save(lifeInsurance);
    }

    private void addCarInsurance() {
        Integer risk = requestInteger("Risk: ");
        String owner = requestString("Owner: ");
        Long price = requestLong("Price: ");
        String number = requestString("Car number: ");
        Long carPrice = requestLong("Car price: ");
        CarInsurance carInsurance = insuranceFactory.getCarInsurance(risk, owner, price, number, carPrice);
        insuranceService.save(carInsurance);
    }

    private String unifyCommand(String command) {
        return command.trim().toLowerCase();
    }

    private String getUserCommand() {
        return requestString("> ");
    }

    private String requestString(String prompt) {
        try(Scanner scanner = new Scanner(System.in)) {
            do {
                System.out.print(prompt);
                String line = scanner.nextLine();
                if (line == null || line.isEmpty())
                    continue;
                return unifyCommand(line);
            } while (true);
        }
    }

    private Long requestLong(String prompt) {
        try(Scanner scanner = new Scanner(System.in)) {
            do {
                System.out.print(prompt);
                String line = scanner.nextLine();
                if (line == null || line.isEmpty())
                    continue;
                long longV;
                try {
                    longV = Long.parseLong(line);
                } catch (Exception e) {
                    continue;
                }
                return longV;
            } while (true);
        }
    }

    private Integer requestInteger(String prompt) {
        try(Scanner scanner = new Scanner(System.in)) {
            do {
                System.out.print(prompt);
                String line = scanner.nextLine();
                if (line == null || line.isEmpty())
                    continue;
                int intV;
                try {
                    intV = Integer.parseInt(line);
                } catch (Exception e) {
                    continue;
                }
                return intV;
            } while (true);
        }
    }

    private void injectData() {
        Insurance insurance1 = insuranceService.save(insuranceFactory.getLifeInsurance(5, "Alice", 200L, "Ice"));
        Insurance insurance2 = insuranceService.save(insuranceFactory.getCarInsurance(4, "Bob", 100L, "AA 00 BB", 200L));
        Insurance insurance3 = insuranceService.save(insuranceFactory.getCarInsurance(3, "Carl", 50L, "CC 00 DD", 170L));
        Derivative derivative1 = derivativeFactory.getDerivative();
        derivative1.getInsurances().add(insurance1);
        derivative1.getInsurances().add(insurance2);
        derivative1.getInsurances().add(insurance3);
        derivativeService.save(derivative1);

        Insurance insurance4 = insuranceService.save(insuranceFactory.getHouseInsurance(5, "Alice", 80L, "Vito street"));
        Insurance insurance5 = insuranceService.save( insuranceFactory.getCarInsurance(2, "Bill", 20L, "EE 00 FF", 30L));

        Derivative derivative2 = derivativeFactory.getDerivative();
        derivative2.getInsurances().add(insurance4);
        derivative2.getInsurances().add(insurance5);
        derivativeService.save(derivative2);
        Derivative derivative3 = derivativeFactory.getDerivative();
        derivativeService.save(derivative3);
    }
}
