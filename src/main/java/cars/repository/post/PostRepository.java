package cars.repository.post;

import cars.model.Brand;
import cars.model.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Optional<Post> findById(int id);

    boolean update(Post post);

    boolean deleteById(int id);

    Collection<Post> findAll();

    Collection<Post> findPostsFromTheLastDay();

    Collection<Post> findPostsWithPhoto();

    Collection<Post> findPostsWithBrand(Brand brand);
}
