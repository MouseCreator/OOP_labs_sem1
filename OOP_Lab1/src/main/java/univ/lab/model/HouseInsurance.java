package univ.lab.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "house_insurances")
public class HouseInsurance extends Insurance {
    @OneToOne
    private HouseLicense houseLicense;
}
