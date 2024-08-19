package cars.repository.engine;

import cars.model.Engine;
import cars.repository.CrudRepository;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmEngineRepository implements EngineRepository {
    private final CrudRepository crudRepository;
    private static final Logger LOGGER = Logger.getLogger(HbmEngineRepository.class);

    @Override
    public Engine save(Engine engine) {
        try {
            crudRepository.run(session -> session.save(engine));
        } catch (Exception e) {
            LOGGER.error("Exception during save engine", e);
        }
        return engine;
    }

    @Override
    public Collection<Engine> findAll() {
        return crudRepository.query("fron Engine", Engine.class);
    }

    @Override
    public Optional<Engine> findById(int id) {
        return crudRepository.optional("from Engine where id = :fId", Engine.class, Map.of("fId", id));
    }

    @Override
    public boolean update(Engine engine) {
        boolean result = false;
        try {
            crudRepository.run(session -> session.merge(engine));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Exception during update engine", e);
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = false;
        try {
            crudRepository.run("delete from Engine where id = fId", Map.of("fId", id));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Exception during delete engine by id");
        }
        return result;
    }
}
