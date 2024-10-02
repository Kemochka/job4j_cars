package cars.controller;

import cars.model.Brand;
import cars.model.Car;
import cars.model.Color;
import cars.model.Engine;
import cars.service.brand.BrandService;
import cars.service.car.CarService;
import cars.service.color.ColorService;
import cars.service.engine.EngineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final BrandService brandService;
    private final ColorService colorService;
    private final EngineService engineService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("cars", carService.findAll());
        return "cars/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("colors", colorService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("engines", engineService.findAll());
        return "cars/create";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var carOptional = carService.findById(id);
        if (carOptional.isEmpty()) {
            model.addAttribute("message", "Car not found");
            return "errors/404";
        }
        model.addAttribute("car", carOptional.get());
        return "cars/one";
    }

    @GetMapping("update/{id}")
    public String editById(Model model, @PathVariable int id) {
        var carOptional = carService.findById(id);
        if (carOptional.isEmpty()) {
            model.addAttribute("message", "Car not found");
            return "errors/404";
        }
        model.addAttribute("car", carOptional.get());
        model.addAttribute("colors", colorService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("engines", engineService.findAll());
        return "cars/edit";
    }

    @GetMapping("delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        if (!carService.deleteById(id)) {
            model.addAttribute("message", "Car with id not found");
        }
        return "redirect:/cars";
    }

    @PostMapping("/create")
    public String create(Model model, @ModelAttribute Car car) {
        try {
            Color color = colorService.findById(car.getColor().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid color ID"));
            Brand brand = brandService.findById(car.getBrand().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid brand ID"));
            String engineName = car.getEngine().getName();
            Engine engine = engineService.findByName(engineName)
                    .orElseGet(() -> {
                        Engine newEngine = new Engine();
                        newEngine.setName(engineName);
                        return engineService.save(newEngine);
                    });
            car.setColor(color);
            car.setBrand(brand);
            car.setEngine(engine);

            carService.save(car);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
        return "redirect:/cars";
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute Car car) {
        try {
            Color color = colorService.findById(car.getColor().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid color ID"));
            Brand brand = brandService.findById(car.getBrand().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid brand ID"));
            String engineName = car.getEngine().getName();
            Engine engine = engineService.findByName(engineName)
                    .orElseGet(() -> {
                        Engine newEngine = new Engine();
                        newEngine.setName(engineName);
                        return engineService.save(newEngine);
                    });
            car.setColor(color);
            car.setBrand(brand);
            car.setEngine(engine);
            if (!carService.update(car)) {
                model.addAttribute("message", "Car not found");
                return "errors/404";
            }
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
        return "redirect:/cars";
    }
}
