package univ.lab.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "life_insurances")
public class LifeInsurance extends Insurance{
    private String riskFactor;
}
