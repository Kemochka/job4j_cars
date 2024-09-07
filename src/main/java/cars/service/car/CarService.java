package cars.service.car;

import cars.model.Car;

import java.util.Collection;
import java.util.Optional;

public interface CarService {
    Car save(Car car);

    Collection<Car> findAll();

    Optional<Car> findById(int id);

    boolean update(Car car);

    boolean deleteById(int id);
}
