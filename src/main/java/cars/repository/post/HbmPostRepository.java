package cars.repository.post;

import cars.model.Brand;
import cars.model.Post;
import cars.repository.CrudRepository;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmPostRepository implements PostRepository {
    private final CrudRepository crudRepository;
    private static final Logger LOGGER = Logger.getLogger(HbmPostRepository.class);

    @Override
    public Post save(Post post) {
        try {
            crudRepository.run(session -> session.save(post));
        } catch (Exception e) {
            LOGGER.error("Exception during save post", e);
        }
        return post;
    }

    @Override
    public Optional<Post> findById(int id) {
        return crudRepository.optional("from Post where id = :fId", Post.class, Map.of("fId", id));
    }

    @Override
    public boolean update(Post post) {
        boolean result = false;
        try {
            crudRepository.run(session -> session.merge(post));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Exception during update post", e);
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = false;
        try {
            crudRepository.run("delete from Post where id = :fId", Map.of("fId", id));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Exception during delete post by id");
        }
        return result;
    }

    @Override
    public Collection<Post> findAll() {
        return crudRepository.query("from Post as p join fetch p.priceHistory", Post.class);
    }

    @Override
    public Collection<Post> findPostsFromTheLastDay() {
        return crudRepository.query("from Post where created >= :fDate", Post.class, Map.of("fDate", LocalDateTime.now().minusDays(1)));
    }

    @Override
    public Collection<Post> findPostsWithPhoto() {
        return crudRepository.query("from p Post where p.photo.size !=0", Post.class);
    }

    @Override
    public Collection<Post> findPostsWithBrand(Brand brand) {
        return crudRepository.query("from p Post where p.brand.name = :fBrand", Post.class, Map.of("fBrand", brand));
    }
}
