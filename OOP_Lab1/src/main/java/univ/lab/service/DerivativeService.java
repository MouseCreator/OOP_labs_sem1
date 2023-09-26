package univ.lab.service;

import univ.lab.model.Derivative;
import univ.lab.model.Insurance;

import java.util.List;

public interface DerivativeService extends CrudService<Derivative> {
    void addInsuranceToDerivative(Long derivativeId, Insurance insurance);
    void removeInsuranceFromDerivative(Long derivativeId, Insurance insurance);
    Long calculatePrice(Long derivativeId);
    List<Insurance> getInsurancesSorted(Long derivativeId);
}
