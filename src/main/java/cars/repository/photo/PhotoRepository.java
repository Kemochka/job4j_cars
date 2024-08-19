package cars.repository.photo;

import cars.model.Photo;

import java.util.Optional;

public interface PhotoRepository {
    Photo save(Photo photo);
    Optional<Photo> findById(int id);
    void deleteById(int id);
}
