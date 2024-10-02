package cars.repository.color;

import cars.model.Color;
import cars.repository.CrudRepository;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmColorRepository implements ColorRepository {
    private final CrudRepository crudRepository;
    private final static Logger LOGGER = Logger.getLogger(ColorRepository.class);

    @Override
    public Color save(Color color) {
        try {
            crudRepository.run(session -> session.save(color));
        } catch (Exception e) {
            LOGGER.error("Exception during save color");
        }
        return color;
    }

    @Override
    public Collection<Color> findAll() {
        return crudRepository.query("from Color", Color.class);
    }

    @Override
    public Optional<Color> findById(int id) {
        return crudRepository.optional("from Color where id = :fId", Color.class, Map.of("fId", id));
    }

    @Override
    public boolean update(Color color) {
        boolean result = false;
        try {
            crudRepository.run(session -> session.merge(color));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Exception during update color");
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = false;
        try {
            crudRepository.run("delete from Color where id = :fId", Map.of("fId", id));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Exception during delete color by id");
        }
        return result;
    }
}
