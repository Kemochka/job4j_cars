package cars.repository.car;

import cars.model.Car;
import cars.repository.CrudRepository;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmCarRepository implements CarRepository {
    private final CrudRepository crudRepository;
    private static final Logger LOGGER = Logger.getLogger(HbmCarRepository.class);

    @Override
    public Car save(Car car) {
        try {
            crudRepository.run(session -> session.save(car));
        } catch (Exception e) {
            LOGGER.error("Exception during save car", e);
        }
        return car;
    }

    @Override
    public Collection<Car> findAll() {
        return crudRepository.query("from Car", Car.class);
    }

    @Override
    public Optional<Car> findById(int id) {
        return crudRepository.optional("from Car where id = :fId", Car.class, Map.of("fId", id));
    }

    @Override
    public boolean update(Car car) {
        boolean result = false;
        try {
            crudRepository.run(session -> session.merge(car));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Exception during update car", e);
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = false;
        try {
            crudRepository.run("delete from Car where id = :fId", Map.of("fId", id));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Exception during delete car by id");
        }
        return result;
    }
}
