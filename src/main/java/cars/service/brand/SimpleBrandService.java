package cars.service.brand;

import cars.model.Brand;
import cars.repository.brand.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleBrandService implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Collection<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> findById(int id) {
        return brandRepository.findById(id);
    }

    @Override
    public boolean update(Brand brand) {
        return brandRepository.update(brand);
    }

    @Override
    public boolean deleteById(int id) {
        return brandRepository.deleteById(id);
    }
}
