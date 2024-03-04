package univ.lab.service;

import univ.lab.model.Derivative;
import univ.lab.model.Insurance;

import java.util.List;
import java.util.function.Predicate;

public interface DerivativeService extends CrudService<Derivative> {
    void addInsuranceToDerivative(Long derivativeId, Insurance insurance);
    void removeInsuranceFromDerivative(Long derivativeId, Insurance insurance);
    Long calculatePrice(Long derivativeId);
    List<Insurance> getInsurancesSorted(Long derivativeId);
    List<Insurance> getInsurancesByParameters(Long derivativeId, List<Predicate<Insurance>> predicates);
}
