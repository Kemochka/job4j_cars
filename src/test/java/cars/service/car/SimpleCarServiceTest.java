package cars.service.car;

import cars.model.Car;
import cars.repository.car.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleCarServiceTest {
    private SimpleCarService carService;
    private CarRepository carRepository;

    @BeforeEach
    public void init() {
        carRepository = mock(CarRepository.class);
        carService = new SimpleCarService(carRepository);
    }

    @Test
    public void whenSaveCarThenReturnSavedCar() {
        var car = new Car();
        car.setName("Tesla");
        when(carRepository.save(car)).thenReturn(car);
        var actual = carService.save(car);
        assertThat(actual).isEqualTo(car);
    }

    @Test
    public void whenFindCarByIdThenReturnEmpty() {
        when(carRepository.findById(1)).thenReturn(Optional.empty());
        var actual = carService.findById(1);
        assertThat(actual).isEmpty();
    }

    @Test
    public void whenUpdateCarThenReturnTrue() {
        var car = new Car();
        when(carRepository.update(car)).thenReturn(true);
        var actual = carService.update(car);
        assertThat(actual).isTrue();
    }

    @Test
    public void whenDeleteCarByIdThenReturnTrue() {
        when(carRepository.deleteById(1)).thenReturn(true);
        var actual = carService.deleteById(1);
        assertThat(actual).isTrue();
    }

    @Test
    public void whenDeleteCarByIdThenReturnFalse() {
        when(carRepository.deleteById(1)).thenReturn(false);
        var actual = carService.deleteById(1);
        assertThat(actual).isFalse();
    }

    @Test
    public void whenCovertToDtoThenReturnCarDto() {
        var car = new Car();
        var actual = carService.covertToDto(car);
        assertThat(actual).isNotNull();
    }
}