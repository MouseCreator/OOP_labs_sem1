package univ.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;
@Entity
@Data
@Table(name="derivatives")
@ToString(callSuper = true)
public class Derivative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "derivatives_insurances",
        joinColumns = @JoinColumn(name = "derivatives_id"),
        inverseJoinColumns = @JoinColumn(name = "insurances_id"))
    private List<Insurance> insurances;
}
