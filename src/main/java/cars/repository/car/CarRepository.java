package cars.repository.car;

import cars.model.Car;

import java.util.Collection;
import java.util.Optional;

public interface CarRepository {
    Car save(Car car);

    Collection<Car> findAll();

    Optional<Car> findById(int id);

    boolean update(Car car);

    boolean deleteById(int id);
}
