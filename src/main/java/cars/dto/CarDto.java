package cars.dto;

import cars.model.Brand;
import cars.model.Color;
import cars.model.Engine;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarDto {
    private int id;
    private String name;
    private Color color;
    private Engine engine;
    private Brand brand;
}
