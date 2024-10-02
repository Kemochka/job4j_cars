package cars.controller;

import cars.service.photo.PhotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/photos")
public class PhotoController {
    private final PhotoService photoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        var contentOptional = photoService.findById(id);
        if (contentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        System.out.println("Fetching photo with ID: " + id);
        return ResponseEntity.ok(contentOptional.get().getContent());
    }
}
