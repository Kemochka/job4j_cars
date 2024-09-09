package cars.service.user;

import cars.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);

    void update(User user);

    void delete(int userId);

    List<User> findAllOrderById();

    Optional<User> findById(int userId);

    List<User> findByLikeLogin(String key);

    Optional<User> findByLoginAndPassword(String login, String password);
}
