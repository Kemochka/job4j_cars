package cars.repository.car;

import cars.model.Car;
import cars.repository.CrudRepository;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AllArgsConstructor
class HbmCarRepositoryTest {
    private static CarRepository carRepository;

    @BeforeAll
    public static void initRepositories() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sf);
        carRepository = new HbmCarRepository(crudRepository);
    }

    @Test
    public void whenSaveCarThenFindTheSameCar() {
        Car car = new Car();
        car.setName("VW");
        carRepository.save(car);
        Optional<Car> rsl = carRepository.findById(car.getId());
        assertTrue(rsl.isPresent());
        assertThat(rsl.get().getName()).isEqualTo(car.getName());
    }

    @Test
    public void whenUpdateCarThenYouGetNewCar() {
        Car car = new Car();
        car.setName("VW");
        carRepository.save(car);
        car.setName("Audi");
        carRepository.update(car);
        Optional<Car> rsl = carRepository.findById(car.getId());
        assertTrue(rsl.isPresent());
        assertThat(rsl.get().getName()).isEqualTo("Audi");
    }

    @Test
    public void whenDeleteCarThenThereIsNoCar() {
        Car car = new Car();
        car.setName("VW");
        carRepository.save(car);
        Optional<Car> rsl = carRepository.findById(car.getId());
        assertTrue(rsl.isPresent());
        carRepository.deleteById(car.getId());
        Optional<Car> rsl2 = carRepository.findById(car.getId());
        assertThat(rsl2).isNotPresent();
    }
}