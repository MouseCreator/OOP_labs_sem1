package univ.lab.service;

import univ.lab.dao.DerivativeDao;
import univ.lab.model.Derivative;
import univ.lab.model.Insurance;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DerivativeServiceImpl implements DerivativeService {

    private final DerivativeDao derivativeDao;

    public DerivativeServiceImpl(DerivativeDao derivativeDao) {
        this.derivativeDao = derivativeDao;
    }

    @Override
    public Derivative save(Derivative entity) {
        return derivativeDao.save(entity);
    }

    @Override
    public Optional<Derivative> find(Long id) {
        return derivativeDao.findById(id);
    }

    @Override
    public List<Derivative> findAll() {
        return derivativeDao.findAll();
    }

    @Override
    public void delete(Derivative entity) {
        derivativeDao.delete(entity);
    }

    @Override
    public void delete(Long id) {
        derivativeDao.delete(id);
    }

    @Override
    public Derivative update(Derivative entity) {
        return derivativeDao.update(entity);
    }

    @Override
    public void addInsuranceToDerivative(Long derivativeId, Insurance insurance) {
        Optional<Derivative> derivativeOptional = find(derivativeId);
        if (derivativeOptional.isEmpty()) {
            throw new IllegalArgumentException("Cannot find derivative with id " + derivativeId);
        }
        Derivative derivative = derivativeOptional.get();
        derivative.getInsurances().add(insurance);
        update(derivative);
    }

    @Override
    public void removeInsuranceFromDerivative(Long derivativeId, Insurance insurance) {
        Optional<Derivative> derivativeOptional = find(derivativeId);
        if (derivativeOptional.isEmpty()) {
            throw new IllegalArgumentException("Cannot find derivative with id " + derivativeId);
        }
        Derivative derivative = derivativeOptional.get();
        derivative.getInsurances().remove(insurance);
        update(derivative);
    }

    @Override
    public Long calculatePrice(Long derivativeId) {
        Optional<Derivative> derivativeOptional = find(derivativeId);
        if (derivativeOptional.isEmpty()) {
            throw new IllegalArgumentException("Cannot find derivative with id " + derivativeId);
        }
        Derivative derivative = derivativeOptional.get();
        return derivative.getInsurances()
                .stream()
                .mapToLong(Insurance::getPrice)
                .sum();
    }

    @Override
    public List<Insurance> getInsurancesSorted(Long derivativeId) {
        Optional<Derivative> derivativeOptional = find(derivativeId);
        if (derivativeOptional.isEmpty()) {
            throw new IllegalArgumentException("Cannot find derivative with id " + derivativeId);
        }
        Derivative derivative = derivativeOptional.get();
        Comparator<Insurance> insuranceComparator = Comparator.comparingInt(Insurance::getRisk);
        List<Insurance> insurances = derivative.getInsurances();
        insurances.sort(insuranceComparator);
        return insurances;
    }
}
