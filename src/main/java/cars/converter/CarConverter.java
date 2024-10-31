package cars.converter;

import cars.dto.CarDto;
import cars.model.Car;

public class CarConverter {
    public static CarDto convertToDto(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .name(car.getName())
                .color(car.getColor())
                .engine(car.getEngine())
                .brand(car.getBrand())
                .build();
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
