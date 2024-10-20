package cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostDto {
    private int id;
    private CarDto car;
    private String description;
    private LocalDateTime created;
    private boolean sold;
    private long price;
}
