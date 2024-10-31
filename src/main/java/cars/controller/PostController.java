package cars.controller;

import cars.converter.CarConverter;
import cars.dto.CarDto;
import cars.dto.PhotoDto;
import cars.dto.PostDto;
import cars.model.Car;
import cars.model.Post;
import cars.model.User;
import cars.service.car.CarService;
import cars.service.photo.PhotoService;
import cars.service.post.PostService;
import cars.service.price.PriceHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final CarService carService;
    private final PhotoService photoService;
    private final PriceHistoryService priceHistoryService;

    @GetMapping
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("cars", carService.findAll());
        return "posts/create";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Post not found");
            return "errors/404";
        }
        Post post = postOptional.get();
        PostDto postDto = postService.convertToDto(post);
        model.addAttribute("post", postDto);
        model.addAttribute("photos", photoService.findByPostId(postOptional.get().getId()));
        model.addAttribute("priceHistories", priceHistoryService.findAllLastPriceByPostId(postOptional.get().getId()));
        return "posts/one";
    }

    @GetMapping("delete/{id}")
    public String deleteById(Model model, @PathVariable int id) {
        boolean isDeleted = postService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Post is not deleted");
        }
        return "redirect:/posts";
    }

    @PostMapping("/create")
    public String create(@RequestParam int carId, @ModelAttribute Post post, @SessionAttribute User user, @RequestParam MultipartFile file) throws IOException {
        post.setUser(user);
        CarDto carDto = carService.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));
        Car car = CarConverter.convertToCar(carDto);
        post.setCar(car);
        PhotoDto photoDto = new PhotoDto(file.getOriginalFilename(), file.getBytes());
        postService.save(post, photoDto);
        return "redirect:/posts";
    }

    @GetMapping("update/{id}")
    public String editById(Model model, @PathVariable int id) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Post not found");
            return "errors/404";
        }
        model.addAttribute("post", postOptional.get());
        model.addAttribute("photos", photoService.findByPostId(postOptional.get().getId()));
        return "posts/edit";
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute Post post, @RequestParam MultipartFile file, @SessionAttribute User user) throws IOException {
        post.setUser(user);
        post.setPriceHistory(priceHistoryService.findAllLastPriceByPostId(post.getId()));
        var isUpdated = postService.update(post, new PhotoDto(file.getOriginalFilename(), file.getBytes()));
        if (!isUpdated) {
            model.addAttribute("message", "Post not update");
            return "errors/404";
        }
        return "redirect:/posts";
    }
}
