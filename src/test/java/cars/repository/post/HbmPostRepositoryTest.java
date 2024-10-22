package cars.repository.post;

import cars.model.Photo;
import cars.model.Post;
import cars.repository.CrudRepository;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HbmPostRepositoryTest {
    private static PostRepository postRepository;

    @BeforeAll
    public static void initRepository() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        postRepository = new HbmPostRepository(new CrudRepository(sf));
    }

    @Test
    void whenSavePost() {
        Post post = new Post();
        post.setPhotos(List.of(new Photo()));
        postRepository.save(post);
        Optional<Post> result = postRepository.findById(post.getId());
        assertTrue(result.isPresent());
        assertThat(result.get().getId()).isEqualTo(post.getId());
    }

    @Test
    void whenFindById() {
        Post post = new Post();
        post.setPhotos(List.of(new Photo()));
        postRepository.save(post);
        Optional<Post> result = postRepository.findById(post.getId());
        assertTrue(result.isPresent());
        assertThat(result.get()).isEqualTo(post);
    }

    @Test
    void whenUpdate() {
        Post post = new Post();
        post.setPhotos(List.of(new Photo()));
        post.setDescription("desc");
        postRepository.save(post);
        post.setPhotos(List.of(new Photo()));
        post.setDescription("description");
        postRepository.update(post);
        Optional<Post> result = postRepository.findById(post.getId());
        assertTrue(result.isPresent());
        assertThat(result.get().getDescription()).isEqualTo("description");
    }

    @Test
    void whenDelete() {
        Post post = new Post();
        post.setPhotos(List.of(new Photo()));
        postRepository.save(post);
        Optional<Post> result = postRepository.findById(post.getId());
        assertTrue(result.isPresent());
        post.setPhotos(Collections.emptyList());
        postRepository.save(post);
        postRepository.deleteById(post.getId());
        assertThat(postRepository.findById(post.getId())).isEqualTo(Optional.empty());
    }

    @Test
    void whenFindCarsWithPhoto() {
        Post post = new Post();
        Photo photo = new Photo();
        List<Photo> photos = new ArrayList<>();
        photos.add(photo);
        post.setPhotos(photos);
        postRepository.save(post);
        Optional<Post> result = postRepository.findById(post.getId());
        assertTrue(result.isPresent());
        assertThat(result.get()).isEqualTo(post);
    }
}