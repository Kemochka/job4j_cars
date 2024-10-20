package cars.service.post;

import cars.dto.PhotoDto;
import cars.dto.PostDto;
import cars.model.Brand;
import cars.model.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostService {
    Post save(Post post, PhotoDto image);

    Optional<Post> findById(int id);

    boolean update(Post post, PhotoDto image);

    boolean deleteById(int id);

    Collection<Post> findAll();

    Collection<Post> findPostsFromTheLastDay();

    Collection<Post> findPostsWithPhoto();

    Collection<Post> findPostsWithBrand(Brand brand);

    PostDto convertToDto(Post post);
}
