package cars.service.car;

import cars.dto.CarDto;
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
    public Collection<CarDto> findAll() {
        Collection<Car> cars = carRepository.findAll();
        return cars.stream().map(this::covertToDto).toList();
    }

    @Override
    public Optional<CarDto> findById(int id) {
        return carRepository.findById(id).map(this::covertToDto);
    }

    @Override
    public boolean update(Car car) {
        return carRepository.update(car);
    }

    @Override
    public boolean deleteById(int id) {
        return carRepository.deleteById(id);
    }

    public CarDto covertToDto(Car car) {
        return new CarDto(car.getId(), car.getName(), car.getColor(), car.getEngine(), car.getBrand());
    }
}
