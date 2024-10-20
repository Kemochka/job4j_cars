package cars.converter;

import cars.dto.CarDto;
import cars.model.Car;

public class CarConverter {
    public static CarDto convertToDto(Car car) {
        return new CarDto(
                car.getId(),
                car.getName(),
                car.getColor(),
                car.getEngine(),
                car.getBrand()
        );
    }

    public static Car convertToCar(CarDto carDto) {
        Car car = new Car();
        car.setId(carDto.getId());
        car.setName(carDto.getName());
        car.setColor(carDto.getColor());
        car.setEngine(carDto.getEngine());
        car.setBrand(carDto.getBrand());
        return car;
    }
}
