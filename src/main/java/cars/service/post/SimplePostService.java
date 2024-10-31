package cars.service.post;

import cars.converter.PostConverter;
import cars.dto.PhotoDto;
import cars.dto.PostDto;
import cars.model.Brand;
import cars.model.Post;
import cars.repository.post.PostRepository;
import cars.service.photo.PhotoService;
import cars.service.price.PriceHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {
    private final PostRepository postRepository;
    private final PhotoService photoService;
    private final PriceHistoryService priceHistoryService;
    private final PostConverter postConverter;

    @Override
    public Post save(Post post, PhotoDto image) {
        saveNewFile(post, image);
        Post savedPost = postRepository.save(post);
        priceHistoryService.create(savedPost, savedPost.getPrice());
        return savedPost;
    }

    public PostDto convertToDto(Post post) {
        return postConverter.convertToPostDto(post);
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
    public boolean update(Post post, PhotoDto image) {
        var isNewFileEmpty = image.getContent().length == 0;
        if (!isNewFileEmpty) {
            photoService.deleteByPostId(post.getId());
            saveNewFile(post, image);
        } else {
            var oldPhoto = photoService.findByPostId(post.getId());
            if (oldPhoto != null) {
                post.setPhotos(oldPhoto);
            }
        }
        var isUpdated = postRepository.update(post);
        post = postRepository.findById(post.getId()).orElseThrow(() -> new IllegalStateException("Post not found after update"));
        var lastPrice = priceHistoryService.findLastByPostId(post.getId());
        var price = post.getPrice();
        if (lastPrice.isPresent()) {
            if (lastPrice.get().getAfter() != price) {
                priceHistoryService.update(post, price);
            }
        }
        return isUpdated;
    }

    @Override
    public boolean deleteById(int id) {
        var postOptional = findById(id);
        postOptional.ifPresent(post -> photoService.deleteByPostId(post.getId()));
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
