package cars.repository.engine;

import cars.model.Engine;
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

class HbmEngineRepositoryTest {
    private static EngineRepository engineRepository;

    @BeforeAll
    public static void initRepository() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        engineRepository = new HbmEngineRepository(new CrudRepository(sf));
    }

    @Test
    void whenSave() {
        Engine engine = new Engine();
        engine = engineRepository.save(engine);
        Optional<Engine> result = engineRepository.findById(engine.getId());
        assertTrue(result.isPresent());
        assertThat(result.get().getName()).isEqualTo(engine.getName());
    }

    @Test
    void whenUpdate() {
        Engine engine = new Engine();
        engine.setName("En");
        engineRepository.save(engine);
        engine.setName("Engine");
        engineRepository.update(engine);
        Optional<Engine> result = engineRepository.findById(engine.getId());
        assertTrue(result.isPresent());
        assertThat(result.get().getName()).isEqualTo("Engine");
    }

    @Test
    void whenSaveItemAndCheckItemId() {
        Engine engine = new Engine();
        engine = engineRepository.save(engine);
        Optional<Engine> result = engineRepository.findById(engine.getId());
        assertTrue(result.isPresent());
        assertThat(result.get()).isEqualTo(engine);
    }

    @Test
    void whenTestFindAll() {
        Engine engine1 = new Engine();
        Engine engine2 = new Engine();
        engine1 = engineRepository.save(engine1);
        engine2 = engineRepository.save(engine2);
        Collection<Engine> expected = List.of(engine1, engine2);
        assertThat(engineRepository.findAll()).isEqualTo(expected);
    }

    @Test
    void whenDelete() {
        Engine engine = new Engine();
        engine = engineRepository.save(engine);
        Optional<Engine> result = engineRepository.findById(engine.getId());
        assertTrue(result.isPresent());
        engineRepository.deleteById(engine.getId());
        assertThat(engineRepository.findAll().size()).isEqualTo(0);
    }
}