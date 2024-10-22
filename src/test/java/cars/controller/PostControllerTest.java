package cars.controller;

import cars.dto.CarDto;
import cars.model.*;
import cars.service.car.CarService;
import cars.service.photo.PhotoService;
import cars.service.post.PostService;
import cars.service.price.PriceHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostControllerTest {
    private PostService postService;
    private CarService carService;
    private PhotoService photoService;
    private PriceHistoryService priceHistoryService;
    private PostController postController;

    @BeforeEach
    public void initServices() {
        postService = mock(PostService.class);
        carService = mock(CarService.class);
        photoService = mock(PhotoService.class);
        priceHistoryService = mock(PriceHistoryService.class);
        postController = new PostController(postService, carService, photoService, priceHistoryService);
    }

    @Test
    public void whenGetAllAndReturnsPostsView() {
        var post = new Post();
        when(postService.findAll()).thenReturn(List.of(post));
        var model = new ConcurrentModel();
        var view = postController.getAllPosts(model);
        assertThat(view).isEqualTo("posts/list");
    }

    @Test
    public void whenGetCreationPageThenReturnsCreationView() {
        when(postService.findAll()).thenReturn(List.of(new Post()));
        when(carService.findAll()).thenReturn(List.of(new CarDto(1, "1", new Color(), new Engine(), new Brand())));
        var model = new ConcurrentModel();
        String view = postController.getCreationPage(model);
        assertThat(view).isEqualTo("posts/create");
        assertThat(model.getAttribute("posts")).isNotNull();
        assertThat(model.getAttribute("cars")).isNotNull();
    }

    @Test
    public void whenGetByIdPostExistsThenReturnsPostView() {
        var post = new Post();
        post.setId(1);
        when(postService.findById(1)).thenReturn(Optional.of(post));
        when(photoService.findByPostId(1)).thenReturn(List.of(new Photo()));
        when(priceHistoryService.findAllLastPriceByPostId(1)).thenReturn(List.of());
        var model = new ConcurrentModel();
        String view = postController.getById(model, 1);
        assertThat(view).isEqualTo("posts/one");
        assertThat(model.getAttribute("photos")).isNotNull();
        assertThat(model.getAttribute("priceHistories")).isNotNull();
    }

    @Test
    public void whenGetByIdPostNotFoundThenReturns404View() {
        when(postService.findById(1)).thenReturn(Optional.empty());
        var model = new ConcurrentModel();
        String view = postController.getById(model, 1);
        assertThat(view).isEqualTo("errors/404");
        assertThat(model.getAttribute("message")).isEqualTo("Post not found");
    }

    @Test
    public void whenDeleteByIdPostExistsThenRedirectsToPosts() {
        var post = new Post();
        post.setId(1);
        when(postService.findById(1)).thenReturn(Optional.of(post));
        when(postService.deleteById(1)).thenReturn(true);
        var model = new ConcurrentModel();
        String view = postController.deleteById(model, 1);
        assertThat(view).isEqualTo("redirect:/posts");
    }

    @Test
    public void whenDeleteByIdPostNotDeletedThenReturnsRedirectWithMessage() {
        var post = new Post();
        post.setId(1);
        when(postService.findById(1)).thenReturn(Optional.of(post));
        when(postService.deleteById(1)).thenReturn(false);
        var model = new ConcurrentModel();
        String view = postController.deleteById(model, 1);
        assertThat(view).isEqualTo("redirect:/posts");
        assertThat(model.getAttribute("message")).isEqualTo("Post is not deleted");
    }

    @Test
    public void whenCreatePostThenRedirectsToPosts() throws IOException {
        var file = mock(MultipartFile.class);
        var post = new Post();
        var carDto = new CarDto(1, "car", new Color(), new Engine(), new Brand());
        var user = new User();
        user.setId(1);
        when(file.getOriginalFilename()).thenReturn("photo.jpg");
        when(file.getBytes()).thenReturn("fileContent".getBytes());
        when(carService.findById(anyInt())).thenReturn(Optional.of(carDto));
        when(postService.save(any(), any())).thenReturn(post);
        String view = postController.create(1, post, user, file);
        assertThat(view).isEqualTo("redirect:/posts");
        assertThat(post.getUser()).isEqualTo(user);
        assertThat(post.getCar()).isNotNull();
    }

    @Test
    public void whenUpdatePostThenRedirectsToPosts() throws IOException {
        var file = mock(MultipartFile.class);
        var post = new Post();
        post.setId(1);
        var user = new User();
        user.setId(1);
        when(file.getOriginalFilename()).thenReturn("photo.jpg");
        when(file.getBytes()).thenReturn("fileContent".getBytes());
        when(postService.update(any(), any())).thenReturn(true);
        var model = new ConcurrentModel();
        String view = postController.update(model, post, file, user);
        assertThat(view).isEqualTo("redirect:/posts");
    }
}