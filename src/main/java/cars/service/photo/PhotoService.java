package cars.service.photo;

import cars.dto.PhotoDto;
import cars.model.Photo;

import java.util.List;
import java.util.Optional;

public interface PhotoService {
    Photo save(PhotoDto photoDto);

    Optional<PhotoDto> findById(int id);

    List<Photo> findByPostId(int postId);

    void deleteById(int id);
}
