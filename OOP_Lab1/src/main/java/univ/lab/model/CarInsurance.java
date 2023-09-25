package univ.lab.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Table(name = "car_insurances")
@EqualsAndHashCode(callSuper = false)
@Data
public class CarInsurance extends Insurance{
    @OneToOne
    private CarLicense carLicense;
}
