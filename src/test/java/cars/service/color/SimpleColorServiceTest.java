package cars.service.color;

import cars.model.Color;
import cars.repository.color.ColorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleColorServiceTest {
    private SimpleColorService colorService;
    private ColorRepository colorRepository;

    @BeforeEach
    public void init() {
        colorRepository = mock(ColorRepository.class);
        colorService = new SimpleColorService(colorRepository);
    }

    @Test
    public void whenSaveColor() {
        var color = new Color();
        when(colorRepository.save(color)).thenReturn(color);
        var actual = colorService.save(color);
        assertThat(actual).isEqualTo(color);
    }

    @Test
    public void whenFindAllColors() {
        var color1 = new Color();
        var color2 = new Color();
        Collection<Color> colors = List.of(color1, color2);
        when(colorRepository.findAll()).thenReturn(colors);
        var actual = colorService.findAll();
        assertThat(actual).isEqualTo(colors);
    }

    @Test
    public void whenUpdateColor() {
        var color = new Color();
        when(colorRepository.update(color)).thenReturn(true);
        var actual = colorService.update(color);
        assertThat(actual).isTrue();
    }
}