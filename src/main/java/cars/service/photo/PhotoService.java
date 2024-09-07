package cars.service.photo;

import cars.dto.PhotoDto;
import cars.model.Photo;

import java.util.Optional;

public interface PhotoService {
    Photo save(PhotoDto photoDto);

    Optional<PhotoDto> findById(int id);

    void deleteById(int id);
}
