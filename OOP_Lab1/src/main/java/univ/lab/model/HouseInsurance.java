package univ.lab.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "house_insurances")
@ToString(callSuper = true)
public class HouseInsurance extends Insurance {
    private String houseAddress;
}
