package cars.repository.photo;

import cars.model.Photo;
import cars.repository.CrudRepository;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmPhotoRepository implements PhotoRepository {
    private final CrudRepository crudRepository;
    private static final Logger LOGGER = Logger.getLogger(HbmPhotoRepository.class);

    @Override
    public Photo save(Photo photo) {
        try {
            crudRepository.run(session -> session.save(photo));
        } catch (Exception e) {
            LOGGER.error("Exception during save photo", e);
        }
        return photo;
    }

    @Override
    public Optional<Photo> findById(int id) {
        return crudRepository.optional("from Photo where id = :fId", Photo.class, Map.of("fId", id));
    }

    @Override
    public List<Photo> findByPostId(int postId) {
        return crudRepository.query("from Photo where post.id = :fPostId", Photo.class, Map.of("fPostId", postId));
    }

    @Override
    public void deleteById(int id) {
        crudRepository.run("delete from Photo where id = :fId", Map.of("fId", id));
    }
}
