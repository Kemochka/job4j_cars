package cars.service.user;

import cars.model.User;
import cars.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleUserServiceTest {
    private SimpleUserService userService;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new SimpleUserService(userRepository);
    }

    @Test
    public void whenCreateUserThenReturnSavedUser() {
        User user = new User();
        user.setLogin("testLogin");
        when(userRepository.create(user)).thenReturn(user);
        User actual = userService.create(user);
        assertThat(actual).isEqualTo(user);
    }

    @Test
    public void whenFindAllOrderByIdThenReturnUserList() {
        User user1 = new User();
        User user2 = new User();
        List<User> users = List.of(user1, user2);
        when(userRepository.findAllOrderById()).thenReturn(users);
        List<User> actual = userService.findAllOrderById();
        assertThat(actual).isEqualTo(users);
    }

    @Test
    public void whenFindByIdThenReturnOptionalUser() {
        User user = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Optional<User> actual = userService.findById(1);
        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(user);
    }

    @Test
    public void whenFindByIdThenReturnEmpty() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        Optional<User> actual = userService.findById(1);
        assertThat(actual).isEmpty();
    }

    @Test
    public void whenFindByLikeLoginThenReturnUserList() {
        User user1 = new User();
        User user2 = new User();
        List<User> users = List.of(user1, user2);
        when(userRepository.findByLikeLogin("test")).thenReturn(users);
        List<User> actual = userService.findByLikeLogin("test");
        assertThat(actual).isEqualTo(users);
    }

    @Test
    public void whenFindByLoginAndPasswordThenReturnOptionalUser() {
        User user = new User();
        when(userRepository.findByLoginAndPassword("testLogin", "testPassword")).thenReturn(Optional.of(user));
        Optional<User> actual = userService.findByLoginAndPassword("testLogin", "testPassword");
        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(user);
    }

    @Test
    public void whenFindByLoginAndPasswordThenReturnEmpty() {
        when(userRepository.findByLoginAndPassword("wrongLogin", "wrongPassword")).thenReturn(Optional.empty());
        Optional<User> actual = userService.findByLoginAndPassword("wrongLogin", "wrongPassword");
        assertThat(actual).isEmpty();
    }
}