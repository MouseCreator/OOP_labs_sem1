package univ.lab.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Table(name = "car_insurances")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Data
public class CarInsurance extends Insurance{
    private String carNumber;
    private Long carPriceDollars;
}
