package cars.repository.engine;

import cars.model.Engine;

import java.util.Collection;
import java.util.Optional;

public interface EngineRepository {
    Engine save(Engine engine);

    Collection<Engine> findAll();

    Optional<Engine> findById(int id);

    boolean update(Engine engine);

    boolean deleteById(int id);
}
