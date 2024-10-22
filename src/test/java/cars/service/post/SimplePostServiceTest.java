package cars.service.post;

import cars.converter.PostConverter;
import cars.dto.PhotoDto;
import cars.model.Brand;
import cars.model.Photo;
import cars.model.Post;
import cars.repository.post.PostRepository;
import cars.service.photo.PhotoService;
import cars.service.price.PriceHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class SimplePostServiceTest {
    private SimplePostService postService;
    private PostRepository postRepository;
    private PhotoService photoService;
    private PriceHistoryService priceHistoryService;

    @BeforeEach
    public void init() {
        postRepository = mock(PostRepository.class);
        photoService = mock(PhotoService.class);
        priceHistoryService = mock(PriceHistoryService.class);
        PostConverter postConverter = mock(PostConverter.class);
        postService = new SimplePostService(postRepository, photoService, priceHistoryService, postConverter);
    }

    @Test
    public void whenSavePostThenReturnSavedPost() {
        var post = new Post();
        var photoDto = new PhotoDto("image.jpg", new byte[]{1, 2, 3});
        var savedPost = new Post();
        savedPost.setId(1);
        when(postRepository.save(post)).thenReturn(savedPost);
        when(photoService.save(any(PhotoDto.class))).thenReturn(new Photo());
        var actual = postService.save(post, photoDto);
        assertThat(actual).isEqualTo(savedPost);
    }

    @Test
    public void whenFindByIdThenReturnPost() {
        var post = new Post();
        post.setId(1);
        when(postRepository.findById(1)).thenReturn(Optional.of(post));
        var actual = postService.findById(1);
        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(post);
        verify(postRepository, times(1)).findById(1);
    }

    @Test
    public void whenFindByIdThenReturnEmpty() {
        when(postRepository.findById(1)).thenReturn(Optional.empty());
        var actual = postService.findById(1);
        assertThat(actual).isEmpty();
    }

    @Test
    public void whenUpdatePostThenReturnTrue() {
        var post = new Post();
        var photoDto = new PhotoDto("image.jpg", new byte[]{1, 2, 3});
        when(postRepository.update(post)).thenReturn(true);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(photoService.save(any(PhotoDto.class))).thenReturn(new Photo());
        when(priceHistoryService.findLastByPostId(post.getId())).thenReturn(Optional.empty());
        var isUpdated = postService.update(post, photoDto);
        assertThat(isUpdated).isTrue();
    }

    @Test
    public void whenUpdatePostWithEmptyImageThenKeepOldPhoto() {
        var post = new Post();
        post.setId(1);
        var emptyPhotoDto = new PhotoDto("image.jpg", new byte[]{});
        var oldPhotos = List.of(new Photo());
        when(postRepository.update(post)).thenReturn(true);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(photoService.findByPostId(post.getId())).thenReturn(oldPhotos);
        when(priceHistoryService.findLastByPostId(post.getId())).thenReturn(Optional.empty());
        var isUpdated = postService.update(post, emptyPhotoDto);
        assertThat(isUpdated).isTrue();
        assertThat(post.getPhotos()).isEqualTo(oldPhotos);
    }

    @Test
    public void whenDeletePostThenReturnTrue() {
        when(postRepository.deleteById(1)).thenReturn(true);
        var isDeleted = postService.deleteById(1);
        assertThat(isDeleted).isTrue();
    }

    @Test
    public void whenFindAllPostsThenReturnPosts() {
        var post1 = new Post();
        var post2 = new Post();
        var posts = List.of(post1, post2);
        when(postRepository.findAll()).thenReturn(posts);
        var actual = postService.findAll();
        assertThat(actual).isEqualTo(posts);
    }

    @Test
    public void whenFindPostsWithBrandThenReturnPosts() {
        var brand = new Brand();
        var post1 = new Post();
        var post2 = new Post();
        var posts = List.of(post1, post2);
        when(postRepository.findPostsWithBrand(brand)).thenReturn(posts);
        var actual = postService.findPostsWithBrand(brand);
        assertThat(actual).isEqualTo(posts);
    }
}