package cars.service.engine;

import cars.model.Engine;
import cars.repository.engine.EngineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleEngineServiceTest {
    private SimpleEngineService engineService;
    private EngineRepository engineRepository;

    @BeforeEach
    public void init() {
        engineRepository = mock(EngineRepository.class);
        engineService = new SimpleEngineService(engineRepository);
    }

    @Test
    public void whenSaveEngine() {
        var engine = new Engine();
        when(engineRepository.save(engine)).thenReturn(engine);
        var actual = engineService.save(engine);
        assertThat(actual).isEqualTo(engine);
    }

    @Test
    public void whenFindAllEngines() {
        var engine1 = new Engine();
        var engine2 = new Engine();
        Collection<Engine> engines = List.of(engine1, engine2);
        when(engineRepository.findAll()).thenReturn(engines);
        var actual = engineService.findAll();
        assertThat(actual).isEqualTo(engines);
    }

    @Test
    public void whenAddEngineAndFindById() {
        var engine = new Engine();
        when(engineRepository.findById(engine.getId())).thenReturn(Optional.of(engine));
        var actual = engineService.findById(engine.getId());
        assertThat(actual).isEqualTo(Optional.of(engine));
    }

    @Test
    public void whenUpdateEngine() {
        var engine = new Engine();
        when(engineRepository.update(engine)).thenReturn(true);
        var actual = engineService.update(engine);
        assertThat(actual).isTrue();
    }

    @Test
    public void whenDeleteEngine() {
        var engine = new Engine();
        when(engineRepository.deleteById(engine.getId())).thenReturn(true);
        var actual = engineService.deleteById(engine.getId());
        assertThat(actual).isTrue();
    }
}