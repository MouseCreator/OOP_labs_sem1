package univ.lab.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "owners")
public class InsuranceOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
