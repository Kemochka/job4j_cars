package cars.service.post;

import cars.dto.PhotoDto;
import cars.model.Brand;
import cars.model.Post;
import cars.repository.post.PostRepository;
import cars.service.photo.PhotoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {
    private final PostRepository postRepository;
    private final PhotoService photoService;

    @Override
    public Post save(Post post, PhotoDto image) {
        saveNewFile(post, image);
        return postRepository.save(post);
    }

    private void saveNewFile(Post post, PhotoDto image) {
        var file = photoService.save(image);
        file.setPost(post);
        post.getPhotos().add(file);
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public boolean update(Post post) {
        return postRepository.update(post);
    }

    @Override
    public boolean deleteById(int id) {
        return postRepository.deleteById(id);
    }

    @Override
    public Collection<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Collection<Post> findPostsFromTheLastDay() {
        return postRepository.findPostsFromTheLastDay();
    }

    @Override
    public Collection<Post> findPostsWithPhoto() {
        return postRepository.findPostsWithPhoto();
    }

    @Override
    public Collection<Post> findPostsWithBrand(Brand brand) {
        return postRepository.findPostsWithBrand(brand);
    }
}
