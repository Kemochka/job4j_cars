package cars.service.color;

import cars.model.Color;

import java.util.Collection;
import java.util.Optional;

public interface ColorService {
    Color save(Color color);

    Collection<Color> findAll();

    Optional<Color> findById(int id);

    boolean update(Color color);

    boolean deleteById(int id);
}
