package cars.repository.user;

import cars.model.User;
import cars.repository.CrudRepository;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HbmUserRepositoryTest {
    private static UserRepository userRepository;

    @BeforeAll
    public static void initRepository() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        userRepository = new HbmUserRepository(new CrudRepository(sf));
    }

    @Test
    void whenSave() {
        User user = new User();
        userRepository.create(user);
        Optional<User> result = userRepository.findById(user.getId());
        assertTrue(result.isPresent());
        assertThat(result.get().getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    void whenUpdate() {
        User user = new User();
        user.setLogin("log");
        userRepository.create(user);
        user.setLogin("login");
        userRepository.update(user);
        Optional<User> result = userRepository.findById(user.getId());
        assertTrue(result.isPresent());
        assertThat(result.get().getLogin()).isEqualTo("login");
    }

    @Test
    void whenFindAll() {
        User user1 = new User();
        User user2 = new User();
        userRepository.create(user1);
        userRepository.create(user2);
        Collection<User> users = List.of(user1, user2);
        assertThat(userRepository.findAllOrderById()).isEqualTo(users);
    }

    @Test
    void whenDelete() {
        User user = new User();
        userRepository.create(user);
        Optional<User> result = userRepository.findById(user.getId());
        assertTrue(result.isPresent());
        userRepository.delete(user.getId());
        assertThat(userRepository.findById(user.getId())).isEqualTo(Optional.empty());
    }
}