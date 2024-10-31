package cars.dto;

import cars.model.Brand;
import cars.model.Color;
import cars.model.Engine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CarDto {
    private int id;
    private String name;
    private Color color;
    private Engine engine;
    private Brand brand;
}
