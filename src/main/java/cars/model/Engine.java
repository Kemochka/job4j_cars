package cars.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "engines")
@Data
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
