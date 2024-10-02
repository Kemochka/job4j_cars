package cars.service.color;

import cars.model.Color;
import cars.repository.color.ColorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleColorService implements ColorService {
    private final ColorRepository colorRepository;

    @Override
    public Color save(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public Collection<Color> findAll() {
        return colorRepository.findAll();
    }

    @Override
    public Optional<Color> findById(int id) {
        return colorRepository.findById(id);
    }

    @Override
    public boolean update(Color color) {
        return colorRepository.update(color);
    }

    @Override
    public boolean deleteById(int id) {
        return colorRepository.deleteById(id);
    }
}
