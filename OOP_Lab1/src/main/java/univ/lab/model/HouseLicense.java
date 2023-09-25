package univ.lab.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="house_licenses")
public class HouseLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
}
