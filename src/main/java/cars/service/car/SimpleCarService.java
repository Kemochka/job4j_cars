package cars.service.car;

import cars.model.Car;
import cars.repository.car.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCarService implements CarService {
    private final CarRepository carRepository;

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Collection<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }

    @Override
    public boolean update(Car car) {
        return carRepository.update(car);
    }

    @Override
    public boolean deleteById(int id) {
        return carRepository.deleteById(id);
    }
}
