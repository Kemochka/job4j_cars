package cars.service.engine;

import cars.model.Engine;

import java.util.Collection;
import java.util.Optional;

public interface EngineService {
    Engine save(Engine engine);

    Collection<Engine> findAll();

    Optional<Engine> findById(int id);

    Optional<Engine> findByName(String name);

    boolean update(Engine engine);

    boolean deleteById(int id);
}
