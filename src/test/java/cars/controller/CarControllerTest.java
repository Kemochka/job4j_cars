package cars.controller;

import cars.dto.CarDto;
import cars.model.Brand;
import cars.model.Car;
import cars.model.Color;
import cars.model.Engine;
import cars.service.brand.BrandService;
import cars.service.car.CarService;
import cars.service.color.ColorService;
import cars.service.engine.EngineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CarControllerTest {
    CarController carController;
    CarService carService;
    BrandService brandService;
    ColorService colorService;
    EngineService engineService;

    @BeforeEach
    public void initServices() {
        carService = mock(CarService.class);
        colorService = mock(ColorService.class);
        brandService = mock(BrandService.class);
        engineService = mock(EngineService.class);
        carController = new CarController(carService, brandService, colorService, engineService);
    }

    @Test
    public void whenGetAllCarsAndReturnsCarsView() {
        var car = new CarDto(1, "car", new Color(), new Engine(), new Brand());
        when(carService.findAll()).thenReturn(List.of(car));
        var model = new ConcurrentModel();
        var view = carController.getAll(model);
        assertThat(view).isEqualTo("cars/list");
    }

    @Test
    public void whenGetCreationPageThenReturnsCreateView() {
        when(colorService.findAll()).thenReturn(List.of(new Color()));
        when(brandService.findAll()).thenReturn(List.of(new Brand()));
        when(engineService.findAll()).thenReturn(List.of(new Engine()));
        var model = new ConcurrentModel();
        var view = carController.getCreationPage(model);
        assertThat(view).isEqualTo("cars/create");
    }

    @Test
    public void whenGetCarByIdThenReturnsCarView() {
        var car = new CarDto(1, "car", new Color(), new Engine(), new Brand());
        when(carService.findById(1)).thenReturn(Optional.of(car));
        var model = new ConcurrentModel();
        var view = carController.getById(model, 1);
        assertThat(view).isEqualTo("cars/one");
        assertThat(model.getAttribute("car")).isEqualTo(car);
    }

    @Test
    public void whenGetCarByIdNotFoundThenReturns404View() {
        when(carService.findById(anyInt())).thenReturn(Optional.empty());
        var model = new ConcurrentModel();
        var view = carController.getById(model, 1);
        assertThat(view).isEqualTo("errors/404");
        assertThat(model.getAttribute("message")).isEqualTo("Car not found");
    }

    @Test
    public void whenUpdateCarNotFoundThenReturns404View() {
        when(carService.update(any())).thenReturn(false);
        var model = new ConcurrentModel();
        var car = new Car();
        var view = carController.update(model, car);
        assertThat(view).isEqualTo("errors/404");
    }

    @Test
    public void whenDeleteCarThenRedirectsToCarsList() {
        when(carService.deleteById(anyInt())).thenReturn(true);
        var model = new ConcurrentModel();
        var view = carController.delete(model, 1);
        assertThat(view).isEqualTo("redirect:/cars");
    }

    @Test
    public void whenDeleteCarNotFoundThenRedirectsToCarsListWithErrorMessage() {
        when(carService.deleteById(anyInt())).thenReturn(false);
        var model = new ConcurrentModel();
        var view = carController.delete(model, 1);
        assertThat(view).isEqualTo("redirect:/cars");
        assertThat(model.getAttribute("message")).isEqualTo("Car with id not found");
    }
}