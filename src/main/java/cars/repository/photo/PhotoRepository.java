package cars.repository.photo;

import cars.model.Photo;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository {
    Photo save(Photo photo);

    Optional<Photo> findById(int id);

    List<Photo> findByPostId(int postId);

    void deleteById(int id);

    boolean deleteByPostId(int postId);
}
