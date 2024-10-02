package cars.service.engine;

import cars.model.Engine;
import cars.repository.engine.EngineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleEngineService implements EngineService {
    private final EngineRepository engineRepository;

    @Override
    public Engine save(Engine engine) {
        return engineRepository.save(engine);
    }

    @Override
    public Collection<Engine> findAll() {
        return engineRepository.findAll();
    }

    @Override
    public Optional<Engine> findById(int id) {
        return engineRepository.findById(id);
    }

    @Override
    public Optional<Engine> findByName(String name) {
        return engineRepository.findByName(name);
    }

    @Override
    public boolean update(Engine engine) {
        return engineRepository.update(engine);
    }

    @Override
    public boolean deleteById(int id) {
        return engineRepository.deleteById(id);
    }
}
