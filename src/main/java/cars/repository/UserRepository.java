package cars.repository;

import cars.model.User;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;
    private static final Logger LOGGER = Logger.getLogger(UserRepository.class);

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = sf.openSession();
        User result = null;
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            result = user;
        } catch (Exception e) {
            LOGGER.error("Exception during create user", e);
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("Exception during update user", e);
        } finally {
            session.close();
        }
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("delete User where id = :userId")
                    .setParameter("userId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("Exception during delete user", e);
        } finally {
            session.close();
        }
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        List<User> users = new ArrayList<>();
        try {
            session.beginTransaction();
            users = session.createQuery("from User order by id", User.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("Exception during find all users ordered by id", e);
        } finally {
            session.close();
        }
        return users;
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User as i where i.id = :userId", User.class);
            query.setParameter("userId", userId);
            Optional<User> user = query.uniqueResultOptional();
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            LOGGER.error("Exception during find user by id", e);
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        List<User> users = new ArrayList<>();
        try {
            session.beginTransaction();
            var query = session.createQuery("from User where login like :key", User.class);
            query.setParameter("key", "%" + key + "%");
            users = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("Exception during find user like login", e);
        } finally {
            session.close();
        }
        return users;
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();

        try {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User as i where i.login = :login", User.class);
            query.setParameter("login", login);
            Optional<User> user = query.uniqueResultOptional();
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            LOGGER.error("Exception during find user by login", e);
        } finally {
            session.close();
        }
        return Optional.empty();
    }
}
